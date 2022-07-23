package hello.instacloneproject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Follow {
    @Id
    @GeneratedValue
    private long id;

    @JoinColumn(name = "follwing_user_id")
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