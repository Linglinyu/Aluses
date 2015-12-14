package cn.ruihe.aluses.model.readilytake.service;

import cn.ruihe.aluses.entity.CliReadilyTakeComment;
import cn.ruihe.aluses.entity.CliReadilyTakePost;
import cn.ruihe.aluses.entity.ReportPost;
import cn.ruihe.aluses.entity.Reporttype;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeArticleOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeCommentOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeTagOutput;

import java.util.List;

/**
 * @Author DHC
 * @Date 2015-07-15 13:12:24
 */
public interface CliReadilyTakeService {
    List<CliReadilyTakeCommentOutput> findByRid(int rid);

    /**
     * 获取随手拍标签
     *
     * @return
     */
    List<CliReadilyTakeTagOutput> getCliReadilyTakeTagList();


    /**
     * 获取随手拍文章列表
     *
     * @return
     */
    List<CliReadilyTakeArticleOutput> getTeamListOutputByList(String projectname, int pagesize, int pagenum);

    /**
     * 8.发布随手拍文章
     *
     * @return
     */
    CliReadilyTakePost getSaveCliReadilyTakePost(CliReadilyTakePost cliReadilyTakePost , String projectname );

    /**
     * 9.发布随手拍评论
     *
     * @return
     */
    CliReadilyTakeComment getSaveCliReadilyCommentPost(int rid, int uid, int region, int project,
                                                       String projectname, String uname, String telephone, String content);

    /**
     * 18 验证手机号是否是黑名单
     * @return
     */
   int getPhonebyStatus( String telephone  );

	/**
	 * 20 删除邻里帖子
	 * @return
	 */
	int delpost(int id, int uid);

	/**
	 * 21 收藏帖子
	 * @return
	 */
	int collectpost(int id, int uid);

	/**
	 * 22 获取帖子举报类型
	 * @return
	 */
	List<Reporttype> getreporttype(int uid, int type);

	/**
	 * @param 23 举报帖子或者圈子
	 *  @return
	 */
	int reportpost(ReportPost reportpost);

	/**
	 * 24 获取我的帖子
	 * @return
	 */
	List<CliReadilyTakeArticleOutput> getmypost(int uid,int pagesize,int pagenum);
    
	
	/**
	 * 32 获取收藏的帖子
	 * @return
	 */
	List<CliReadilyTakeArticleOutput> getmycollectpost(int uid,int pagesize,int pagenum);

	/**
	 * API33 是否点赞
	 * @return
	 */
	int ispraise(int uid, int id);
}
