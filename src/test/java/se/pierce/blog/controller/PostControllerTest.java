package se.pierce.blog.controller;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import se.pierce.blog.Application;
import se.pierce.blog.model.PostDto;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void postsNotFound() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<PostDto> response = restTemplate.exchange(
                createURLWithPort("/blog-web/posts"),
                HttpMethod.GET, entity, PostDto.class);

        Assert.assertSame(response.getStatusCode(), Matchers.is(404));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}