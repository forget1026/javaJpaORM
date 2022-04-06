package jpabook.start;

import javax.persistence.*;

@Entity
//@SequenceGenerator(
//        name = "BOARD_SEQ_GENERATOR",
//        sequenceName = "BOARD_SEQ",
//        initialValue = 1, allocationSize = 1
//)
@TableGenerator(
        name = "BOARD_SEQ_GENERATOR",
        table = "MY_SEQUENCE",
        pkColumnValue = "BOARD_SEQ", allocationSize = 1
)
public class Board {
    @Id
//    IDENTIY 전략
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    SEQUENCE 전략
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
//    TABLE 전략
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
