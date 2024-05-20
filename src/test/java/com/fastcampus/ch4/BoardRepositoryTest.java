package com.fastcampus.ch4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

// 1. TDD작성 시 @SpringBootTest 필수 입력
@SpringBootTest
class BoardRepositoryTest {


    // 2. BoardRepository를 테스트 할 것이기 때문에 해당 인터페이스를 주입받습니다
    @Autowired
    private BoardRepository boardRepo;

    @Test
    public void insertTest(){

        // 3. 값을 저장할 @Entity를 객체화 합니다.
        Board board = new Board();

        // 4. 값 세팅
        board.setBno(1L);
        board.setTitle("Test Title");
        board.setContent("This is Test");
        board.setWriter("aaa");
        board.setViewCnt(0L);
        board.setInDate(new Date());
        board.setUpDate(new Date());

        // 5. DB에 값 저장(save() 실행)
        boardRepo.save(board);


    }
}