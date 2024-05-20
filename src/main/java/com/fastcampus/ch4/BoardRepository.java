package com.fastcampus.ch4;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {

    //쿼리메서드 작성 S

    //SELECT COUNT(*) FROM BOARD WHERE WRITER = :writer
    //원래는 countAllBoardByWriter으로 작성해야 함
    //하지만 Board(@Entity클래스 이름)은 상단 class에 CrudRepository<Board, Long>를 extends해줬으므로 생략 가능
    //쿼리메서드 작명 규칙 : CrudRepository에서 제공하는 추상메서드 + @Entity클래스 이름(생략가능) + By + 컬럼명(매개변수)
    int countAllByWriter(String writer);


    //SELECT * FROM BOARD WHERE WRITER = :writer
    //질문)콜론(:)은 무엇인가요? => 답변)JPQL문법입니다. 크게 신경쓰지 않으셔도 돼요
    List<Board> findByWriter(String writer);


    //SELECT * FROM BOARD WHERE TITLE = :title AND WRITER = :writer
    //조건을 여러개 주고 싶을 경우, 아래와 같이 메서드 이름에 And 작성 및 매개변수를 2개 이상 작성합니다
    List<Board> findByTitleAndWriter(String title, String writer);


    //DELETE FROM BOARD WHERE WRITER = :writer
    //반환타입이 int인 이유는 삭제에 성공한 행의 수가 반환되기 때문입니다
    @Transactional //삭제 메서드에는 @Transactional 애너테이션은 필수입니다
    int deleteByWriter(String writer);
}
