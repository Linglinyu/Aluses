package cn.ruihe.aluses.model.home;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.entity.BrcUserInfo;
import cn.ruihe.aluses.entity.CliReadilyTakePost;
import cn.ruihe.aluses.entity.Community;
import cn.ruihe.aluses.entity.Communitycontent;
import cn.ruihe.aluses.entity.Communitymember;
import cn.ruihe.aluses.entity.Grouptype;
import cn.ruihe.aluses.entity.ReportPost;
import cn.ruihe.aluses.entity.Reporttype;
import cn.ruihe.aluses.entity.SysEventCallArticle;
import cn.ruihe.aluses.entity.SysEventCallTheme;
import cn.ruihe.aluses.entity.SysUser;
import cn.ruihe.aluses.model.home.interceptor.AllowRole;
import cn.ruihe.aluses.model.home.service.CommunityService;
import cn.ruihe.aluses.model.home.service.ProjectService;
import cn.ruihe.aluses.model.home.service.ReportPostService;
import cn.ruihe.aluses.model.home.vo.SimpleProjectOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeTagOutput;
import cn.ruihe.utils.BrcUserInfoUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author dhc
 * @Date 2015-07-20 16-42
 */
@Controller
@RequestMapping("ct")
public class CommunityController {
	
	@Autowired
	ReportPostService reportPostService;
	
    @Autowired
    CommunityService communityService;

    @Autowired
    ProjectService projectService;

    @AllowRole({1, 4})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getCommunities(HttpSession session, Model model, @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "key", required = false) String key, @RequestParam(value = "project", required = false) Integer project) {
        if(page == null) {
            page = 1;
        }
        SysUser user = (SysUser) session.getAttribute("user");
        Integer projectid;
        if(user.getRole() == 1 && project != null && project != 0) {
            projectid = project;
        } else {
            projectid = user.getProject();
        }
        Map<String, String[]> pms = new HashMap<>();
        if(project != null) {
            pms.put("project", new String[]{project.toString()});
        }
        if(!StringUtils.isEmpty(key)) {
            pms.put("key", new String[]{key});
        }
        Pager<Community> pager = communityService.getCommunities(projectid, page, 12, key);
        
        List<SimpleProjectOutput> projects = projectService.getSimpleProjects();
        List<Grouptype> tags =  communityService.getGrouptypeTagList();
        model.addAttribute("cts", pager.getDatas());
        model.addAttribute("project", projectid == null ? 0 : projectid);
        model.addAttribute("pager", pager.pagesLink(pms));
        model.addAttribute("projects", projects);
        model.addAttribute("tags", tags);
        model.addAttribute("key", key);
        if(!StringUtils.isEmpty(key)&&(pager==null||pager.getSize()==0))
        {
        	throw new MessageException("没有相关信息", 400);
        }
        return "admin/ct";
    }

    
    @AllowRole({1, 4})
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String getCommunilty(Model model, @RequestParam(required = false) Integer id) {
    	Community community = communityService.getCommunity(id);
    	List<SimpleProjectOutput> projects = projectService.getSimpleProjects();
        List<Grouptype> tags =  communityService.getGrouptypeTagList();
        model.addAttribute("ct", community);
        model.addAttribute("projects", projects);
        model.addAttribute("tags", tags);
        System.out.println(community.getGroupdescribe());
        return "admin/ct_detail"; 
    }
    


    @AllowRole({1, 4})
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult updateCommunity(HttpSession session, Community input){  	
    	int id=input.getId();
    	System.out.println("id="+id);
        SysUser user = (SysUser) session.getAttribute("user");
        Community community = communityService.getCommunity(id);
        if(user.getRole() != 1 && community.getProject().intValue() != user.getProject()) {
            throw new MessageException("没有修改权限", 401);
        }
        if(StringUtils.isEmpty(input.getGroupname())) {
            throw new MessageException("圈子名称不能为空", 400);
        }
        if(input.getProject() == null && user.getRole() != 1) {
            input.setProject(community.getProject());
        }
        input.setRegion(1);
        Community result;
        if(input.getId()==null)
        {
        	result = communityService.addCommunity(input);	
        }
        else {
            result = communityService.updateCommunity(input);
        }
        return ApiResult.SUCCESS(result);
    }
    
    @AllowRole(1)
    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult addCommunity(Community input){
    	
    	if(StringUtils.isEmpty(input.getGroupname())) {
            throw new MessageException("圈子名称不能为空", 400);
        }
        Community result;
    	input.setRegion(1);
        result = communityService.addCommunity(input);	
        
        return ApiResult.SUCCESS(result);
    }
   

    @AllowRole({1, 4})
    @RequestMapping(value = "{id}/status", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changeCommunityStatus(HttpSession session, @PathVariable int id, @RequestParam int status) {
        SysUser user = (SysUser) session.getAttribute("user");
        Community community = communityService.getCommunity(id);
        if(user.getRole() != 1 && community.getProject().intValue() != user.getProject()) {
            throw new MessageException("没有修改权限", 401);
        }
        int result = communityService.changeCommunityStatus(id, status);
        return ApiResult.SUCCESS(result);
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "{id}/audit", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changeCommunityAudit(HttpSession session, @PathVariable int id, @RequestParam int audit) {
        SysUser user = (SysUser) session.getAttribute("user");
        Community community = communityService.getCommunity(id);
        if(user.getRole() != 1 && community.getProject().intValue() != user.getProject()) {
            throw new MessageException("没有修改权限", 401);
        }
        int result = communityService.changeCommunityAudit(id, audit);
        return ApiResult.SUCCESS(result);
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "members", method = RequestMethod.GET)
    public String showCommunityMembers(Model model, @RequestParam int id) {
        List<Communitymember> communitymembers = communityService.getCommunityMembers(id);
        Community community = communityService.getCommunity(id);
        model.addAttribute("c", community);
        model.addAttribute("mbs", communitymembers);
        return "admin/ct_members";
    }
    
//    @AllowRole({1, 4})
//    @RequestMapping(value = "members", method = RequestMethod.GET)
//    public String showCommunityMembers(HttpSession session, Model model, @RequestParam(value = "page", required = false) Integer page,
//    		@RequestParam int id) {
//        if(page == null) {
//            page = 1;
//        }
//        Pager<Communitymember> pager = communityService.getCommunityMembers(id, page, 12);
//        Community community = communityService.getCommunity(id);
//        model.addAttribute("c", community);
//        model.addAttribute("mbs", pager.getDatas());
//        return "admin/ct_members";
//    }

    @AllowRole({1, 4})
    @RequestMapping(value = "members/{id}/{groupid}/{uname}/status", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changeCommunityMemberStatus(@PathVariable int id,@PathVariable int groupid,@PathVariable String uname, @RequestParam int status) {
        int result = communityService.changeCommunityMemberStatus(id, status ,groupid , uname);
        return ApiResult.SUCCESS(result);
    }


    @AllowRole({1, 4})
    @RequestMapping(value = "chats", method = RequestMethod.GET)
    public String showCommunityChats(Model model, @RequestParam int id, @RequestParam(value = "page", required = false) Integer page) {
        if(page == null) {
            page = 1;
        }
        Pager<Communitycontent> chats = communityService.getCommunityChats(id, page, 12);
        Community community = communityService.getCommunity(id);
        model.addAttribute("c", community);
        model.addAttribute("chats", chats.getDatas());
        model.addAttribute("pager", chats.pagesLink(null));
        return "admin/ct_chats";
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "chats/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changeCommunityChatStatus(@PathVariable int id, @RequestParam int status) {
        int result = communityService.changeCommunityChatStatus(id, status);
        return ApiResult.SUCCESS(result);
    }
    
    //added by cwx,圈子举报
    @AllowRole({1, 4})
    @RequestMapping(value = "reportpost", method = RequestMethod.GET)
    public String getPostReportposts(Model model, @RequestParam int id) {
        List<ReportPost> comments = reportPostService.getPostReportposts(id,2);
        Community community = communityService.getCommunity(id);
        StringBuilder userIds = new StringBuilder();
        if(comments!=null&&comments.size()>0)
        {
        	for(int i=0;i<comments.size();i++)
        	{
        		userIds.append(comments.get(i).getUid()).append(',');
        	}
        	if(userIds.length()>1)
        	{
        		userIds.deleteCharAt(userIds.length()-1);
        	}
        }
        System.out.println(userIds);
//		String str=HttpRequestor.doPostJsonStr("http://www.88888881.com:8881/brc-app-web/app/getUserHeadAndStepS.json", 
//        String.format("{\"IDs\":\"%s\"}", userIds));
//		System.out.println(str);
        List<BrcUserInfo> brcUserInfoList=new ArrayList<BrcUserInfo>();
        
        brcUserInfoList=BrcUserInfoUtils.getBrcUserInfos(userIds.toString());
        
        for(ReportPost comment:comments){
        	comment.setUname("");
        	if (brcUserInfoList != null && !brcUserInfoList.isEmpty())
				for (BrcUserInfo brcUserInfo : brcUserInfoList) {
					if (comment.getUid() == brcUserInfo.getIClientID()) {
						if(brcUserInfo.getSNickName()!=null)
						comment.setUname(brcUserInfo.getSNickName());
						else {
							comment.setUname(comment.getUid().toString());
						}
						break;
					}
					
				}
        	if(comment.getUname().isEmpty())
        	{
        		comment.setUname("匿名");
        	}
        }
        model.addAttribute("c", community);
        model.addAttribute("comments", comments);
        return "admin/ct_reportpost";
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "reportpost/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changePostReportpostStatus(@PathVariable int id, @RequestParam int status) {
        int result = reportPostService.changeReportpostStatus(id, status);
        return ApiResult.SUCCESS(result);
    }

//    @AllowRole({1, 4})
//    @RequestMapping(value = "reportpost/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public ApiResult deletePostReportpost(@PathVariable int id) {
//        int result = reportPostService.changeReportpostStatus(id, 9);
//        return ApiResult.SUCCESS(result);
//    }
    
    //added  by  cwx,圈子标签
    @AllowRole(1)
    @RequestMapping(value = "tags", method = RequestMethod.GET)
    public String getTags(Model model) {
        List<Grouptype> tags =  communityService.getGrouptypeTagList();
        model.addAttribute("tags", tags);
        return "admin/ct_tags";
    }

    @AllowRole(1)
    @RequestMapping(value = "tags", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult auTag(Grouptype tag) {
        if(tag.getTypename() == null || Objects.equals(tag.getTypename(), "")) {
            throw new MessageException("标签名称不能为空", 400);
        }
        Grouptype ttake;
        System.out.println("程序开始");
        System.out.println(tag.getDescribe());
        if(tag.getId() == null) {
            ttake = communityService.addTag(tag);
        } 
        else {
        	ttake = communityService.updateTag(tag);
        }
        return ApiResult.SUCCESS(ttake);
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "tags/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<Grouptype> getTag(@PathVariable int id) {
    	System.out.println(id);
    	Grouptype ttake = communityService.getTag(id);
        return ApiResult.SUCCESS(ttake);
    }
    
    @AllowRole(1)
    @RequestMapping(value = "tags/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult deleteTag(@PathVariable int id) {
        int result = communityService.deleteTag(id);
        return ApiResult.SUCCESS(result);
    }
}
