package se.pierce.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.pierce.blog.model.PostDto;
import se.pierce.blog.storage.PostRepository;
import se.pierce.blog.storage.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostStorageService {

    @Autowired
    private PostRepository postRepository;

    public void savePost(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
    }

    public List<PostDto> allPost() {
        List<Post> posts = postRepository.findAll();

        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = new PostDto();
            postDto.setContent(post.getContent());
            postDto.setTitle(post.getTitle());
            postDto.setId(String.valueOf(post.getId()));
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    public void updatePost(PostDto post) {

    }
}


