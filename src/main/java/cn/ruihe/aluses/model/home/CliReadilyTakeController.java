package cn.ruihe.aluses.model.home;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.entity.BrcUserInfo;
import cn.ruihe.aluses.entity.CliPartake;
import cn.ruihe.aluses.entity.CliReadilyTakeComment;
import cn.ruihe.aluses.entity.CliReadilyTakePost;
import cn.ruihe.aluses.entity.ReportPost;
import cn.ruihe.aluses.entity.SysReadilyTtake;
import cn.ruihe.aluses.entity.SysUser;
import cn.ruihe.aluses.model.home.interceptor.AllowRole;
import cn.ruihe.aluses.model.home.service.ProjectService;
import cn.ruihe.aluses.model.home.service.ReadilyTakeService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author DHC
 * @Date 2015-07-18 17:07:51
 */
@Controller
@RequestMapping("rt")
public class CliReadilyTakeController {
	
	@Autowired
	ReportPostService reportPostService;

    @Autowired
    ReadilyTakeService readilyTakeService;

    @Autowired
    ProjectService projectService;

    @AllowRole(1)
    @RequestMapping(value = "tags", method = RequestMethod.GET)
    public String getTags(Model model) {
        List<CliReadilyTakeTagOutput> tags =  readilyTakeService.getCliReadilyTakeTagList();
        model.addAttribute("tags", tags);
        return "admin/rt_tags";
    }

    @AllowRole(1)
    @RequestMapping(value = "tags", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult auTag(SysReadilyTtake tag) {
        if(tag.getTypename() == null || Objects.equals(tag.getTypename(), "")) {
            throw new MessageException("标签名称不能为空", 400);
        }
        if(tag.getColor() == null || Objects.equals(tag.getColor(), "")) {
            throw new MessageException("标签颜色不能为空", 400);
        }
        if(tag.getDescribe() == null || Objects.equals(tag.getDescribe(), "")) {
            throw new MessageException("标签描述不能为空", 400);
        }
        SysReadilyTtake ttake;
        if(tag.getId() == null) {
            ttake = readilyTakeService.addTag(tag);
        } else {
            ttake = readilyTakeService.updateTag(tag);
        }
        return ApiResult.SUCCESS(ttake);
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "tags/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<SysReadilyTtake> getTag(@PathVariable int id) {
        SysReadilyTtake ttake = readilyTakeService.getTag(id);
        return ApiResult.SUCCESS(ttake);
    }

    @AllowRole(1)
    @RequestMapping(value = "tags/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult deleteTag(@PathVariable int id) {
        int result = readilyTakeService.changeTagStatus(id, 2);
        return ApiResult.SUCCESS(result);
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "posts", method = RequestMethod.GET)
    public String getPosts(HttpSession session, Model model, @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "key", required = false) String key, @RequestParam(value = "project", required = false) Integer project) {
        if(page == null) {
            page = 1;
        }

        Map<String, String[]> pms = new HashMap<>();
        if(project != null && project != 0) {
            pms.put("project", new String[]{project.toString()});
        }
        if(!StringUtils.isEmpty(key)) {
            pms.put("key", new String[]{key});
        } else {
            key = null;
        }

        SysUser user = (SysUser) session.getAttribute("user");
        Integer projectid;
        if(user.getRole() == 1 && project != null && project != 0) {
            projectid = project;
        } else {
            projectid = user.getProject();
        }
        Pager<CliReadilyTakePost> pager = readilyTakeService.getPosts(projectid, page, 12, key);
        List<SimpleProjectOutput> projects = projectService.getSimpleProjects();
        model.addAttribute("posts", pager.getDatas());
        model.addAttribute("project", projectid == null ? 0 : projectid);
        model.addAttribute("pager", pager.pagesLink(pms));
        model.addAttribute("projects", projects);
        model.addAttribute("key", key);
        if(key!=null&&(pager==null||pager.getSize()==0))
        {
        	throw new MessageException("没有相关信息", 400);
        }
        return "admin/rt_posts";
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "post", method = RequestMethod.GET)
    public String getPost(HttpSession session, Model model, @RequestParam int id){
        SysUser user = (SysUser) session.getAttribute("user");
        CliReadilyTakePost post = readilyTakeService.getPost(id);
        if(user.getRole() != 1 && post.getProject().intValue() != user.getProject()) {
            throw new MessageException("没有查看权限", 401);
        }
        List<CliReadilyTakeTagOutput> ttakes = readilyTakeService.getCliReadilyTakeTagList();
        List<SimpleProjectOutput> projects = projectService.getSimpleProjects();
        model.addAttribute("post", post);
        model.addAttribute("projects", projects);
        model.addAttribute("ttakes", ttakes);
        return "admin/rt_post";
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "post/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult updatePost(HttpSession session, @PathVariable int id, CliReadilyTakePost input){
        if(id != input.getId()) {
            throw new MessageException("非法修改", 400);
        }
        SysUser user = (SysUser) session.getAttribute("user");
        CliReadilyTakePost post = readilyTakeService.getPost(id);
        if(user.getRole() != 1 && post.getProject().intValue() != user.getProject()) {
            throw new MessageException("没有修改权限", 401);
        }
        if(input.getTitle() == null || Objects.equals(input.getTitle(), "")) {
            throw new MessageException("标题不能为空", 400);
        }
        if(input.getProject() == null && user.getRole() != 1) {
            input.setProject(post.getProject());
        }
        input.setRegion(1);
        CliReadilyTakePost result = readilyTakeService.updateCliReadilyTakePost(input);
        return ApiResult.SUCCESS(result);
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "posts/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult deletePost(HttpSession session, @PathVariable int id) {
        SysUser user = (SysUser) session.getAttribute("user");
        CliReadilyTakePost post = readilyTakeService.getPost(id);
        if(user.getRole() != 1 && (post.getProject().intValue() != user.getProject() || user.getHasdelpost() != 1)) {
            throw new MessageException("没有删除权限", 401);
        }
        int result = readilyTakeService.changePostStatus(id, 3);
        return ApiResult.SUCCESS(result);
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "post/comment", method = RequestMethod.GET)
    public String getPostComments(Model model, @RequestParam int id) {
        List<CliReadilyTakeComment> comments = readilyTakeService.getPostComments(id);
        CliReadilyTakePost post = readilyTakeService.getPost(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "admin/rt_post_comment";
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "post/comment/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changePostCommentStatus(@PathVariable int id, @RequestParam int status) {
        int result = readilyTakeService.changeCommentStatus(id, status);
        return ApiResult.SUCCESS(result);
    }

//    @AllowRole({1, 4})
//    @RequestMapping(value = "post/comment/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public ApiResult deletePostComment(@PathVariable int id) {
//        int result = readilyTakeService.changeCommentStatus(id, 3);
//        return ApiResult.SUCCESS(result);
//    }
    
    @AllowRole({1, 4})
    @RequestMapping(value = "post/reportpost", method = RequestMethod.GET)
    public String getPostReportposts(Model model, @RequestParam int id) {
        List<ReportPost> comments = reportPostService.getPostReportposts(id,1);
        CliReadilyTakePost post = readilyTakeService.getPost(id);
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
        	
        }
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "admin/rt_post_reportpost";
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "post/reportpost/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changePostReportpostStatus(@PathVariable int id, @RequestParam int status) {
        int result = reportPostService.changeReportpostStatus(id, status);
        return ApiResult.SUCCESS(result);
    }

//    @AllowRole({1, 4})
//    @RequestMapping(value = "post/reportpost/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public ApiResult deletePostReportpost(@PathVariable int id) {
//        int result = reportPostService.changeReportpostStatus(id, 9);
//        return ApiResult.SUCCESS(result);
//    }
}
