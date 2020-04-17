package main.web.api;

//обрабатывает все запросы /api/post/*

import main.domain.post.model.PostsDTO;
import main.domain.post.usecase.PostUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * + 1. Список   постов   -  GET   /api/post/
 * - 2. Поиск   постов   -   GET   /api/post/search/
 * - 3. Получение   поста   -   GET   /api/post/ID
 * - 4. Список   постов   за   конкретную   дату   -   GET   /api/post/byDate
 * - 5. Список   постов   по   тэгу   -   GET   /api/post/byTag
 * 6. Список   постов   на   модерацию   -   GET   /api/post/moderation
 * 7. Список   моих   постов   -   GET   /api/post/my
 * 8. Добавление   поста   -   POST   /api/post
 * 9. Редактирование   поста   -   PUT   /api/post/ID
 * 10. Лайк   поста   -   POST   /api/post/like
 * 11. Дизлайк   поста   -  P OST   /api/post/dislike
 */
@RestController
public class ApiPostController {
    @Autowired
    PostUseCase postUseCase;

//    1. Список   постов
    @GetMapping(value = "/api/post/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public PostsDTO getPosts(@RequestParam(name = "offset", defaultValue = "0") int offset,
                             @RequestParam(name = "limit") int limit,
                             @RequestParam(name = "mode") String mode) {
        return postUseCase.getPostsDTO(offset, limit, mode);
    }

//    3. Получение   поста   -   GET   /api/post/ID

}
