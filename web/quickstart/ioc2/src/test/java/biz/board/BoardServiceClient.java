package biz.board;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class BoardServiceClient {
    public static void main(String[] args) {
        // 1. 스프링 컨테이너를 구동한다
        AbstractApplicationContext container = new GenericXmlApplicationContext("spring/applicationContext.xml");
        // 2. Spring 컨테이너로부터 BoardServiceImpl객체를 Lookup한다
        BoardService boardService = (BoardService)container.getBean("boardService");

        // 3. 글 등록 테스트
        BoardVO vo = new BoardVO();
        //vo.setSeq(100);
        vo.setTitle("제목");
        vo.setWriter("글쓴이");
        vo.setContent("내용 ...");
        boardService.insertBoard(vo);

        // 4. 글 목록 검색 테스트
        List<BoardVO> boardList = boardService.getBoardList(vo);
        for (BoardVO board : boardList){
            System.out.println("---> "+board.toString());
        }

        // 5. Spring컨테이너 종료
        container.close();
    }
}
