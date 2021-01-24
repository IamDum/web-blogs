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

        ResponseEntity<Void> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.POST, entity, Void.class);
        Assert.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(3)
    public void updatePost() {
        PostDto request = new PostDto();
        request.setId("1");
        request.setContent("updating a content");
        request.setTitle("updating a title");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PostDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PostDto> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.PUT, entity, PostDto.class);
        Assert.assertSame(response.getStatusCode(), HttpStatus.CREATED);
        PostDto responsePostDto = response.getBody();
        Assert.assertNotNull(responsePostDto);
        Assert.assertEquals(responsePostDto.getContent(), "updating a content");
        Assert.assertEquals(responsePostDto.getTitle(), "updating a title");
    }

    @Test
    @Order(4)
    public void shouldHaveOnePost() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<PostDto[]> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.GET, entity, PostDto[].class);
        Assert.assertSame(response.getStatusCode(), HttpStatus.OK);
        PostDto[] body = response.getBody();
        Assert.assertNotNull(body);
        Assert.assertSame(body.length, 1);
    }

    @Test
    @Order(5)
    public void deleteById() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<PostDto[]> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts/1"),
                HttpMethod.DELETE, entity, PostDto[].class);
        Assert.assertSame(response.getStatusCode(), HttpStatus.OK);
        // get Post
        ResponseEntity<PostDto> response1 = restTemplate.exchange(
                createURLWithPort("/blog-web/posts/1"),
                HttpMethod.GET, entity, PostDto.class);
        Assert.assertSame(response1.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}