package cn.ruihe.aluses.model.home;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.model.home.interceptor.AllowRole;
import cn.ruihe.aluses.model.home.service.ProjectService;
import cn.ruihe.aluses.model.home.vo.SimpleProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * @Author DHC
 * @Date 2015-07-20 14:58:41
 */
@Controller
@RequestMapping("pj")
public class PorjectController {

    @Autowired
    ProjectService projectService;

//    @AllowRole(1)
//    @RequestMapping(value = "",method = RequestMethod.GET)
//    public String getProjects(Model model) {
//        List<SimpleProjectOutput> outputs = projectService.getSimpleProjects();
//        model.addAttribute("pjs", outputs);
//        return "admin/pj";
//    }
    
    //added by cwx
    @AllowRole(1)
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getProjects(HttpSession session,Model model,@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "key", required = false) String key) {
    	if(page == null) {
            page = 1;
        }
        Map<String, String[]> pms = new HashMap<>();
        if(!StringUtils.isEmpty(key)) {
            pms.put("key", new String[]{key});
        } else {
            key = null;
        }
        Pager<SimpleProjectOutput> pager = projectService.getSimpleProjectsByPage(page, 12);
        model.addAttribute("pjs", pager.getDatas());
        model.addAttribute("pager", pager.pagesLink(pms));
        model.addAttribute("key", key);
        return "admin/pj";
    }

    @AllowRole(1)
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<SimpleProjectOutput> getProject(@PathVariable int id) {
        SimpleProjectOutput pj = projectService.getSimpleProject(id);
        return ApiResult.SUCCESS(pj);
    }

    @AllowRole(1)
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<SimpleProjectOutput> updateProject(SimpleProjectOutput project) {
        SimpleProjectOutput pj;
        if(project.getName()==null||project.getName().equals("")) {
            throw new MessageException("名称不能为空", 400);
        }
        if(project.getContactway()==null||project.getContactway().equals("")) {
            throw new MessageException("联系方式不能为空", 400);
        }
        if(project.getRemark()==null||project.getRemark().equals("")) {
            throw new MessageException("描述不能为空", 400);
        }
        if(project.getId() == null) {
            pj = projectService.addProject(project);
        } else {
            pj = projectService.updatePorject(project);
        }
        return ApiResult.SUCCESS(pj);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult deleteUser(@PathVariable int id) {
        int result = projectService.deletePorject(id);
        return ApiResult.SUCCESS(result);
    }
    
}
