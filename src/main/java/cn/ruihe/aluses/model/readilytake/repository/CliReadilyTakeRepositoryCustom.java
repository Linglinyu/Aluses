package cn.ruihe.aluses.model.readilytake.repository;

import java.io.Serializable;
import java.util.List;

import cn.ruihe.aluses.entity.CliReadilyTakeComment;
import cn.ruihe.aluses.entity.CliReadilyTakePost;
import cn.ruihe.aluses.entity.Community;
import cn.ruihe.aluses.entity.CommunityOut;
import cn.ruihe.aluses.entity.Communitycontent;
import cn.ruihe.aluses.entity.Communitymember;
import cn.ruihe.aluses.entity.Grouptype;
import cn.ruihe.aluses.entity.ReportPost;
import cn.ruihe.aluses.entity.Reporttype;
import cn.ruihe.aluses.entity.SysEventCallArticle;
import cn.ruihe.aluses.model.readilytake.vo.ActivityListOutput;
import cn.ruihe.aluses.model.readilytake.vo.ActivityThemeOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeArticleOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeCommentOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeTagOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamChatListOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamListOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamUserListOutput;

/**
 * @Author DHC
 * @Date 2015-07-15 15:55:42
 */
public interface CliReadilyTakeRepositoryCustom extends Serializable{
	
   
    
    /**
     * 1.获取随手拍标签
     * @return
     */
    List<CliReadilyTakeTagOutput> getCliReadilyTakeTagList();
    
    /**
     * 2.获取随手拍标签
     * @param index 
     * @param projectname
     * @param pagesize
     * @param pagenum
     * @return
     */
    List<CliReadilyTakeArticleOutput> getTeamListOutputByList( String projectname ,  int  pagesize ,  int  pagenum );

    /**
     * 3.获取随手拍文章评论
     * @param rid
     * @return
     */
    List<CliReadilyTakeCommentOutput> findByRid(int rid);
    
    /**
     * 4.获取圈子列表
     * @param uid
     * @param region
     * @param project
     * @param project2 
     * @param projectname
     * @param type
     * @return
     */
    List<TeamListOutput> getTeamList( int uid, int region, int project,  String projectname, int type);
    
    /**
     * 5.获取圈子聊天内容
     * @param uid
     * @param groupid
     * @param pagesize
     * @param pagenum
     * @return
     */
    List<TeamChatListOutput> getTeamChatList(  int uid, int groupid, int pagesize, int pagenum );
    
    /**
     * 6.获取活动召集主题
     * @return
     */
    List<ActivityThemeOutput> getActivityThemeList();
    
    /**
     * 7.获取活动列表
     * @param uid
     * @param region
     * @param project
     * @param projectname
     * @param type
     * @return
     */
    List<ActivityListOutput> getActivityListOutput(  int uid , int region , int project ,String projectname , int type );
    
    CliReadilyTakePost getSaveCliReadilyTakePost(CliReadilyTakePost cliReadilyTakePost , String projectname,int index);
    
    CliReadilyTakeComment getSaveCliReadilyCommentPost( int rid ,  int  uid , int  region , int  project , 
    		String  projectname , String  uname  , String  telephone ,String  content , int id,int index);
    
    /**
     * 10.发布活动文章
     * @param sysEventCallArticle
     * @param images
     * @return
     */
    SysEventCallArticle getSaveEventCallArticle(SysEventCallArticle sysEventCallArticle  , String projectname , int pkId, int index );
    
    /**
     * 11.申请圈子
     * @param community
     * @return
     */
    int getSvaeTeamChatCommunity( Community community , String projectname , int pkId , String  username , int index );
    
    /**
     * 12.申请加入圈子
     * @param communitymember
     * @return
     */
    boolean getSaveTeambyadd( Communitymember communitymember  , String projectname  , int pkId , String clientId , boolean isSave,int index);
    
    /**
     * 13.发送圈子消息
     * @param communitycontent
     * @return
     */
    boolean getSaveTeambypush( Communitycontent communitycontent , int pkId );
    
    /**
     * 14.随手拍、活动召集文章点赞
     * @param uid
     * @param uname
     * @param telephone
     * @param type
     * @return
     */
    boolean getPraise( int id, int type,int uid );
    
    int getSystemCode(String name );
    
    /**
     * 15.报名活动
     * @return
     */
    boolean  getApplyActivity( int type, int uid ,  int region ,  String project , String projectname ,  String telephone , int id , int pkId ,int index );
    
    /**
     * 16 获取圈子成员列表 
     * @param groupid
     * @return
     */
    List<TeamUserListOutput> getTeamuserList( int groupid );
    
    /**
     * 17 圈子成员删除、设置圈主、取消圈主
     * @param uid
     * @param type
     * @return
     */
    boolean getTeamByOperation( int id , int type , int uid  );
    
    /**
     * 18 验证手机号是否是黑名单
     * @param uid
     * @param type
     * @return
     */
    int getPhonebyStatus( String  telephone );
    
    /**
     * 19.验证当前用户是否已加入传入圈子
     * @param groupid
     * @return
     */
    int getIsGroupMem( int  groupid , String telephone );
    
    /**
	 * 20 删除邻里帖子
	 * @return
	 */
	int delpost(int id, int uid);
	
	/**
	 * 21 收藏帖子
	 * @return
	 */
	int collectpost(int id,int pid, int uid);

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
	 * 25 获取圈子类型
	 * @return
	 */
	List<Grouptype> getgrouptype();

	/**
	 *26 修改圈子成员昵称
	 * @return
	 */
	int setgroupname(int gid, int uid, String groupname);

	/**
	 * 27 修改圈子信息
	 * @return
	 */
	int setgroupinfo(Community community);

	/**
	 * 28 解散圈子
	 * @return
	 */
	int delgroup(int gid, int uid);

	/**
	 * 29 退出圈子
	 * @return
	 */
	int exitgroup(int gid, int uid);

	/**
	 * 32 获取收藏的帖子
	 * @return
	 */
	List<CliReadilyTakeArticleOutput> getmycollectpost(int uid,int pagesize,int pagenum);

	/**
	 * 是否报名
	 */
	int getIsenroll(int id, int eid);
	
	/**
	 * 是否点赞
	 */
	int ispraise(int id, int uid);
}
