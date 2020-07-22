package main.web.api;

//обрабатывает все запросы /api/post/*

import main.domain.post.model.PostSaveRequestDTO;
import main.domain.post.model.PostInfoDTO;
import main.domain.post.model.PostSaveResponseDTO;
import main.domain.post.model.PostResponseDTO;
import main.domain.post.usecase.PostUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * + 1. Список   постов   -  GET   /api/post/
 * + 2. Поиск   постов   -   GET   /api/post/search/
 * + 3. Получение   поста   -   GET   /api/post/ID
 * + 4. Список   постов   за   конкретную   дату   -   GET   /api/post/byDate
 * + 5. Список   постов   по   тэгу   -   GET   /api/post/byTag
 * + 6. Список   постов   на   модерацию   -   GET   /api/post/moderation
 * + 7. Список   моих   постов   -   GET   /api/post/my
 * + 8. Добавление   поста   -   POST   /api/post
 * + 9. Редактирование   поста   -   PUT   /api/post/ID
 * 10. Лайк   поста   -   POST   /api/post/like
 * 11. Дизлайк   поста   -  P OST   /api/post/dislike
 */
@RestController
@RequestMapping("/api/post")
public class ApiPostController {
    private PostUseCase postUseCase;

    @Autowired
    public ApiPostController(PostUseCase postUseCase) {
        this.postUseCase = postUseCase;
    }

    //    1. Список   постов
    @GetMapping
    public PostResponseDTO getPosts(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                    @RequestParam(name = "limit") int limit,
                                    @RequestParam(name = "mode") String mode) {
        return postUseCase.getPosts(offset, limit, mode);
    }

//    2. Поиск   постов   -   GET   /api/post/search
    @GetMapping(value = "/search")
    public PostResponseDTO searchPosts (@RequestParam(name = "offset", defaultValue = "0") int offset,
                                        @RequestParam(name = "limit") int limit,
                                        @RequestParam(name = "query") String query){
        return postUseCase.searchPosts(offset, limit, query);
    }
//    3. Получение   поста   -   GET   /api/post/ID
    @GetMapping(value = "/{id}")
    public PostInfoDTO getPostById(@PathVariable Integer id){
        return postUseCase.getPostById(id);
    }

//    4. Список   постов   за   конкретную   дату   -   GET   /api/post/byDate
    @GetMapping(value = "/byDate")
    public PostResponseDTO getPostsByDate(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                          @RequestParam(name = "limit") int limit,
                                          @RequestParam(name = "date") String date){
        return postUseCase.getPostsByDate(offset, limit, date);
    }

//    5. Список   постов   по   тэгу   -   GET   /api/post/byTag
    @GetMapping(value = "/byTag")
    public PostResponseDTO getPostsByTag(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                         @RequestParam(name = "limit") int limit,
                                         @RequestParam(name = "tag") String tag){
        return postUseCase.getPostsByTag(offset, limit, tag);
    }

//    6. Список   постов   на   модерацию   -   GET   /api/post/moderation
    @GetMapping("/moderation")
    @Secured("ROLE_MODERATOR")
    public PostResponseDTO getPostsModeration(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                              @RequestParam(name = "limit") int limit,
                                              @RequestParam(name = "status") String status){

        return postUseCase.getPostsModeration(offset, limit, status);
    }

//    7. Список   моих   постов   -   GET   /api/post/my
    @GetMapping("/my")
    @Secured("ROLE_USER")
    public PostResponseDTO getMyPosts(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                      @RequestParam(name = "limit") int limit,
                                      @RequestParam(name = "status") String status){
        return postUseCase.getCurrentUserPosts(offset, limit, status);
    }

//    8. Добавление   поста   -   POST   /api/post
    @PostMapping
    public PostSaveResponseDTO addPost (@RequestBody PostSaveRequestDTO requestDTO){
        PostSaveResponseDTO response = postUseCase.addPost(requestDTO);
        return response;
    }

    //    9. Редактирование   поста   -   PUT   /api/post/ID
    @PutMapping("/{id}")
    public PostSaveResponseDTO editPost(@PathVariable("id") Integer id,
                                        @RequestBody PostSaveRequestDTO requestDTO) {
        PostSaveResponseDTO response = postUseCase.editPost(id, requestDTO);
        return response;
    }
}
