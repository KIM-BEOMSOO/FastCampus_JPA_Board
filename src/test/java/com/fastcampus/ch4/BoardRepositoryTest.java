package com.fastcampus.ch4;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

// TDD작성 시 @SpringBootTest 필수 입력
@SpringBootTest
// TDD 순서 지정할 수 있게 해주는 애너테이션
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryTest {


    // BoardRepository를 테스트 할 것이기 때문에 해당 인터페이스를 주입받습니다
    // BoardRepository는 CrudRepository인터페이스를 extends 받고 있고
    // CrudRepository 내부에 있는 추상 메서드를 Spring Data가 구현을 해 줍니다. 
    // 즉, CrudRepository를 extends 받은 뒤, Spring Data 라이브러리를 추가하면 
    // CrudRepository 내부에 선언된 추상 메서드를 사용할 수 있습니다
    @Autowired
    private BoardRepository boardRepo;

    // 4.BoardRepository 인터페이스를 이용한 데이터 삭제 테스트
    @Test
    @Order(4)
    public void deleteTest(){
        // 4-1.@Id의 값이 1L인 행을 삭제
        boardRepo.deleteById(1L);

        // 4-2.정상적으로 삭제됐는지 여부 확인
        Board board = boardRepo.findById(1L).orElse(null);
        
        // 4-3.null이면 정상삭제 => 테스트 통과
        assertTrue(board==null);
    }
    

    // 3.BoardRepository 인터페이스를 이용한 데이터 업데이트 테스트
    @Test
    @Order(3)
    public void updateTest(){
        // 3-1.BoardRepository 인터페이스의 findById메서드를 사용하여 @Id의 값이 1L인 게시글을 찾고, 없을 경우 null 반환
        Board board = boardRepo.findById(1L).orElse(null);

        // 3-2.1차 테스트 확인
        assertTrue(board!=null);

        // 3-3.데이터 수정
        board.setTitle("modified Title");

        // 3-4.수정한 데이터 저장 (save()사용)
        boardRepo.save(board);

        // 3-5.@Id의 값이 1L인 게시글이 없으면 새로운 Board객체 반환 (@@?)
        Board board2 = boardRepo.findById(1L).orElse(new Board());

        // 3-6.확인
        System.out.println("board = " + board);
        System.out.println("board2 = " + board2);

        // 3-7.변경한 데이터와 select한 데이터의 값이 같으면 통과
        assertTrue(board.getTitle().equals(board2.getTitle()));
        
    }

    // 2.BoardRepository 인터페이스를 이용한 데이터 조회 테스트
    @Test
    @Order(2)
    public void selectTest(){
        // 2-1.findById메서드를 사용하여 @Id컬럼의 값이 1L인 것을 찾고, 없을 경우 null을 반환
        Board board = boardRepo.findById(1L).orElse(null);

        // 2-2.board값 확인
        System.out.println("board = " + board);

        // 2-3.board에 들어간 값이 null이 아니면 테스트 통과
        assertTrue(board!=null);
    }


    // 1.BoardRepository 인터페이스를 이용한 테이터 저장 테스트
    @Test
    @Order(1)
    public void insertTest(){

        // 1-1.값을 저장할 @Entity를 객체화 합니다.
        Board board = new Board();

        // 1-2.값 세팅
        board.setBno(1L);
        board.setTitle("Test Title");
        board.setContent("This is Test");
        board.setWriter("aaa");
        board.setViewCnt(0L);
        board.setInDate(new Date());
        board.setUpDate(new Date());

        // 1-3.DB에 값 저장(save() 실행)
        boardRepo.save(board);


    }
}