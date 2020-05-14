package main.domain.user.usecase;



import main.domain.user.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserSecurity {

    private Map<String, Integer> SESSION_HOLDER = new HashMap<>();

    @Autowired
    UserRepositoryPort userRepositoryPort;

//    public AuthResponse checkAuth(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//
//
//        /**
//         * Авторизация пользователя из хранилища
//         * */
//        String sessionId = session.getId();
//        if (SESSION_HOLDER.containsKey(sessionId)) {
//            int userId = SESSION_HOLDER.get(sessionId);
//            User user = userRepositoryPort.findById(userId).orElseThrow();
//
//            //Create DTO
//            int id = user.getId();
//            String name = user.getName();
//            String photo = user.getPhoto();
//            String email = user.getEmail();
//            boolean moderation = user.isModerator();
//            int moderationCount = user.getModeratedPosts().size();
//            boolean settings = moderation;
//
//            return new AuthSuccessfulDTO(id, name, photo, email, moderation, moderationCount, settings);
//        }
//        else return new AuthFailedDTO();

        /**
         * Авторизация пользователя из атрибута сессии
         * */
//        Integer sessionUserId = (Integer) session.getAttribute("user");
//        if(sessionUserId == null) {
//            return new AuthFailedDTO();
//        }
//        else {
//            User user = userRepositoryPort.findById(sessionUserId).orElseThrow();
//            //Create DTO
//            int id = user.getId();
//            String name = user.getName();
//            String photo = user.getPhoto();
//            String email = user.getEmail();
//            boolean moderation = user.isModerator();
//            int moderationCount = user.getModeratedPosts().size();
//            boolean settings = moderation;
//
//            return new AuthSuccessfulDTO(id, name, photo, email, moderation, moderationCount, settings);
//        }
//    }

//    public AuthResponse loginUser(HttpServletRequest request, AuthRequestDTO loginDTO) {
//        String loginEmail = loginDTO.getEmail();
//        String loginPassword = loginDTO.getPassword();
//        User user = userRepositoryPort.findUserByEmailAndPassword(loginEmail, loginPassword).orElse(null);
//        if (user == null) {
//            return new AuthFailedDTO();
//        } else {
//
//            HttpSession session = request.getSession(true);
//            //Create session attribute
//            session.setAttribute("user", user.getId());
//            //Save session
//            SESSION_HOLDER.put(session.getId(), user.getId());
//
//            //Create DTO
//            int id = user.getId();
//            String name = user.getName();
//            String photo = user.getPhoto();
//            String email = user.getEmail();
//            boolean moderation = user.isModerator();
//            int moderationCount = user.getModeratedPosts().size();
//            boolean settings = moderation;
//
//
//            return new AuthSuccessfulDTO(id, name, photo, email, moderation, moderationCount, settings);
//        }
//    }
}
