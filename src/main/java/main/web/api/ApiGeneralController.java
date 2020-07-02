package main.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.domain.globalsetting.model.GlobalSettingDto;
import main.domain.globalsetting.usecase.GlobalSettingUseCase;
import main.domain.postcomments.model.CommentRequestDTO;
import main.domain.postcomments.model.CommentResponseDTO;
import main.domain.postcomments.service.PostCommentUseCase;
import main.domain.tag.entity.Tag;
import main.domain.tag.usecase.TagUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * для прочих запросов к API
 * <p>
 * + 1. Общие   данные   блога   -   GET   /api/init
 *   2. Загрузка   изображений   -   POST   /api/image
 *   3. Отправка   комментария   к   посту   -   POST   /api/comment/
 * - (заглушка) 4. Получение   списка   тэгов   -   GET   /api/tag/ (!!!!!!!!!!Нужны изеры и посты для тестирования)
 *   5. Модерация   поста   -   POST   /api/moderation
 * - 6. Календарь   (количества   публикаций)   -   GET   /api/calendar/
 *   7. Редактирование   моего   профиля   -   POST   /api/profile/my
 *   8. Моя   статистика   -   GET   /api/statistics/my
 * ? 9. Статистика   по   всему   блогу   -   GET   /api/statistics/all
 * + 10. Получение   настроек   -   GET   /api/settings/
 * + 11. Сохранение   настроек   -   PUT   /api/settings/
 */

@RestController
public class ApiGeneralController {
    private GlobalSettingUseCase globalSettingUseCase;
    private TagUseCase tagUseCase;
    private PostCommentUseCase postCommentUseCase;

    @Autowired
    public ApiGeneralController(GlobalSettingUseCase globalSettingUseCase, TagUseCase tagUseCase, PostCommentUseCase postCommentUseCase) {
        this.globalSettingUseCase = globalSettingUseCase;
        this.tagUseCase = tagUseCase;
        this.postCommentUseCase = postCommentUseCase;
    }

    //    1. Общие   данные   блога   -   GET   /api/init
    @GetMapping(value = "/api/init")
    public ObjectNode getBlogInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode blogInfoJson = objectMapper.createObjectNode();
        blogInfoJson.put("title", "DevPub");
        blogInfoJson.put("subtitle", "Рассказы   разработчиков");
        blogInfoJson.put("phone", "+7   903   666-44-55");
        blogInfoJson.put("email", "mail@mail.ru");
        blogInfoJson.put("copyright", "Дмитрий   Сергеев");
        blogInfoJson.put("copyrightFrom", "2005");
        return blogInfoJson;
    }

    //3. Отправка   комментария   к   посту   -   POST   /api/comment/
    @PostMapping("/api/comment")
    public ResponseEntity<CommentResponseDTO> comment(@RequestBody CommentRequestDTO commentRequestDTO){
        try {
            CommentResponseDTO postCommentDTO = postCommentUseCase.saveComment(commentRequestDTO);
            return new ResponseEntity<>(postCommentDTO, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //TODO: Заглушка для главной страницы! Реализовать в будущем
    //4. Получение   списка   тэгов   -   GET   /api/tag/
    @GetMapping(value = "/api/tag")
    public List<Tag> getTags(@RequestParam(name = "query", required = false) String query){
        return tagUseCase.getTags(query);
    }

    //    10. Получение   настроек   -   GET   /api/settings/
    @GetMapping(value = "/api/settings")
    public GlobalSettingDto getSettings() {
        return globalSettingUseCase.getSettings();
    }

    //    11. Сохранение   настроек
    @PutMapping(value = "/api/settings")
    @Secured("ROLE_MODERATOR")
    public void putSettings(@RequestBody GlobalSettingDto settingEditDto) {
        globalSettingUseCase.editSettings(settingEditDto);
    }
}
