package com.fastcampus.ch4;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

//Qboard import
import static com.fastcampus.ch4.QBoard.board;

// TDD작성 시 @SpringBootTest 필수 입력
@SpringBootTest
// TDD 순서 지정할 수 있게 해주는 애너테이션
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryTest4 {


    // BoardRepository를 테스트 할 것이기 때문에 해당 인터페이스를 주입받습니다
    // BoardRepository는 CrudRepository인터페이스를 extends 받고 있고
    // CrudRepository 내부에 있는 추상 메서드를 Spring Data가 구현을 해 줍니다.
    // 즉, CrudRepository를 extends 받은 뒤, Spring Data 라이브러리를 추가하면
    // CrudRepository 내부에 선언된 추상 메서드를 사용할 수 있습니다
    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    public EntityManager em;


    //매 테스트마다 실행시켜주는 애너테이션입니다.
    //보통 테스트 데이터를 삽입할 때에 사용합니다
    @BeforeEach
    public void testData(){
        //1~100개의 데이터를 삽입합니다
        for(int i = 1; i <= 100; i++){
            Board board = new Board();
            board.setBno((long)i);
            board.setTitle("title"+i);
            board.setContent("content"+i);
            board.setWriter("writer"+(i%5));
            board.setViewCnt((long)(Math.random()*100));
            board.setInDate(new Date());
            board.setUpDate(new Date());
            //값을 저장하여 DB에 적용합니다
            boardRepo.save(board);
        }
    }

    @Test
    @DisplayName("querydsl로 쿼리 작성 테스트3 - 동적 쿼리작성") //테스트 이름이 변경됩니다.
    public void querydslTest3() {
        String searchBy = "TC"; //제목(title)과 작성내용(content)에서 검색
        String keyword = "1";
        keyword = "%" + keyword + "%";

        BooleanBuilder builder = new BooleanBuilder();

        if(searchBy.equalsIgnoreCase("T")){
            builder.and(board.title.like(keyword));
        }else if (searchBy.equalsIgnoreCase("C")){
            builder.and(board.content.like(keyword));
        }else if (searchBy.equalsIgnoreCase("TC")) {
            builder.and(board.title.like(keyword).or(board.content.like(keyword)));
        }

        JPAQueryFactory qf = new JPAQueryFactory(em);
        JPAQuery query = qf.selectFrom(board)
                .where(builder)
                .orderBy(board.upDate.desc());

        List<Board> list = query.fetch();
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("querydsl로 쿼리 작성 테스트2 - 복잡한 쿼리작성") //테스트 이름이 변경됩니다.
    public void querydslTest2() {
        JPAQueryFactory qf = new JPAQueryFactory(em);

        JPAQuery<Tuple> query = qf.select(board.writer, board.viewCnt.sum()).from(board)
                .where(board.title.notLike("title1%"))
                .where(board.writer.eq("writer1"))
                .where(board.content.contains("content"))
                .where(board.content.isNotNull())
                .groupBy(board.writer)
                .having(board.viewCnt.sum().gt(100)) //조회수의 종합이 100이 넘는 작성자
                .orderBy(board.writer.asc())
                .orderBy(board.viewCnt.sum().desc());

        List<Tuple> list = query.fetch();
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("querydsl로 쿼리 작성 테스트1 - 간단한 쿼리작성") //테스트 이름이 변경됩니다.
    public void querydslTest() {


//        QBoard board = QBoard.board;
        //1.JPAQueryFactory를 생성
        JPAQueryFactory qf = new JPAQueryFactory(em);

        //2.쿼리 작성
        JPAQuery<Board> query =qf.selectFrom(board)
                .where(board.title.eq("title1"));

        //3.쿼리 실행
        List<Board> list = query.fetch();
        list.forEach(System.out::println);
    }
}