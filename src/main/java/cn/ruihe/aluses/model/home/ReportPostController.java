/**
 * 
 */
package cn.ruihe.aluses.model.home;

import java.util.List;
import java.util.Objects;

import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.entity.Reporttype;
import cn.ruihe.aluses.entity.SysReadilyTtake;
import cn.ruihe.aluses.model.home.interceptor.AllowRole;
import cn.ruihe.aluses.model.home.service.ProjectService;
import cn.ruihe.aluses.model.home.service.ReportPostService;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeTagOutput;
/**
 * @Author DHC
 * @Date 2015-07-18 17:07:51
 */
@Controller
@RequestMapping("rp")
public class ReportPostController {
	
	@Autowired
	ReportPostService reportPostService;

    @Autowired
    ProjectService projectService;
    
    @AllowRole(1)
    @RequestMapping(value = "tags", method = RequestMethod.GET)
    public String getTags(Model model) {
        List<Reporttype> tags =  reportPostService.getReportPostTagList();
        model.addAttribute("tags", tags);
        return "admin/rp_tags";
    }

    @AllowRole(1)
    @RequestMapping(value = "tags", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult auTag(Reporttype tag) {
        if(tag.getTypename() == null || Objects.equals(tag.getTypename(), "")) {
            throw new MessageException("标签名称不能为空", 400);
        }
        Reporttype ttake;
        if(tag.getId() == null) {
            ttake = reportPostService.addTag(tag);
        } else {
            ttake = reportPostService.updateTag(tag);
        }
        return ApiResult.SUCCESS(ttake);
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "tags/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<Reporttype> getTag(@PathVariable int id) {
    	System.out.println(id);
        Reporttype ttake = reportPostService.getTag(id);
        return ApiResult.SUCCESS(ttake);
    }

    @AllowRole(1)
    @RequestMapping(value = "tags/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult deleteTag(@PathVariable int id) {
        int result = reportPostService.changeTagStatus(id, 2);
        return ApiResult.SUCCESS(result);
    }

}
