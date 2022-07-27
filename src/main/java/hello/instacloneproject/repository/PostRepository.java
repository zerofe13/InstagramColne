package hello.instacloneproject.repository;

import hello.instacloneproject.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public Post findById(Long postId){
        return em.createQuery("select p from Post p join fetch p.user where p.id =:postId",Post.class)
                .setParameter("postId",postId)
                .getSingleResult();
    }

    public Post findByIdWithLikesList(Long postId){
        return em.createQuery("select p from Post p left join fetch p.user left join fetch p.likeList where p.id =:postId",Post.class)
                .setParameter("postId",postId)
                .getSingleResult();
    }

    public void delete(Post post){
        em.remove(post);
    }
}
