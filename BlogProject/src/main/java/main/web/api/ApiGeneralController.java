package main.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.domain.globalsetting.model.GlobalSettingDto;
import main.domain.globalsetting.usecase.GlobalSettingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * для прочих запросов к API
 * <p>
 * +1. Общие   данные   блога   -   GET   /api/init
 * 2. Загрузка   изображений   -   POST   /api/image
 * 3. Отправка   комментария   к   посту   -   POST   /api/comment/
 * 4. Получение   списка   тэгов   -   GET   /api/tag/
 * 5. Модерация   поста   -   POST   /api/moderation
 * 6. Календарь   (количества   публикаций)   -   GET   /api/calendar/
 * 7. Редактирование   моего   профиля   -   POST   /api/profile/my
 * 8. Моя   статистика   -   GET   /api/statistics/my
 * 9. Статистика   по   всему   блогу   -   GET   /api/statistics/all
 * +10. Получение   настроек   -   GET   /api/settings/ (доделать проверку авторизации и прав модерации)
 * +11. Сохранение   настроек   -   PUT   /api/settings/
 */

@RestController
public class ApiGeneralController {
    @Autowired
    GlobalSettingUseCase globalSettingUseCase;

    //    1. Общие   данные   блога   -   GET   /api/init
    @GetMapping(value = "/api/init", produces = {MediaType.APPLICATION_JSON_VALUE})
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

    //    10. Получение   настроек   -   GET   /api/settings/
    @GetMapping(value = "/api/settings", produces = {MediaType.APPLICATION_JSON_VALUE})
    public GlobalSettingDto getSettings() {
        return globalSettingUseCase.getSettings();
    }

    //    11. Сохранение   настроек
    @PutMapping(value = "/api/settings")
    public void putSettings(@RequestBody GlobalSettingDto settingEditDto) {
        globalSettingUseCase.editSettings(settingEditDto);
    }
}
