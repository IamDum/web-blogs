package se.pierce.blog.controller;

import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import se.pierce.blog.Application;
import se.pierce.blog.model.PostDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostControllerTest {

    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @Test
    @Order(1)
    public void allPostsShouldBeZero() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<PostDto[]> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.GET, entity, PostDto[].class);
        Assert.assertSame(response.getStatusCode(), HttpStatus.OK);
        PostDto[] body = response.getBody();
        Assert.assertSame(body.length, 0);
    }

    @Test
    @Order(2)
    public void addPost() {
        PostDto request = new PostDto();
        request.setContent("Writing a content");
        request.setTitle("Adding title");

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PostDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PostDto> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.POST, entity, PostDto.class);
        Assert.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(3)
    public void allPosts() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<PostDto[]> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.GET, entity, PostDto[].class);
        Assert.assertSame(response.getStatusCode(), HttpStatus.OK);
        PostDto[] body = response.getBody();
        Assert.assertSame(body.length, 1);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}