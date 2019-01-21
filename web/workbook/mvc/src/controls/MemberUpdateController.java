package controls;

import annotation.Component;
import bind.DataBinding;
import dao.MemberDao;
import vo.Member;

import java.util.Map;

@Component("/member/update.do")
public class MemberUpdateController implements Controller, DataBinding {
    MemberDao memberDao;

    public MemberUpdateController setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
        return this;
    }

    public Object[] getDataBinders(){
        return new Object[]{"no",Integer.class,"member",vo.Member.class};
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Member member = (Member)model.get("member");
        Integer no = (int)(model.get("no"));
        if(member.getEmail() == null){
            model.put("member", memberDao.selectOne(no));
            return "/member/MemberUpdateForm.jsp";
        } else {
            memberDao.update(member);
            return "redirect:list.do";
        }

    }
}
