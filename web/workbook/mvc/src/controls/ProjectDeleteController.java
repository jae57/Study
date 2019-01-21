package controls;

import annotation.Component;
import bind.DataBinding;
import dao.ProjectDao;

import java.util.Map;
@Component("/project/delete.do")
public class ProjectDeleteController implements Controller, DataBinding {
    ProjectDao projectDao;

    public ProjectDeleteController setProjectDao(ProjectDao projectDao){
        this.projectDao = projectDao;
        return this;
    }

    @Override
    public Object[] getDataBinders() {
        return new Object[]{"no",Integer.class};
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Integer no = (int)model.get("no");
        projectDao.delete(no);
        return "redirect:list.do";
    }
}
