package view.user;

import biz.user.Impl.UserDAO;
import biz.user.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    // GET 요청
    @RequestMapping(value="/login.do", method=RequestMethod.GET)
    public String loginView(@ModelAttribute("user") UserVO vo) {
        System.out.println("로그인 화면으로 이동");
        vo.setId("test");
        vo.setPassword("test123");
        return "login.jsp";

    }

    // POST 요청
    @RequestMapping(value="/login.do", method=RequestMethod.POST)
    public String login(UserVO vo, UserDAO userDAO, HttpSession session) {
        if(vo.getId() == null || vo.getId().equals("")){
            throw new IllegalArgumentException("아이디는 반드시 입력해야 합니다.");
        }
        UserVO user = userDAO.getUser(vo);
        if(user!=null){
            session.setAttribute("userName", user.getName());
            return "getBoardList.do";
        }else{
            return "login.jsp";
        }
    }
}
