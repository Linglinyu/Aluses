package cn.ruihe.aluses.model.readilytake.service;

import java.util.List;

import cn.ruihe.aluses.model.home.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ruihe.aluses.entity.Community;
import cn.ruihe.aluses.entity.Communitycontent;
import cn.ruihe.aluses.entity.Communitymember;
import cn.ruihe.aluses.entity.Grouptype;
import cn.ruihe.aluses.model.readilytake.repository.CliReadilyTakeRepositoryCustom;
import cn.ruihe.aluses.model.readilytake.repository.CommonRepository;
import cn.ruihe.aluses.model.readilytake.vo.TeamChatListOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamListOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamUserListOutput;

/**
 * <p>Title: TeamServiceImpl.java</p>
 * <p>Description: </p>
 * @author try
 * @date 2015年7月16日-下午2:55:51
 * @version 1.0
 */
@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	CommonRepository commonRepository;
	 
    @Autowired
    CliReadilyTakeRepositoryCustom cliReadilyTakeRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Override
	public List<TeamListOutput> getTeamList(int uid, int region, int project, String projectname,
			int type) {
		//根据名子查找类型ID
//    	int index = cliReadilyTakeRepository.getSystemCode( projectname );
		return cliReadilyTakeRepository.getTeamList(  uid ,    region ,    project , projectname , type );
	}

	@Override
	public List<TeamChatListOutput> getTeamChatList(int uid, int groupid, int pagesize, int pagenum) {
		return cliReadilyTakeRepository.getTeamChatList(  uid ,    groupid ,    pagesize , pagenum );
	}

	@Override
	public int getSvaeTeamChatCommunity(Community community , String projectname , String  username  ) {
		int index = cliReadilyTakeRepository.getSystemCode( projectname );

		int projectId= this.projectRepository.getProjectIdByName(projectname);

		if (projectId==-1){
			return -1;
		}

		community.setProject(projectId);

		return cliReadilyTakeRepository.getSvaeTeamChatCommunity(  community  , projectname , commonRepository.getNewId("community") , username ,index);
	}

	@Override
	public boolean getSaveTeambyadd(Communitymember communitymember , String projectname , boolean isSave) {
		
		String clientId = "";
		int pkId = commonRepository.getNewId("communitymember");
		int index=cliReadilyTakeRepository.getSystemCode( projectname );
//		CliReadilyTakeRepositoryImpl.getParameterCode( communitymember.get);
		return cliReadilyTakeRepository.getSaveTeambyadd(  communitymember , projectname , pkId , clientId ,  isSave, index);
		
	}

	@Override
	public boolean getSaveTeambypush( Communitycontent communitycontent ) {
		
		int pkId = commonRepository.getNewId("communitycontent");
		return cliReadilyTakeRepository.getSaveTeambypush(  communitycontent  , pkId );
	}

	@Override
	public List<TeamUserListOutput> getTeamuserList(int groupid) {
		return cliReadilyTakeRepository.getTeamuserList(  groupid );
	}

	@Override
	public boolean getTeamByOperation( int id , int type , int uid ) {
		return cliReadilyTakeRepository.getTeamByOperation(  id ,  type ,  uid );
	}

	@Override
	public int getIsGroupMem(int groupid , String telephone  ) {
		return cliReadilyTakeRepository.getIsGroupMem( groupid , telephone );
	}

	
	@Override
	public List<Grouptype> getgrouptype() {
		return cliReadilyTakeRepository.getgrouptype();
	}

	
	@Override
	public int setgroupname(int gid, int uid, String groupname) {
		return cliReadilyTakeRepository.setgroupname(gid,uid, groupname);
	}

	@Override
	public int setgroupinfo(Community community) {
		return cliReadilyTakeRepository.setgroupinfo(community);
	}

	@Override
	public int delgroup(int gid, int uid) {
		return cliReadilyTakeRepository.delgroup(gid,uid);
	}

	@Override
	public int exitgroup(int gid, int uid) {
		return cliReadilyTakeRepository.exitgroup(gid,uid);
	}

}
