package com.fastcampus.ch4;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

// TDD작성 시 @SpringBootTest 필수 입력
@SpringBootTest
// TDD 순서 지정할 수 있게 해주는 애너테이션
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryTest3 {


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

    //6.@Query에 navtiveQuery=true 속성 적용하여 SQL 작성하기
    @Test
    @DisplayName("@Query로 네이티브 쿼리(SQL) 작성 테스트2 => 일부 컬럼 가져오기") //테스트 이름이 변경됩니다.
    public void queryAnnoTest5(){
        List<Object[]> list = boardRepo.findAllBoardBySQL2();
//      list.forEach(System.out::println); <= 이렇게 작성하면 메모리 주소가 찍힘. => toString으로 변환해야 함
        list.stream()
                .map(arr-> Arrays.toString(arr)) //arr을 String으로 변환
                .forEach(System.out::println);
        assertTrue(list.size()==100);
    }

    //5.@Query에 navtiveQuery=true 속성 적용하여 SQL 작성하기
    @Test
    @DisplayName("@Query로 네이티브 쿼리(SQL) 작성 테스트") //테스트 이름이 변경됩니다.
    public void queryAnnoTest4(){
        List<Board> list = boardRepo.findAllBoardBySQL();
        assertTrue(list.size()==100);

    }

    //4.@Query 사용하여 JPQL 작성하기 - 여러 조건 - 매개변수 이름
    @Test
    @DisplayName("@Query로 JPQL 작성 테스트 - 매개변수 이름") //테스트 이름이 변경됩니다.
    public void queryAnnoTest3(){
        //BoardRepository에 선언한 @Query 메서드 실행
        //("SELECT b FROM Board b.title=:title AND b.writer=:writer")
        List<Board> list = boardRepo.findByTitleAndWriter2("title1","writer1");
        assertTrue(list.size()==1);
    }

    //3.@Query 사용하여 JPQL 작성하기 - 여러 조건 - 매개변수 순서
    @Test
    @DisplayName("@Query로 JPQL 작성 테스트 - 매개변수 순서") //테스트 이름이 변경됩니다.
    public void queryAnnoTest2(){
        //BoardRepository에 선언한 @Query 메서드 실행 ("SELECT b FROM Board b.title=?1 AND b.writer=?2")
        List<Board> list = boardRepo.findByTitleAndWriter2("title1","writer1");
        list.forEach(System.out::println);
        assertTrue(list.size()==1);
    }

    //2.@Query 사용하여 JPQL 작성하기 - 하나의 조건
    @Test
    @DisplayName("@Query로 JPQL 작성 테스트") //테스트 이름이 변경됩니다.
    public void queryAnnoTest(){
        //BoardRepository에 선언한 @Query 메서드 실행 (SELECT * FROM Board)
        List<Board> list = boardRepo.findAllBoard();
        assertTrue(list.size()==100);
    }

    //1.createQuery 사용하여 JPQL 작성하기
    //복잡한 쿼리를 작성할 때에 JPQL을 직접 작성하는 테스트입니다.
    //자주 사용 안함. (@Query를 더 많이 사용합니다. => 해당 부분은 참고용(이런게 있구나~))
    @Test
    @DisplayName("createQuery로 JPQL 작성 테스트") //테스트 이름이 변경됩니다.
    public void createQueryTest(){
        String query = "SELECT b FROM Board b";
        TypedQuery<Board> tQuery = em.createQuery(query, Board.class);
        List<Board> list = tQuery.getResultList();

        list.forEach(System.out::println);
        assertTrue(list.size()==100);
    }
}