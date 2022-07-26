package hello.instacloneproject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "Post_id")
    private long id;

    @Embedded
    private UploadFile postImgFile;

    private String tag;
    private String text;



    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public void setPostImgFile(UploadFile postImgFile) {
        this.postImgFile = postImgFile;
    }

    @Builder
    public Post(UploadFile postImgFile, String tag, String text,User user) {
        this.postImgFile = postImgFile;
        this.tag = tag;
        this.text = text;
        setUser(user);
    }
    // 연관관계 메서드
    public void setUser(User user){
        this.user = user;
        user.getPostList().add(this);
    }

    @PrePersist
    public void createDateTime(){
        this.dateTime = LocalDateTime.now();
    }
}
