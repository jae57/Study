package controls;

import annotation.Component;
import bind.DataBinding;
import dao.MemberDao;
import vo.Member;

import java.util.Map;

@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding {
    MemberDao memberDao;

    public MemberAddController setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
        return this;
    }

    public Object[] getDataBinders(){
        return new Object[]{
                "member", vo.Member.class
        };
    }

    public String execute(Map<String, Object> model)throws Exception{
        Member member = (Member)model.get("member");
        if(member.getEmail()==null){
            return "/member/MemberForm.jsp";
        }else{
            memberDao.insert(member);
            return "redirect:list.do";
        }
    }
}
