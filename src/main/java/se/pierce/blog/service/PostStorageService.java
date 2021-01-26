package se.pierce.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.pierce.blog.model.PostDto;
import se.pierce.blog.storage.PostRepository;
import se.pierce.blog.storage.entity.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            postDtoList.add(post.toPostDto());
        }
        return postDtoList;
    }

    public PostDto updatePost(PostDto post) {
        long idLong = Long.parseLong(post.getId());
        Optional<Post> optionalPost = postRepository.findById(idLong);
        if (optionalPost.isPresent()) {
            Post postFromDB = optionalPost.get();
            postFromDB.setContent(post.getContent());
            postFromDB.setTitle(post.getTitle());
            postRepository.save(postFromDB);
            return post;
        }
        return throwNotFoundException();
    }

    public PostDto getPostById(String postId) {
        long parseLong = Long.parseLong(postId);
        Optional<Post> posts = postRepository.findById(parseLong);
        if (posts.isPresent()) {
            return posts.get().toPostDto();
        }
        return throwNotFoundException();
    }

    public void delPostById(String postId) {
        long postIdLong = Long.parseLong(postId);
        Optional<Post> posts = postRepository.findById(postIdLong);
        if (posts.isPresent()) {
            postRepository.deleteById(postIdLong);
        } else {
            throwNotFoundException();
        }
    }

    private PostDto throwNotFoundException() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
    }
}


