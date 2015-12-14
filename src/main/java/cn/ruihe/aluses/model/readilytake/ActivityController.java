package cn.ruihe.aluses.model.readilytake;

import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.entity.SysEventCallArticle;
import cn.ruihe.aluses.model.readilytake.service.ActivityService;
import cn.ruihe.aluses.model.readilytake.vo.ActivityListOutput;
import cn.ruihe.aluses.model.readilytake.vo.ActivityThemeOutput;
import cn.ruihe.aluses.model.readilytake.vo.SysEventCallArticleIn;
import cn.ruihe.utils.ImagesUP;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: ActivityController.java</p>
 * <p>Description: 活动</p>
 * @author try
 * @date 2015年7月16日-下午6:39:44
 * @version 1.0
 */
@Controller
public class ActivityController {

    @Autowired
    ActivityService activityService;

    
    @Autowired
	 HttpServletRequest request;
    
    /**
     * API06 6.获取活动召集主题
     */
    @RequestMapping("api/activitythemebylist")
    @ResponseBody
    public ApiResult getActivityThemeList() {
        List<ActivityThemeOutput> lists = activityService.getActivityThemeList();
        return ApiResult.SUCCESS(lists);
    }
    
    
    /**
     * API07 获取活动列表
     */
    @RequestMapping("api/activitybylist")
    @ResponseBody
    public ApiResult getActivitybylist(  @RequestParam int uid , @RequestParam int region , @RequestParam int project ,@RequestParam String projectname , @RequestParam  int type  ) {
        List<ActivityListOutput> lists = activityService.getActivityList(   uid ,  region ,  project , projectname ,  type  );

        
        return ApiResult.SUCCESS(lists);
    }
    
    /**
     * API07 获取活动列表ios
     */
    @RequestMapping("api/activitybylistios")
    @ResponseBody
    public ApiResult getActivitybylistIos(  @RequestParam int uid , @RequestParam int region , @RequestParam int project ,@RequestParam String projectname , @RequestParam  int type  ) {
        List<ActivityListOutput> lists = activityService.getActivityList(   uid ,  region ,  project , projectname ,  type  );
        for(int i=0;i<lists.size();i++)
        {
        	ActivityListOutput list=lists.get(i);
        	String url=list.getPictures();
        	if(url!=null)
        	{
        	   try
        	   {
        	       list.setPictures(ImagesUP.modifyImageUrl(request,url));
        	   } catch (Exception e) {
    			   e.printStackTrace();
    		   }
        	}
        }
        return ApiResult.SUCCESS(lists);
    }
    
    /**
     * API10 发布活动文章
     */
	@RequestMapping("/api/activitybyaddarticle")
    @ResponseBody
    public ApiResult getActivitybyaddarticle( SysEventCallArticleIn sysEventCallArticleIn ,   @RequestParam("files") MultipartFile[] files , String projectname  ) {
		
		String imagesName = "mate_images";
		SysEventCallArticle sysEventCallArticle = new SysEventCallArticle();
    	try {
    		sysEventCallArticle.setBegintime( new Date(sysEventCallArticleIn.getBegintime()*1000));
    		sysEventCallArticle.setContent(sysEventCallArticleIn.getContent());;
    		sysEventCallArticle.setEid(sysEventCallArticleIn.getEid());
    		sysEventCallArticle.setEndtime( new Date(sysEventCallArticleIn.getEndtime()*1000));
    		sysEventCallArticleIn.setEnrollendtime(sysEventCallArticleIn.getEnrollendtime());
    		sysEventCallArticleIn.setIsonlinemoney(sysEventCallArticleIn.getIsonlinemoney());
    		sysEventCallArticle.setMoney(sysEventCallArticleIn.getMoney());
    		sysEventCallArticle.setProject(sysEventCallArticleIn.getProject());
    		sysEventCallArticle.setRegion(sysEventCallArticleIn.getRegion());
    		sysEventCallArticle.setTelephone(sysEventCallArticleIn.getTelephone());
    		sysEventCallArticle.setTitle(sysEventCallArticleIn.getTitle());
    		sysEventCallArticle.setUid(sysEventCallArticleIn.getUid());
    		sysEventCallArticle.setBegintime( new Date(sysEventCallArticleIn.getBegintime()*1000 ));
    		sysEventCallArticle.setUname(sysEventCallArticleIn.getUname());
    		sysEventCallArticle.setUpperlimit(sysEventCallArticleIn.getUpperlimit());
    		sysEventCallArticle.setPictures( ImagesUP.addImage(request, imagesName, files ) );
    		sysEventCallArticle.setSmallpic( sysEventCallArticle.getPictures() );
    		
    		SysEventCallArticle sysEventCallArticleNew = activityService.getSaveEventCallArticle(  sysEventCallArticle ,  projectname  );
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
        return ApiResult.SUCCESS();
    }
    
	
	 /**
     * API14 14.随手拍、活动召集文章点赞
     */
	@RequestMapping("/api/praise")
    @ResponseBody
    public ApiResult getPraise(  @RequestParam int id , @RequestParam int type ,@RequestParam int uid ) {
    	activityService.getPraise(  id , type ,uid );
        return ApiResult.SUCCESS();
    }
	
	 /**
     * API15 15.报名活动
     */
	@RequestMapping("/api/applyactivity")
    @ResponseBody
    public ApiResult getApplyActivity(  @RequestParam int type ,@RequestParam int uid , @RequestParam int region , @RequestParam String project , @RequestParam String projectname , @RequestParam String telephone , @RequestParam int id ) {
    	if(activityService.getApplyActivity(  type,   uid ,   region ,   project ,   projectname ,   telephone ,   id  ))
    		return ApiResult.SUCCESS();
    	else 
    		return ApiResult.FAILURE();    
    }
	
	/**
     * API31 获取热门活动列表
     */
    @RequestMapping("api/hotactivitybylist")
    @ResponseBody
    public ApiResult getHotActivitybylist(  @RequestParam int uid ,@RequestParam String projectname) {
    	int region = 0;
    	int project = 0;
    	int type = 3;
        List<ActivityListOutput> lists = activityService.getActivityList(   uid ,  region ,  project , projectname ,  type  );
        
        return ApiResult.SUCCESS(lists);
    }
	
}
