package mvc;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private Map<String, Controller> mappings;

    // 생성과 동시에 Map 초기화
    public HandlerMapping(){
        mappings = new HashMap<String, Controller>();
        mappings.put("/login.do", new LoginController());
        mappings.put("/getBoardList.do",new GetBoardListController());
        mappings.put("/getBoard.do",new GetBoardController());
        mappings.put("/insertBoard.do",new InsertBoardController());
        mappings.put("/updateBoard.do",new UpdateBoardController());
        mappings.put("/deleteBoard.do", new DeleteBoardController());
        mappings.put("/logout.do",new LogoutController());
    }

    public Controller getController(String path){
        return mappings.get(path);
    }
}
