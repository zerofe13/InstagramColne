package hello.instacloneproject.repository;

import hello.instacloneproject.domain.Likes;
import hello.instacloneproject.domain.Post;
import hello.instacloneproject.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikesRepository {

    private final EntityManager em;

    public void save(Post post, User user){
        Likes likes = Likes.builder().post(post).user(user).build();
        em.persist(likes);
    }

    public Likes findById(Long id){
        return em.find(Likes.class,id);
    }

    public List<Likes> findByPostId(Long postId){
        return em.createQuery("select l from Likes l join fetch l.post  where l.post.id =:postId",Likes.class)
                .setParameter("postId",postId)
                .getResultList();
    }

    public List<Likes> CountByPostId(Long postId){
        return em.createQuery("select count (l) from Likes l join fetch l.post  where l.post.id =:postId",Likes.class)
                .setParameter("postId",postId)
                .getResultList();
    }

    public List<Likes> findByUserEmail(String userEmail){
        return em.createQuery("select l from Likes l join fetch l.user where l.user.email =:userEmail",Likes.class)
                .setParameter("userEmail",userEmail)
                .getResultList();
    }

    public Optional<Likes> findByPostIdAndUserEmail(Long postId, String userEmail){
        return em.createQuery("select l from Likes l join fetch l.post join fetch l.user where l.user.email =:userEmail and l.post.id =:postId",Likes.class)
                .setParameter("userEmail",userEmail)
                .setParameter("postId",postId)
                .getResultList().stream().findFirst();
    }
    public void delete(Long postId,String userEmail){
        Optional<Likes> findLikes = findByPostIdAndUserEmail(postId, userEmail);
        em.remove(findLikes.get());
    }
}
