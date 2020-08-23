package main.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.domain.globalsetting.model.GlobalSettingDto;
import main.domain.globalsetting.usecase.GlobalSettingUseCase;
import main.domain.post.model.CalendarResponseDTO;
import main.domain.post.model.PostModerationDTO;
import main.domain.post.usecase.PostUseCase;
import main.domain.postcomments.model.CommentRequestDTO;
import main.domain.postcomments.model.CommentResponseDTO;
import main.domain.postcomments.usecase.PostCommentUseCase;
import main.domain.tag.model.TagResponseDTO;
import main.domain.tag.usecase.TagUseCase;
import main.domain.user.model.profile.ChangeProfileRequestDTO;
import main.domain.user.service.ImageService;
import main.domain.user.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

/**
 * для прочих запросов к API
 * <p>
 * + 1. Общие   данные   блога   -   GET   /api/init
 * + 2. Загрузка   изображений   -   POST   /api/image
 * + 3. Отправка   комментария   к   посту   -   POST   /api/comment/
 * + 4. Получение   списка   тэгов   -   GET   /api/tag/
 * + 5. Модерация   поста   -   POST   /api/moderation
 * + 6. Календарь   (количества   публикаций)   -   GET   /api/calendar/
 * + 7. Редактирование   моего   профиля   -   POST   /api/profile/my
 * + 8. Моя   статистика   -   GET   /api/statistics/my
 * + 9. Статистика   по   всему   блогу   -   GET   /api/statistics/all
 * + 10. Получение   настроек   -   GET   /api/settings/
 * + 11. Сохранение   настроек   -   PUT   /api/settings/
 */

@RestController
@RequestMapping("/api")
public class ApiGeneralController {
    private GlobalSettingUseCase globalSettingUseCase;
    private TagUseCase tagUseCase;
    private PostCommentUseCase postCommentUseCase;
    private ImageService imageService;
    private PostUseCase postUseCase;
    private UserUseCase userUseCase;

    @Autowired
    public ApiGeneralController(GlobalSettingUseCase globalSettingUseCase, TagUseCase tagUseCase, PostCommentUseCase postCommentUseCase, ImageService imageService, PostUseCase postUseCase, UserUseCase userUseCase) {
        this.globalSettingUseCase = globalSettingUseCase;
        this.tagUseCase = tagUseCase;
        this.postCommentUseCase = postCommentUseCase;
        this.imageService = imageService;
        this.postUseCase = postUseCase;
        this.userUseCase = userUseCase;
    }

    //    1. Общие   данные   блога   -   GET   /api/init
    @GetMapping(value = "/init")
    public ObjectNode getBlogInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode blogInfoJson = objectMapper.createObjectNode();
        blogInfoJson.put("title", "DevPub");
        blogInfoJson.put("subtitle", "Рассказы   разработчиков");
        blogInfoJson.put("phone", "+7   903   666-44-55");
        blogInfoJson.put("email", "user@example.ru");
        blogInfoJson.put("copyright", "Дмитрий   Сергеев");
        blogInfoJson.put("copyrightFrom", "2005");
        return blogInfoJson;
    }

    //2. Загрузка   изображений   -   POST   /api/image
    @PostMapping(value = "/image", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<Object> uploadImage(@RequestParam("image")MultipartFile image){
        return imageService.uploadImage(image);
    }

    //3. Отправка   комментария   к   посту   -   POST   /api/comment/
    @PostMapping("/comment")
    public CommentResponseDTO comment(@Valid @RequestBody CommentRequestDTO commentRequestDTO){
        return postCommentUseCase.saveComment(commentRequestDTO, userUseCase.getCurrentUser().getId());
    }

    //4. Получение   списка   тэгов   -   GET   /api/tag/
    @GetMapping(value = "/tag")
    public TagResponseDTO getTags(@RequestParam(name = "query", required = false) String query){
        return new TagResponseDTO(tagUseCase.getTags(query));
    }

    //5. Модерация   поста   -   POST   /api/moderation
    @PostMapping("/moderation")
    @Secured("ROLE_MODERATOR")
    public Object moderation (@RequestBody PostModerationDTO requestDTO){
        return postUseCase.moderation(requestDTO);
    }

    //6. Календарь   (количества   публикаций)   -   GET   /api/calendar/
    @GetMapping("/calendar")
    public CalendarResponseDTO calendar (@RequestParam(value = "year", required = false) Integer year){
        return postUseCase.getCalendar(year);
    }

    //7. Редактирование   моего   профиля   -   POST   /api/profile/my
    // 7.1. Изменение профиля - multipart/form-data
    @PostMapping(value = "/profile/my", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> changeProfileMultipart(
            @RequestPart(value = "email", required = false) String email,
            @RequestPart(value = "name", required = false) String name,
            @RequestPart(value = "password", required = false) String password,
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            @RequestPart(value = "removePhoto", required = false) String removePhoto){
        return userUseCase.editProfile(email, name, password, photo, Integer.parseInt(removePhoto));
    }

    // 7.2. Изменение профиля - application/json
    @PostMapping(value = "/profile/my", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> changeProfileApplicationJson(@RequestBody ChangeProfileRequestDTO requestDTO){
        return userUseCase.editProfile(requestDTO.getEmail(), requestDTO.getName(), requestDTO.getPassword(), null, requestDTO.getRemovePhoto());
    }

    //    8. Моя   статистика   -   GET   /api/statistics/my
    @GetMapping("/statistics/my")
    public ResponseEntity<Object> statisticsMy(){
        return new ResponseEntity<>(postUseCase.statisticsMy(), HttpStatus.OK);
    }

    //    9. Статистика   по   всему   блогу   -   GET   /api/statistics/all
    @GetMapping("/statistics/all")
    public ResponseEntity<Object> statisticsAll(){
        return new ResponseEntity<>(postUseCase.statisticsAll(), HttpStatus.OK);
    }

    //    10. Получение   настроек   -   GET   /api/settings/
    @GetMapping(value = "/settings")
    public GlobalSettingDto getSettings() {
        return globalSettingUseCase.getSettings();
    }

    //    11. Сохранение   настроек
    @PutMapping(value = "/settings")
    @Secured("ROLE_MODERATOR")
    public void putSettings(@RequestBody GlobalSettingDto settingEditDto) {
        globalSettingUseCase.editSettings(settingEditDto);
    }
}
