package controls;

import annotation.Component;
import bind.DataBinding;
import dao.ProjectDao;
import vo.Project;

import java.util.Map;

@Component("/project/add.do")
public class ProjectAddController implements Controller, DataBinding {
    ProjectDao projectDao;

    public ProjectAddController setProjectDao(ProjectDao projectDao){
        this.projectDao = projectDao;
        return this;
    }

    @Override
    public Object[] getDataBinders() {
        return new Object[]{"project",vo.Project.class};
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Project project = (Project)model.get("project");
        if(project.getTitle() == null){
            return "/project/ProjectForm.jsp";
        }else {
            projectDao.insert(project);
            return "redirect:list.do";
        }

    }


}
