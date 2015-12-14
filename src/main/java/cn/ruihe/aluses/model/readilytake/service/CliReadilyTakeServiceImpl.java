package cn.ruihe.aluses.model.readilytake.service;

import cn.ruihe.aluses.entity.CliReadilyTakeComment;
import cn.ruihe.aluses.entity.CliReadilyTakePost;
import cn.ruihe.aluses.entity.ReportPost;
import cn.ruihe.aluses.entity.Reporttype;
import cn.ruihe.aluses.model.readilytake.repository.CliReadilyTakeRepository;
import cn.ruihe.aluses.model.readilytake.repository.CommonRepository;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeArticleOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeCommentOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeTagOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @Author DHC
 * @Date 2015-07-15 13:19:23
 */
@Service
public class CliReadilyTakeServiceImpl implements CliReadilyTakeService {

    @Autowired
    CliReadilyTakeRepository cliReadilyTakeRepository;

    @Autowired
    CommonRepository commonRepository;

    @Override
    public List<CliReadilyTakeCommentOutput> findByRid(int rid) {
        return cliReadilyTakeRepository.findByRid(rid);
    }

    @Override
    public List<CliReadilyTakeTagOutput> getCliReadilyTakeTagList() {
        return cliReadilyTakeRepository.getCliReadilyTakeTagList();
    }

    @Override
    public List<CliReadilyTakeArticleOutput> getTeamListOutputByList(String projectname, int pagesize, int pagenum) {
    	//根据名子查找类型ID
//    	int index = cliReadilyTakeRepository.getSystemCode( projectname );
        return cliReadilyTakeRepository.getTeamListOutputByList(projectname, pagesize, pagenum);
    }

    @Override
    @Transactional
    public CliReadilyTakePost getSaveCliReadilyTakePost(CliReadilyTakePost cliReadilyTakePost , String projectname ) {
        cliReadilyTakePost.setId(commonRepository.getNewId("cliReadilyTakePost"));
        cliReadilyTakePost.setHit(0);
        cliReadilyTakePost.setCnmu(0);
        cliReadilyTakePost.setStatus(1);
        Date date = new Date();
        cliReadilyTakePost.setUptime(date);
        cliReadilyTakePost.setAudittime(date);
        //使用直接存储的方法有SQL语法兼容性问题
        //return cliReadilyTakeRepository.save(cliReadilyTakePost);
        int index = cliReadilyTakeRepository.getSystemCode( projectname );
        return cliReadilyTakeRepository.getSaveCliReadilyTakePost(cliReadilyTakePost , projectname,index );
    }

    @Override
    @Transactional
    public CliReadilyTakeComment getSaveCliReadilyCommentPost(int rid, int uid, int region, int project,
                                                              String projectname, String uname, String telephone, String content) {
    	int id = commonRepository.getNewId("cliReadilyTakeComment") ;
    	int index = cliReadilyTakeRepository.getSystemCode( projectname );
        return cliReadilyTakeRepository.getSaveCliReadilyCommentPost(rid, uid, region, project, projectname, uname, telephone, content , id ,index);
    }

	@Override
	public int getPhonebyStatus(String telephone) {
		 return cliReadilyTakeRepository.getPhonebyStatus(  telephone );
	}

	
	@Override
	public int delpost(int id, int uid) {
		return cliReadilyTakeRepository.delpost(id, uid);
	}

	
	@Override
	public int collectpost(int pid, int uid) {
		int id=commonRepository.getNewId("collectPost");
		
		return cliReadilyTakeRepository.collectpost(id,pid,uid);
	}

	
	@Override
	public List<Reporttype> getreporttype(int uid, int type) {
		return cliReadilyTakeRepository.getreporttype(uid,type);
	}

	
	@Override
	@Transactional
	public int reportpost(ReportPost reportpost) {
		reportpost.setId(commonRepository.getNewId("reportPost"));
		return cliReadilyTakeRepository.reportpost(reportpost);
	}

	
	@Override
	public List<CliReadilyTakeArticleOutput>  getmypost(int uid,int pagesize,int pagenum) {
		return cliReadilyTakeRepository.getmypost(uid,pagesize,pagenum);
		
	}
	
	@Override
	public List<CliReadilyTakeArticleOutput>  getmycollectpost(int uid,int pagesize,int pagenum) {
		return cliReadilyTakeRepository.getmycollectpost(uid,pagesize,pagenum);
		
	}
	
	@Override
	public int ispraise(int id, int uid) {
		return cliReadilyTakeRepository.ispraise(id, uid);
	}
}
