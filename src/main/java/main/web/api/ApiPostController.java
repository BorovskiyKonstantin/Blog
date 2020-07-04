package main.web.api;

//обрабатывает все запросы /api/post/*

import main.domain.post.model.PostInfoDTO;
import main.domain.post.model.PostRequestDTO;
import main.domain.post.usecase.PostUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * + 1. Список   постов   -  GET   /api/post/
 * + 2. Поиск   постов   -   GET   /api/post/search/
 * + 3. Получение   поста   -   GET   /api/post/ID
 * + 4. Список   постов   за   конкретную   дату   -   GET   /api/post/byDate
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
    private PostUseCase postUseCase;

    @Autowired
    public ApiPostController(PostUseCase postUseCase) {
        this.postUseCase = postUseCase;
    }

    //    1. Список   постов
    @GetMapping(value = "/api/post")
    public PostRequestDTO getPosts(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                   @RequestParam(name = "limit") int limit,
                                   @RequestParam(name = "mode") String mode) {
        return postUseCase.getPosts(offset, limit, mode);
    }

//    2. Поиск   постов   -   GET   /api/post/search
    @GetMapping(value = "/api/post/search")
    public PostRequestDTO searchPosts (@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "limit") int limit,
                                       @RequestParam(name = "query") String query){
        return postUseCase.searchPosts(offset, limit, query);
    }
//    3. Получение   поста   -   GET   /api/post/ID
    @GetMapping(value = "/api/post/{id}")
    public PostInfoDTO getPostById(@PathVariable Integer id){
        return postUseCase.getPostById(id);
    }
//    4. Список   постов   за   конкретную   дату   -   GET   /api/post/byDate
    @GetMapping(value = "/api/post/byDate")
    public PostRequestDTO getPostsByDate(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                         @RequestParam(name = "limit") int limit,
                                         @RequestParam(name = "date") String date){
        return postUseCase.getPostsByDate(offset, limit, date);
    }

//    5. Список   постов   по   тэгу   -   GET   /api/post/byTag
    @GetMapping(value = "/api/post/byTag")
    public PostRequestDTO getPostsByTag(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                        @RequestParam(name = "limit") int limit,
                                        @RequestParam(name = "tag") String tag){
        return postUseCase.getPostsByTag(offset, limit, tag);
    }
}
