package cn.ruihe.aluses.model.home.service;

import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.model.home.repository.ProjectRepository;
import cn.ruihe.aluses.model.home.vo.SimpleProjectOutput;
import cn.ruihe.aluses.model.readilytake.repository.CommonRepository;
import cn.ruihe.utils.UsefulTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author DHC
 * @Date 2015-07-18 14:50:01
 */
@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CommonRepository commonRepository;

    public List<SimpleProjectOutput> getSimpleProjects() {
        return projectRepository.getSimpleProjects();
    }
    
    public Pager<SimpleProjectOutput> getSimpleProjectsByPage(int page, int size) {
    	int count = projectRepository.getProjectCount();
        return projectRepository.getSimpleProjectsByPage(page, size, count);
    }

    public SimpleProjectOutput getSimpleProject(int id) {
        return projectRepository.getSimpleProject(id);
    }

    public SimpleProjectOutput addProject(SimpleProjectOutput project) {
        int nid = commonRepository.getNewId("ORGANIZATIONS", "ID");
        project.setId(nid);
        String[] pinyin = UsefulTool.getPinyin(project.getName());
        project.setFullpinyin(pinyin[0]);
        project.setSimplepinyin(pinyin[1]);
        return projectRepository.addProject(project);
    }

    public SimpleProjectOutput updatePorject(SimpleProjectOutput project) {
        String[] pinyin = UsefulTool.getPinyin(project.getName());
        project.setFullpinyin(pinyin[0]);
        project.setSimplepinyin(pinyin[1]);
        return projectRepository.updatePorject(project);
    }
    
    public int deletePorject(int id) {
    	return projectRepository.deletePorject(id);
    }
    
    
}
