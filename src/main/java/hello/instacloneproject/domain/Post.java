package hello.instacloneproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Likes> likeList=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Comment> commentList =new ArrayList<>();

    @Builder
    public Post(UploadFile postImgFile, String tag, String text,User user) {
        this.postImgFile = postImgFile;
        this.tag = tag;
        this.text = text;
        setUser(user);
    }
    // 연관관계 메서드
    private void setUser(User user){
        this.user = user;
        user.getPostList().add(this);
    }

    @PrePersist
    public void createDateTime(){
        this.dateTime = LocalDateTime.now();
    }
}
