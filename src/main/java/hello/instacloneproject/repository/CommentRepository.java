package hello.instacloneproject.repository;

import hello.instacloneproject.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Comment save(Comment comment){
        em.persist(comment);
        return comment;
    }

    public Comment findById(long id){
        return em.find(Comment.class, id);
    }

    public List<Comment> findByPostId(long id){
        return em.createQuery("select c from Comment c join fetch c.post join fetch c.user where c.post.id =: id ORDER BY c.dateTime")
                .setParameter("id",id)
                .getResultList();
    }

    public void delete(Comment comment){
        em.remove(comment);
    }
}
