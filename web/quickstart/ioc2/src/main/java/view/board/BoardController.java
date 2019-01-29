package view.board;

import biz.board.BoardListVO;
import biz.board.BoardService;
import biz.board.BoardVO;
import biz.board.Impl.BoardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @RequestMapping("/dataTransform.do")
    @ResponseBody
    public BoardListVO dataTransform(BoardVO vo){
        vo.setSearchCondition("TITLE");
        vo.setSearchKeyword("");
        List<BoardVO> boardList= boardService.getBoardList(vo);
        BoardListVO boardListVO = new BoardListVO();
        boardListVO.setBoardList(boardList);
        return boardListVO;
    }

    // 글 등록
    @RequestMapping(value="/insertBoard.do")
    public String insertBoard(BoardVO vo) throws IOException {
        // 파일 업로드 처리
        MultipartFile uploadFile = vo.getUploadFile();
        if(!uploadFile.isEmpty()){
            String fileName = uploadFile.getOriginalFilename();
            uploadFile.transferTo(new File("D:/"+fileName));
        }
        boardService.insertBoard(vo);
        return "getBoardList.do";
    }

    // 검색 조건 목록 설정
    @ModelAttribute("conditionMap")
    public Map<String, String> searchConditionMap(){
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("제목", "TITLE");
        conditionMap.put("내용","CONTENT");
        return conditionMap;
    }

    // 글 목록 검색
    @RequestMapping("/getBoardList.do")
    public String getBoardList(/*@RequestParam(value="searchCondition", defaultValue="TITLE", required=false) String condition, @RequestParam(value="searchKeyword", defaultValue="",required=false) String keyword, */BoardVO vo, Model model) {
        if(vo.getSearchCondition() == null) vo.setSearchCondition("TITLE");
        if(vo.getSearchKeyword() == null) vo.setSearchKeyword("");
        model.addAttribute("boardList",boardService.getBoardList(vo));
        return "getBoardList.jsp";
    }

    // 글 상세 조회
    @RequestMapping("/getBoard.do")
    public String getBoard(BoardVO vo, Model model) {
        model.addAttribute("board",boardService.getBoard(vo)); // Model 에 정보저장
        return "getBoard.jsp";
    }

    // 글 수정
    @RequestMapping("/updateBoard.do")
    public String updateBoard(@ModelAttribute("board") BoardVO vo) {
        System.out.println("번호 : "+vo.getSeq());
        System.out.println("제목 : "+vo.getTitle());
        System.out.println("작성자 : "+vo.getWriter());
        System.out.println("내용 : "+vo.getContent());
        System.out.println("등록일 : "+vo.getRegDate());
        System.out.println("조회수 : "+vo.getCnt());
        boardService.updateBoard(vo);
        return "getBoardList.do";
    }

    // 글 삭제
    @RequestMapping("/deleteBoard.do")
    public String deleteBoard(BoardVO vo) {
        boardService.deleteBoard(vo);
        return "getBoardList.do";
    }

}
