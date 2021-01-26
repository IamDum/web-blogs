package se.pierce.blog.controller;

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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostControllerTest {

    private static final String EXPECTED_TITLE = "Adding title";
    private static final String EXPECTED_CONTENT = "Writing a content";
    private static final String UPDATED_CONTENT = "updating a content";
    private static final String UPDATED_TITLE = "updating a title";
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
        assertSame(response.getStatusCode(), HttpStatus.OK);
        PostDto[] body = response.getBody();
        assertSame(body.length, 0);
    }

    @Test
    @Order(2)
    public void addPost() {
        PostDto request = new PostDto();
        request.setContent(EXPECTED_CONTENT);
        request.setTitle(EXPECTED_TITLE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PostDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.POST, entity, Void.class);
        assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(3)
    public void getPostById() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<PostDto> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts/1"),
                HttpMethod.GET, entity, PostDto.class);
        assertSame(response.getStatusCode(), HttpStatus.OK);

        PostDto responsePostDto = response.getBody();
        assertNotNull(responsePostDto);
        assertEquals(responsePostDto.getTitle(), EXPECTED_TITLE);
        assertEquals(responsePostDto.getContent(), EXPECTED_CONTENT);
    }

    @Test
    @Order(3)
    public void updatePost() {
        PostDto request = new PostDto();
        request.setId("1");
        request.setContent(UPDATED_CONTENT);
        request.setTitle(UPDATED_TITLE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PostDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PostDto> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.PUT, entity, PostDto.class);
        assertSame(response.getStatusCode(), HttpStatus.CREATED);
        PostDto responsePostDto = response.getBody();
        assertNotNull(responsePostDto);
        assertEquals(responsePostDto.getContent(), UPDATED_CONTENT);
        assertEquals(responsePostDto.getTitle(), UPDATED_TITLE);
    }

    @Test
    @Order(3)
    public void updatePostRequestWhenPostNotFound() {
        PostDto request = new PostDto();
        String idWhichIsNotInDB = "100009";
        request.setId(idWhichIsNotInDB);
        request.setContent(UPDATED_CONTENT);
        request.setTitle(UPDATED_TITLE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PostDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PostDto> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.PUT, entity, PostDto.class);
        assertSame(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(4)
    public void shouldHaveOnePost() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<PostDto[]> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.GET, entity, PostDto[].class);
        assertSame(response.getStatusCode(), HttpStatus.OK);
        PostDto[] body = response.getBody();
        assertNotNull(body);
        assertSame(body.length, 1);
    }

    @Test
    @Order(5)
    public void deleteById() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<PostDto[]> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts/1"),
                HttpMethod.DELETE, entity, PostDto[].class);
        assertSame(response.getStatusCode(), HttpStatus.OK);
        // get Post
        ResponseEntity<PostDto> response1 = restTemplate.exchange(
                createURLWithPort("/blog-web/posts/1"),
                HttpMethod.GET, entity, PostDto.class);
        assertSame(response1.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}