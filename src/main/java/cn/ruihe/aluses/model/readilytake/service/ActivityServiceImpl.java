package cn.ruihe.aluses.model.readilytake.service;

import cn.ruihe.aluses.entity.SysEventCallArticle;
import cn.ruihe.aluses.model.readilytake.repository.CliReadilyTakeRepositoryCustom;
import cn.ruihe.aluses.model.readilytake.repository.CommonRepository;
import cn.ruihe.aluses.model.readilytake.vo.ActivityListOutput;
import cn.ruihe.aluses.model.readilytake.vo.ActivityThemeOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: ActivityServiceImpl.java</p>
 * <p>Description: </p>
 * @author try
 * @date 2015年7月16日-下午6:33:17
 * @version 1.0
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    CliReadilyTakeRepositoryCustom cliReadilyTakeRepository;

    @Autowired
    CommonRepository commonRepository;
    
	@Override
	public List<ActivityThemeOutput> getActivityThemeList() {
		return cliReadilyTakeRepository.getActivityThemeList();
	}


	@Override
	public List<ActivityListOutput> getActivityList(  int uid , int region , int project ,String projectname , int type  ) {
		//根据名子查找类型ID
//    	int index = cliReadilyTakeRepository.getSystemCode( projectname );
		return cliReadilyTakeRepository.getActivityListOutput(   uid ,  region ,  project , projectname ,  type );
	}

	
	@Override
	public int getIsenroll(int id, int eid) {
		return cliReadilyTakeRepository.getIsenroll(   id ,  eid );
	}


	@Override
	public SysEventCallArticle getSaveEventCallArticle(SysEventCallArticle sysEventCallArticle  , String projectname ) {
		int index = cliReadilyTakeRepository.getSystemCode( projectname );
		return cliReadilyTakeRepository.getSaveEventCallArticle( sysEventCallArticle , projectname  , commonRepository.getNewId("sysEventCallArticle"),index );
	}


	@Override
	public boolean getPraise(int id, int type,int uid) {
		return cliReadilyTakeRepository.getPraise(  id,  type,uid );
	}


	@Override
	public boolean getApplyActivity(int type, int uid, int region, String project, String projectname, String telephone, int id) {
		
		int pkId = commonRepository.getNewId("cliPartake");
		int index =cliReadilyTakeRepository.getSystemCode( projectname );
		return cliReadilyTakeRepository.getApplyActivity(type,  uid ,  region ,  project ,  projectname ,  telephone , id , pkId, index);
	}
}
