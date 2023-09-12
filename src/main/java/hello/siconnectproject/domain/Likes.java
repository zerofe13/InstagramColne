package hello.siconnectproject.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Likes {

    @Id
    @GeneratedValue
    @Column(name="likes_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Likes(Post post, User user) {
        setPost(post);
        this.user = user;
    }

    private void setPost(Post post){
        this.post = post;
        post.getLikeList().add(this);
    }
}
