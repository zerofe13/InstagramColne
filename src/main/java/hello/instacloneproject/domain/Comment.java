package hello.instacloneproject.domain;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime dateTime;

    @PrePersist
    public void createDate(){
        this.dateTime = LocalDateTime.now();
    }

    @Builder
    public Comment(String text, User user, Post post) {
        this.text = text;
        this.user = user;
        setPost(post);
    }

    //연관관계메서드
    private void setPost(Post post){
        this.post = post;
        post.getCommentList().add(this);
    }
}
