package cn.ruihe.aluses.model.readilytake.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import cn.ruihe.aluses.entity.Community;
import cn.ruihe.aluses.entity.CommunityOut;
import cn.ruihe.aluses.entity.Communitycontent;
import cn.ruihe.aluses.entity.Communitymember;
import cn.ruihe.aluses.entity.Grouptype;
import cn.ruihe.aluses.model.readilytake.vo.TeamChatListOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamListOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamUserListOutput;

/**
 * <p>Title: TeamService.java</p>
 * <p>Description: 圈子信息 service</p>
 * @author try
 * @date 2015年7月16日-下午2:50:40
 * @version 1.0
 */
public interface TeamService {
    
	/**
	 * 4.获取圈子列表
	 * @return
	 */
	List<TeamListOutput> getTeamList(int uid, int region, int project, String projectname, int type);

	/**
	 * 5.获取圈子聊天内容
	 * @return
	 */
	List<TeamChatListOutput> getTeamChatList(int uid, int groupid, int pagesize, int pagenum);
      
	/**
	 * 11.申请圈子
	 * @return
	 */
	int getSvaeTeamChatCommunity(Community community, String projectname, String username);

	/**
	 * 12.申请加入圈子
	 * @return
	 */
	boolean getSaveTeambyadd(Communitymember communitymember, String projectname, boolean isSave);

	
	/**
	 * 13.发送圈子消息
	 * @return
	 */
	boolean getSaveTeambypush(Communitycontent communitycontent);
	
	/**
	 * 16.获取圈子成员列表
	 * @return
	 */
	List<TeamUserListOutput> getTeamuserList( int groupid );
	
	/**
	 * 17.圈子成员删除、设置圈主、取消圈主
	 * @return
	 */
	boolean getTeamByOperation( int id , int type , int uid );
	
	/**
	 * 19.验证当前用户是否已加入传入圈子
	 * @param groupid
	 * @return
	 */
	int getIsGroupMem( int groupid , String telephone  );

	/**
	 * 25 获取圈子类型
	 * @return
	 */
	List<Grouptype> getgrouptype();

	/**
	 * 26 修改圈子成员昵称
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
	
	
	
}
