package com.fastcampus.ch4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// TDD작성 시 @SpringBootTest 필수 입력
@SpringBootTest
// TDD 순서 지정할 수 있게 해주는 애너테이션
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryTest2 {


    // BoardRepository를 테스트 할 것이기 때문에 해당 인터페이스를 주입받습니다
    // BoardRepository는 CrudRepository인터페이스를 extends 받고 있고
    // CrudRepository 내부에 있는 추상 메서드를 Spring Data가 구현을 해 줍니다.
    // 즉, CrudRepository를 extends 받은 뒤, Spring Data 라이브러리를 추가하면
    // CrudRepository 내부에 선언된 추상 메서드를 사용할 수 있습니다
    @Autowired
    private BoardRepository boardRepo;


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



    //3.쿼리 메서드 삭제 테스트
    @Test
    public void delete(){
        //3-1.Writer 컬럼의 값이 writer1인 놈들을 삭제했을 경우, 삭제된 행의 수가 20이면 테스트 통과
        assertTrue(boardRepo.deleteByWriter("writer1")==20);

        //3-2.Board에서 Writer 컬럼의 값이 writer1인 것들을 찾아옵니다.
        List<Board> list = boardRepo.findByWriter("writer1");

        //3-3.write1데이터를 모두 삭제했으니 결과가 0이 나와야 테스트 통과입니다
        assertTrue(list.size() == 0);


    }

    //2.쿼리 메서드 조회 테스트
    @Test
    public void findTest(){

        //2-1.Board에서 Writer 컬럼의 값이 writer1인 것들을 찾아옵니다.
        List<Board> list = boardRepo.findByWriter("writer1");

        //2-2.list의 size가 20이라면 통과
        assertTrue(list.size()==20);

        //2-3.list의 목록을 모두 콘솔에 출력합니다.
        list.forEach(System.out::println);
    }

    //1.testData()로 데이터가 잘 들어갔는지 확인합니다
    //쿼리 메서드 데이터 count 테스트
    @Test
    public void countTest(){
        assertTrue(boardRepo.countAllByWriter("writer1")==20);

    }
}