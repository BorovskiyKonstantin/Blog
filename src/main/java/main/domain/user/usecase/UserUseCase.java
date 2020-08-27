package main.domain.user.usecase;

import main.domain.captchacode.entity.CaptchaCode;
import main.domain.captchacode.port.CaptchaCodeRepositoryPort;
import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.post.port.PostRepositoryPort;
import main.domain.user.entity.User;
import main.domain.user.model.auth.AuthResponseDTO;
import main.domain.user.model.changepass.ChangePassResponseDTO;
import main.domain.user.model.register.RegisterResponseDTO;
import main.domain.user.model.restore.RestoreResponseDTO;
import main.domain.user.port.UserRepositoryPort;
import main.domain.user.service.EmailService;
import main.domain.user.service.ImageService;
import main.web.security.user.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class UserUseCase {
    private UserRepositoryPort userRepositoryPort;
    private PostRepositoryPort postRepositoryPort;
    private PasswordEncoder passwordEncoder;
    private CaptchaCodeRepositoryPort captchaCodeRepositoryPort;
    private ImageService imageService;
    private EmailService emailService;

    @Autowired
    public UserUseCase(UserRepositoryPort userRepositoryPort, PostRepositoryPort postRepositoryPort, PasswordEncoder passwordEncoder, CaptchaCodeRepositoryPort captchaCodeRepositoryPort, ImageService imageService, EmailService emailService) {
        this.userRepositoryPort = userRepositoryPort;
        this.postRepositoryPort = postRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.captchaCodeRepositoryPort = captchaCodeRepositoryPort;
        this.imageService = imageService;
        this.emailService = emailService;
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepositoryPort.findUserByName(username);
    }

    public Optional<User> getUserById(int id) {
        return userRepositoryPort.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepositoryPort.findUserByEmail(email);
    }

    public RegisterResponseDTO registerUser(String email, String name, String password, String captcha, String captchaSecret) {
        //Поиск ошибок
        Map<String, String> errors = new LinkedHashMap<>();
        if (userRepositoryPort.findUserByEmail(email).isPresent())
            errors.put("email", "Этот e-mail уже зарегистрирован");
        if (name == null || name.replaceAll("\\s+", "").length() == 0 || name.matches(".*\\d+.*"))
            errors.put("name", "Имя указано неверно");
        if (password.length() < 6)
            errors.put("password", "Пароль короче 6-ти символов");
        CaptchaCode captchaCode = captchaCodeRepositoryPort.findBySecretCode(captchaSecret).orElseThrow();
        if (!captchaCode.getCode().equals(captcha))
            errors.put("captcha", "Код картинки введен неверно");

        //Формирование ответа при наличии ошибок
        if (errors.size() != 0)
            return new RegisterResponseDTO(false, errors);

            //Регистрация пользователя
        else {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(passwordEncoder.encode(password));
            user.setModerator(false);
            user.setRegTime(new Timestamp(System.currentTimeMillis()));
            userRepositoryPort.save(user);
            return new RegisterResponseDTO(true, null);
        }
    }

    public AuthResponseDTO authCheck(Authentication authentication) {
        if (authentication == null) {
            return AuthResponseDTO.failedDTO();
        } else {
            WebUser webUser = (WebUser) authentication.getPrincipal();
            String email = webUser.getUsername();
            User user = userRepositoryPort.findUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
            return createSuccessfulAuthResponseDTO(user);
        }
    }

    public AuthResponseDTO createSuccessfulAuthResponseDTO(User user) {
        int moderationCount = 0;
        if (user.isModerator()) {
            List<Post> newPosts = postRepositoryPort.getActivePostsByModerationStatus(ModerationStatus.NEW, null);
            moderationCount = newPosts.size();
        }
        return AuthResponseDTO.successfulDTO(user, moderationCount);
    }

    public ChangePassResponseDTO changePassword(String code, String password, String captcha, String captchaSecret) {
        //Поиск ошибок
        Map<String, String> errors = new LinkedHashMap<>();
        User user = userRepositoryPort.findUserByCode(code).orElse(null);
        if (user == null)
            errors.put("code", "Ссылка   для   восстановления   пароля   устарела.   <a   href=”/auth/restore”>Запросить   ссылку   снова</a>");
        if (password.length() < 6)
            errors.put("password", "Пароль   короче   6-ти  с имволов");
        CaptchaCode captchaCode = captchaCodeRepositoryPort.findBySecretCode(captchaSecret).orElseThrow();
        if (!captchaCode.getCode().equals(captcha))
            errors.put("captcha", "Код с картинки введён неверно");

        //Формирование ответа при наличии ошибок
        if (errors.size() > 0)
            return new ChangePassResponseDTO(false, errors);

            //Смена пароля
        else {
            Objects.requireNonNull(user).setPassword(passwordEncoder.encode(password));
            userRepositoryPort.save(user);
            return new ChangePassResponseDTO(true, null);
        }

    }

    public RestoreResponseDTO restorePassword(String email) {
        //поиск юзера
        User user = userRepositoryPort.findUserByEmail(email).orElse(null);

        //если не найден ответ=false
        if (user == null)
            return new RestoreResponseDTO(false);

        /** ему  должно  отправляться  письмо  со  ссылкой  на  восстановление
         * пароля  следующего  вида  -  /login/change-password/HASH,  где  HASH  -  сгенерированный
         код  вида  “b55ca6ea6cb103c6384cfa366b7ce0bdcac092be26bc0”  (код  должен
         генерироваться   случайным   образом   и   сохраняться   в   базе   данных   в   поле   users.code).
         */
        //генерация HASH
        String hash = generateHash(45);

        //сохранение HASH в users.code
        user.setCode(hash);
        userRepositoryPort.save(user);

        //Составление текста письма
        String mailText = "Добрый день, " + user.getName() +
                "\nНа DevPub был получен запрос на сброс пароля Вашей учетной записи." +
                "\nЕсли Вы не отправляли запрос, то просто проигнорируйте это письмо." +
                "\n" +
                "\nВ противном случае перейдите по ссылке: " +
                "\nhttp://localhost:8080/login/change-password/" + hash;

        //Отправка письма
        emailService.sendSimpleMail(user.getEmail(), "Восстановление пароля", mailText);

        //Response
        return new RestoreResponseDTO(true);
    }

    public Optional<User> getCurrentUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepositoryPort.findUserByEmail(userEmail);
    }

    public ResponseEntity<Object> editProfile(String email, String name, String password, MultipartFile photo, Integer removePhoto) {
        User currentUserProfile = getCurrentUser().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Map<String, String> errors = new LinkedHashMap<>(); //Лог ошибок

        //обработка параметра email
        if (email != null) {
            if (!currentUserProfile.getEmail().equals(email))
                if (userRepositoryPort.findUserByEmail(email).isPresent())
                    errors.put("email", "Этот e-mail уже зарегистрирован");
                else
                    currentUserProfile.setEmail(email);
        }

        //обработка параметра name
        if (name != null) {
            if (name.replaceAll("\\s+", "").length() == 0 || name.matches(".*\\d+.*"))
                errors.put("name", "Имя указано неверно");
            else
                currentUserProfile.setName(name);
        }

        //обработка параметра password
        if (password != null) {
            if (password.replaceAll("\\s+", "").length() < 6)
                errors.put("password", "Пароль короче 6-ти символов");
            else
                currentUserProfile.setPassword(passwordEncoder.encode(password));
        }

        //обработка настроек фотографии
        //обработка параметра removePhoto
        if (removePhoto == Integer.valueOf(1)) {
            //удалить файл
            imageService.deleteImage(currentUserProfile.getPhoto());
            //стереть photo
            currentUserProfile.setPhoto(null);
        }
        //обработка параметра photo
        if (photo != null) {
            double sizeMB = (double) photo.getSize() / 1024 / 1024;  //Размер файла в MB
            if (sizeMB > 5)
                errors.put("photo", "Фото слишком большое, нужно не более 5 Мб");
            else {
                String filePath = imageService.uploadProfilePhoto(photo);
                currentUserProfile.setPhoto(filePath);
            }
        }

        //Проверка лога ошибок
        if (errors.size() > 0) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("result", false);
            errorResponse.put("errors", errors);
            return new ResponseEntity<>(errorResponse, HttpStatus.OK);
        }
        userRepositoryPort.save(currentUserProfile);
        return new ResponseEntity<>(Collections.singletonMap("result", true), HttpStatus.OK);
    }

    private String generateHash(int length) {
        final String symbols = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int symbolNumb = random.nextInt(symbols.length());
            char symbol = symbols.charAt(symbolNumb);
            builder.append(symbol);
        }
        return builder.toString();
    }

}
