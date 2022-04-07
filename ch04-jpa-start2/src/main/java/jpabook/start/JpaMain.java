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
            tx.begin(); //트랜잭션 시작
//            loicIdentity(em);  //비즈니스 로직
            testSave(em);
            queryLogicJoin(em);
            updateRelation(em);
            deleteRelation(em);
            tx.commit();//트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void loicIdentity(EntityManager entityManager) {
        Board board = new Board();
        entityManager.persist(board);
        System.out.println("board.id = " + board.getId());
    }

    public static void logic(EntityManager em) {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        //등록
        em.persist(member);

        //수정
        member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age = " + findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        //삭제
        em.remove(member);
    }

    public static void testSave(EntityManager entityManager) {
        Team team1 = new Team("team1", "팀1");
        entityManager.persist(team1);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        entityManager.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        entityManager.persist(member2);
    }

    public static void queryLogicJoin(EntityManager entityManager) {
        String jpql = "select m from Member m join m.team t where t.name =:teamName";
        List<Member> list = entityManager.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for(Member member : list) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
    }

    public static void updateRelation(EntityManager entityManager) {
        Team team2 = new Team("team2", "팀2");
        entityManager.persist(team2);

        Member member = entityManager.find(Member.class, "member1");
        member.setTeam(team2);
    }

    public static void deleteRelation(EntityManager entityManager) {
        Member member2 = entityManager.find(Member.class, "member2");
        member2.setTeam(null);
    }
}
