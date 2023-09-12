package hello.siconnectproject.repository;

import hello.siconnectproject.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
        return em.createQuery("select c from Comment c join fetch c.user join fetch c.post where c.post.id =: id ORDER BY c.dateTime",Comment.class)
                .setParameter("id",id)
                .getResultList();
    }

    public void delete(Comment comment){
        em.remove(comment);
    }
}
