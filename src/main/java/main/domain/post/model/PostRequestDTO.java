package main.domain.post.model;

import java.util.List;

public class PostRequestDTO {
    private long count;
    private List<PostInfoDTO> posts;

    public PostRequestDTO(long count, List<PostInfoDTO> posts) {
        this.count = count;
        this.posts = posts;
    }

    public long getCount() {
        return count;
    }

    public List<PostInfoDTO> getPosts() {
        return posts;
    }
}
