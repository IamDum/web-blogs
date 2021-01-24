package se.pierce.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.pierce.blog.model.PostDto;
import se.pierce.blog.service.PostStorageService;

import java.util.List;

@RestController
@RequestMapping("/blog-web")
public class PostController {

    @Autowired
    private PostStorageService storageService;

    @PostMapping(value = "/posts")
    public void addPost(@RequestBody PostDto post) {
        storageService.savePost(post.getTitle(), post.getContent());
    }

    @GetMapping(value = "/posts")
    public List<PostDto> getPost() {
        return storageService.allPost();
    }

    @PutMapping(value = "/posts")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto post) {
        PostDto postDto = storageService.updatePost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @GetMapping(value = "/posts/{postId}")
    public PostDto getPostById(@PathVariable("postId") String postId) {
        return storageService.getPostById(postId);
    }

    @DeleteMapping(value = "/posts/{postId}")
    public void deletePost(@PathVariable("postId") String postId) {
        storageService.delPostById(postId);
    }
}



