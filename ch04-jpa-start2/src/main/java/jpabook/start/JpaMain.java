package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {
            tx.begin();
            save(em);
//            tx.commit();//트랜잭션 커밋
            find(em);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void save(EntityManager entityManager) {
        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        entityManager.persist(productA);

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("회원1");
        member1.getProducts().add(productA);
        entityManager.persist(member1);

        entityManager.flush();
        entityManager.detach(member1);
    }

    public static void find(EntityManager entityManager) {
        Member member = entityManager.find(Member.class, "member1");
        List<Product> products = member.getProducts();
        for (Product product : products) {
            System.out.println("product.name : " + product.getName());
        }
    }
}