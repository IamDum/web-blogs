package se.pierce.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void post(@RequestBody PostDto post) {
        storageService.savePost(post.getTitle(), post.getContent());
    }

    @GetMapping(value = "/posts")
    public List<PostDto> getPost() {
        return storageService.allPost();
    }

    @PutMapping(value = "/posts")
    public void updatePost(@RequestBody PostDto post) {
        //return storageService.updatePost(post);
    }
}

