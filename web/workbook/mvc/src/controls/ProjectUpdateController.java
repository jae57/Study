package controls;

import annotation.Component;
import bind.DataBinding;
import dao.ProjectDao;
import vo.Project;

import java.util.Map;

@Component("/project/update.do")
public class ProjectUpdateController implements Controller, DataBinding {
    ProjectDao projectDao;

    public ProjectUpdateController setProjectDao(ProjectDao projectDao){
        this.projectDao = projectDao;
        return this;
    }
    @Override
    public Object[] getDataBinders() {
        return new Object[]{"no", Integer.class, "project",vo.Project.class};
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Project project = (Project)model.get("project");
        if(project.getTitle() == null){
            Integer no = (int)model.get("no");
            Project detailInfo = projectDao.selectOne(no);
            model.put("project", detailInfo);
            return "/project/ProjectUpdateForm.jsp";
        }else{
            projectDao.update(project);
            return "redirect:list.do";
        }

    }
}
