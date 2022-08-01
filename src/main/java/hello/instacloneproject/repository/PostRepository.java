package hello.instacloneproject.repository;

import hello.instacloneproject.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    /**
     * 페치조인에 페이징을 사용 할 경우
     * row에 영향을 주지않는 to one 관계만 먼저 페치조인을 한후 to many 관계인 Likes 나중에 맵핑해줌
     */
    public List<Post> findStory(String userEmail, Pageable pageable){
        return em.createQuery("select p from Post p" +
                        " join fetch p.user where p.user in (select f.followedUser from Follow f where f.followingUser.email =:userEmail) order by p.dateTime DESC ", Post.class)
                .setParameter("userEmail", userEmail)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

    }

    /**
     * db에 쉼표로 tag 저장되어 있기때문에
     * ,를 기준으로 tag를 검색한다.
     * ex) a,b,c,d
     */
    public List<Post> tagSearch(String tag, Pageable pageable){
        return em.createQuery("select p from Post p join fetch p.user where p.tag like :tag or p.tag like concat('%,',:tag,',%') " +
                "or p.tag like concat(:tag,',%') or p.tag like concat('%,',:tag) order by p.dateTime desc ",Post.class)
                .setParameter("tag",tag)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    public List<Post> findLikePost(String userEmail,Pageable pageable){
        return em.createQuery("select p from Post p join fetch p.user " +
                        "where p in (select l.post from Likes l where l.user.email =:userEmail) order by p.dateTime DESC ",Post.class)
                .setParameter("userEmail",userEmail)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    public void delete(Post post){
        em.remove(post);
    }
}
