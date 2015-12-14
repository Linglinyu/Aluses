package cn.ruihe.aluses.model.readilytake.service;

import cn.ruihe.aluses.entity.SysEventCallArticle;
import cn.ruihe.aluses.model.readilytake.vo.ActivityListOutput;
import cn.ruihe.aluses.model.readilytake.vo.ActivityThemeOutput;

import java.util.List;

/**
 * <p>Title: ActivityThemeService.java</p>
 * <p>Description: 活动模块 service </p>
 * @author try
 * @date 2015年7月16日-下午6:31:01
 * @version 1.0
 */
public interface ActivityService {
    
    /**
     * 6.获取活动召集主题
     * @return
     */
     List<ActivityThemeOutput> getActivityThemeList();
     
     /**
      * 7.获取活动列表
      * @return
      */
      List<ActivityListOutput> getActivityList( int uid , int region , int project ,String projectname , int type );
 
      /**
       * 10.发布活动文章
       * @return
       */
      SysEventCallArticle getSaveEventCallArticle( SysEventCallArticle sysEventCallArticle ,  String projectname );
      
      /**
       * 14.随手拍、活动召集文章点赞
       * @return
       */
      boolean getPraise(   int id , int type ,int uid);
      
      
      /**
       * 15.报名活动
       * @return
       */
      boolean getApplyActivity( int type, int uid ,  int region ,  String project ,  String projectname ,  String telephone ,  int id );

	/**
	 * 是否报名
	 */
	int getIsenroll(int id, int eid);
      
}
