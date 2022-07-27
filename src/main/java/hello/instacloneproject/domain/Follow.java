package hello.instacloneproject.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Follow {
    @Id
    @GeneratedValue
    @Column(name = "follow_id")
    private long id;

    @JoinColumn(name = "following_user_id")
    @ManyToOne
    private User followingUser;

    @JoinColumn(name ="followed_user_id")
    @ManyToOne
    private User followedUser;


    @Builder
    public Follow(User followingUser, User followedUser) {
        this.followingUser = followingUser;
        this.followedUser = followedUser;
    }

}
