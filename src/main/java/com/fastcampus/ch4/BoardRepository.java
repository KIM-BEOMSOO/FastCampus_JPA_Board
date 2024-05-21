package com.fastcampus.ch4;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Objects;

public interface BoardRepository extends CrudRepository<Board, Long> {

    //쿼리메서드 작성 S

    @Query(value = "SELECT title, writer FROM BOARD", nativeQuery = true)
    //컬럼을 일부만 가져올 경우, 반환타입을 Object로 해주어야 합니다.
    List<Object[]> findAllBoardBySQL2();

    //@Query에 nativeQuery속성을 true로 주면 value는 SQL문법이 됩니다
    //nativeQuery속성을 주지 않으면 JPQL문법이 됩니다.
    //JPQL과 SQL문법은 조금씩 다릅니다. (필요할 경우 구글링을 하여 알아보세요)
    @Query(value = "SELECT * FROM BOARD", nativeQuery = true)
    List<Board> findAllBoardBySQL(); //메서드 이름은 아무거나 해도 상관 없습니다.

    //아래는 JPQL문법입니다.
    //JPQL은 대소문자를 구분합니다. => 대소문자 주의
    //JPQL 문법이 따로 있습니다. (필요할 경우 검색하여 찾아보세요)
    @Query("SELECT b FROM Board b")
    List<Board> findAllBoard(); //메서드 이름은 아무거나 해도 상관 없습니다.


    //순서로 결정하기 => @Query로 JPQL 작성 (아래 문법은 JPQL문법입니다)
    @Query("SELECT b FROM Board b WHERE b.title=?1 AND b.writer=?2")
    List<Board> findByTitleAndWriter2(String title, String writer); //매개변수의 순서로 결정됩니다.

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
