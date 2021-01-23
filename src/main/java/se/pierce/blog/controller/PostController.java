package se.pierce.blog.controller;


import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.pierce.blog.model.PostDto;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/blog-web")
public class PostController {


    @PostMapping(value = "/posts")
    public void post(@RequestBody PostDto post) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find resource");
    }

    @GetMapping(value = "/posts")
    public List<PostDto> getPost() {
        throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
    }

    @PutMapping(value = "/put")
    public String put(@RequestBody PostDto post) {
        throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
    }
}

