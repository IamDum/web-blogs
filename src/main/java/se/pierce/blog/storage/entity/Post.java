package se.pierce.blog.storage.entity;

import javax.persistence.*;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @SequenceGenerator(name = "post_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String content;

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
