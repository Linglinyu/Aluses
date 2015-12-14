package cn.ruihe.aluses.model.readilytake.repository;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.crypto.Data;

import cn.ruihe.aluses.model.home.repository.ProjectRepository;

import cn.ruihe.utils.DBUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ruihe.aluses.entity.CliReadilyTakeComment;
import cn.ruihe.aluses.entity.CliReadilyTakePost;
import cn.ruihe.aluses.entity.Community;
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
import cn.ruihe.leancloud.LeancloudSDKService;
import cn.ruihe.utils.DProperties;
import cn.ruihe.utils.TypeUen;

/**
 * @Author DHC
 * @Date 2015-07-15 15:57:16
 */
public class CliReadilyTakeRepositoryImpl implements CliReadilyTakeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    CliReadilyTakeRepository cliReadilyTakeRepository;

    //01
    @Override
    public List<CliReadilyTakeTagOutput> getCliReadilyTakeTagList() {
        String sql = "SELECT \"id\",\"parentid\",\"typename\",\"describe\",\"color\",\"status\" FROM \"sysReadilyTtake\" WHERE \"status\" = 1";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);
        query.addScalar("parentid", IntegerType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("color", StringType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(CliReadilyTakeTagOutput.class));

        entityManager.close();
        return query.list();
    }

    //02
    @Override
    public List<CliReadilyTakeArticleOutput> getTeamListOutputByList(String projectname, int pagesize, int pagenum) {
        //根据名子查找类型ID
//    	int index = getSystemCode( projectname );
        SQLQuery query = null;
        try {
            String sqlesc = String.format("WHERE RN BETWEEN %d AND %d ", pagesize, pagenum);

            String sql = String.format("SELECT \"id\",\"rid\",\"uid\",\"uname\",\"telephone\",\"region\",\"project\",\"title\",\"hit\",\"cnmu\",\"content\",\"uptime\",\"audittime\",\"status\",\"pictures\",\"smallpic\",\"praisenum\""
                    + " FROM (  SELECT A.*, ROWNUM RN FROM (SELECT * FROM \"cliReadilyTakePost\" where \"status\"=1 AND " +
                    "\"project\" in (SELECT \"ID\" FROM \"ORGANIZATIONS\" WHERE \"ORGNAME\"=:projectname) order by \"audittime\" desc) A)  " + sqlesc);

            query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

            query.setParameter("projectname", java.net.URLDecoder.decode(projectname, "utf-8"));
            query.addScalar("id", IntegerType.INSTANCE);
            query.addScalar("rid", IntegerType.INSTANCE);
            query.addScalar("uid", IntegerType.INSTANCE);
            query.addScalar("uname", StringType.INSTANCE);
            query.addScalar("telephone", StringType.INSTANCE);
            query.addScalar("title", StringType.INSTANCE);
            query.addScalar("hit", IntegerType.INSTANCE);
            query.addScalar("cnmu", IntegerType.INSTANCE);
            query.addScalar("content", StringType.INSTANCE);
            query.addScalar("uptime", DateType.INSTANCE);
            query.addScalar("status", IntegerType.INSTANCE);
            query.addScalar("pictures", StringType.INSTANCE);
            query.addScalar("smallpic", StringType.INSTANCE);
            query.addScalar("praisenum", IntegerType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(CliReadilyTakeArticleOutput.class));
            entityManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.close();
        return query.list();
    }

    //03
    @Override
    public List<CliReadilyTakeCommentOutput> findByRid(int rid) {
        String sql = "SELECT \"uid\",\"uname\",\"content\",\"update\" FROM \"cliReadilyTakeComment\" WHERE \"rid\" = :rid AND \"status\"=1 ORDER BY \"update\"";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);


        query.setParameter("rid", rid);

        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("update", DateType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(CliReadilyTakeCommentOutput.class));
        entityManager.close();
        return query.list();
    }

    //04
    @Override
    public List<TeamListOutput> getTeamList(int uid, int region, int project, String projectname, int type) {

        //根据名子查找类型ID
//    	int index = getSystemCode(projectname);
        SQLQuery query = null;
        String sql = "";
        if (type == 1) {
            try {
                sql = "SELECT \"id\",\"avosid\",\"groupowner\",\"groupname\",\"groupdescribe\",\"uname\",\"grouppic\",\"telephone\",\"status\",\"grouptype\",\"ismemberaudit\" as \"isaudit\" FROM \"community\" WHERE " +
                        "\"project\" in (SELECT \"ID\" FROM \"ORGANIZATIONS\" WHERE \"ORGNAME\"=:projectname) AND (\"status\"=1 or (\"groupowner\"=" + uid + " and \"status\" in(0,1,2) ) )";
                query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

                query.setParameter("projectname", java.net.URLDecoder.decode(projectname, "utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            query.addScalar("id", IntegerType.INSTANCE);
            query.addScalar("avosid", StringType.INSTANCE);
            query.addScalar("groupowner", IntegerType.INSTANCE);
            query.addScalar("groupname", StringType.INSTANCE);
            query.addScalar("groupdescribe", StringType.INSTANCE);
            query.addScalar("uname", StringType.INSTANCE);
            query.addScalar("grouppic", StringType.INSTANCE);
            query.addScalar("telephone", StringType.INSTANCE);
            query.addScalar("status", IntegerType.INSTANCE);
            query.addScalar("grouptype", IntegerType.INSTANCE);
            query.addScalar("isaudit", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(TeamListOutput.class));
            List<TeamListOutput> teamListOutputList = new ArrayList<TeamListOutput>();
            teamListOutputList = query.list();
            if (teamListOutputList != null && teamListOutputList.size() > 0) {
                for (int i = 0; i < teamListOutputList.size(); i++) {
                    teamListOutputList.get(i).setGrouptypename(getGrouptypename(teamListOutputList.get(i).getGrouptype()));
                    teamListOutputList.get(i).setMemstatus(getMemstatus(teamListOutputList.get(i).getId(), uid));
                    teamListOutputList.get(i).setMemnum(getMemberNum(teamListOutputList.get(i).getId()));
                    teamListOutputList.get(i).setMemname(getMemname(teamListOutputList.get(i).getId(), uid));
                }

            }
            entityManager.close();
            return teamListOutputList;

        } else if (type == 2) {
            //communitymember
            String sqlSec = String.format("SELECT \"groupid\" FROM \"communitymember\" WHERE \"status\"=1 AND \"uid\"=%d", uid);

            sql = "SELECT \"id\",\"avosid\",\"groupowner\",\"groupname\",\"groupdescribe\",\"uname\",\"grouppic\",\"telephone\",\"ismemberaudit\" as \"isaudit\" FROM \"community\" WHERE \"status\"=1 AND \"id\" in("
                    + sqlSec + ")";

            query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.addScalar("id", IntegerType.INSTANCE);
            query.addScalar("avosid", StringType.INSTANCE);
            query.addScalar("groupowner", IntegerType.INSTANCE);
            query.addScalar("groupname", StringType.INSTANCE);
            query.addScalar("groupdescribe", StringType.INSTANCE);
            query.addScalar("uname", StringType.INSTANCE);
            query.addScalar("grouppic", StringType.INSTANCE);
            query.addScalar("telephone", StringType.INSTANCE);
            query.addScalar("isaudit", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(TeamListOutput.class));
            List<TeamListOutput> teamListOutputList = new ArrayList<TeamListOutput>();
            teamListOutputList = query.list();
            if (teamListOutputList != null && teamListOutputList.size() > 0) {
                for (int i = 0; i < teamListOutputList.size(); i++) {
                    teamListOutputList.get(i).setGrouptypename(getGrouptypename(teamListOutputList.get(i).getGrouptype()));
                    teamListOutputList.get(i).setMemstatus(getMemstatus(teamListOutputList.get(i).getId(), uid));
                    teamListOutputList.get(i).setMemnum(getMemberNum(teamListOutputList.get(i).getId()));
                    teamListOutputList.get(i).setMemname(getMemname(teamListOutputList.get(i).getId(), uid));
                }

            }
            entityManager.close();
            return teamListOutputList;
        } else if (type == 3) {
            //communitymember
            String sqlSec = String.format("SELECT \"groupid\" FROM \"communitymember\" WHERE \"status\"=1 AND \"uid\"=%d", uid);

            sql = "SELECT \"id\",\"avosid\",\"groupowner\",\"groupname\",\"groupdescribe\",\"uname\",\"grouppic\",\"telephone\",\"ismemberaudit\" as \"isaudit\" FROM \"community\" WHERE \"status\"=1 AND \"id\" not in("
                    + sqlSec + ")";

            query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.addScalar("id", IntegerType.INSTANCE);
            query.addScalar("avosid", StringType.INSTANCE);
            query.addScalar("groupowner", IntegerType.INSTANCE);
            query.addScalar("groupname", StringType.INSTANCE);
            query.addScalar("groupdescribe", StringType.INSTANCE);
            query.addScalar("uname", StringType.INSTANCE);
            query.addScalar("grouppic", StringType.INSTANCE);
            query.addScalar("telephone", StringType.INSTANCE);
            query.addScalar("isaudit", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(TeamListOutput.class));
            List<TeamListOutput> teamListOutputList = new ArrayList<TeamListOutput>();
            teamListOutputList = query.list();
            if (teamListOutputList != null && teamListOutputList.size() > 0) {
                for (int i = 0; i < teamListOutputList.size(); i++) {
                    teamListOutputList.get(i).setGrouptypename(getGrouptypename(teamListOutputList.get(i).getGrouptype()));
                    teamListOutputList.get(i).setMemstatus(getMemstatus(teamListOutputList.get(i).getId(), uid));
                    teamListOutputList.get(i).setMemnum(getMemberNum(teamListOutputList.get(i).getId()));
                    teamListOutputList.get(i).setMemname(getMemname(teamListOutputList.get(i).getId(), uid));
                }

            }
            entityManager.close();
            return teamListOutputList;
        } else {
            List<TeamListOutput> teamListOutputList = new ArrayList<TeamListOutput>();
            entityManager.close();
            return teamListOutputList;
        }

    }

    //05
    @Override
    public List<TeamChatListOutput> getTeamChatList(int uid, int groupid, int pagesize, int pagenum) {
        String sql = String.format("SELECT \"id\",\"groupid\",\"uid\",\"senddate\",\"content\",\"uname\"" + " FROM (  SELECT A.*, ROWNUM RN FROM (SELECT * FROM \"communitycontent\") A)  WHERE \"status\"=1 AND \"groupid\"=:groupidId AND RN BETWEEN %d AND %d", pagesize, pagenum);

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        query.setParameter("groupidId", groupid);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("groupid", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("senddate", DateType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);


        query.setResultTransformer(Transformers.aliasToBean(TeamChatListOutput.class));
        entityManager.close();
        return query.list();
    }

    //06
    @Override
    public List<ActivityThemeOutput> getActivityThemeList() {
        String sql = "SELECT \"id\",\"parentid\",\"typename\",\"describe\",\"status\" FROM \"sysEventCallTheme\" WHERE \"status\"=1";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("parentid", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(ActivityThemeOutput.class));
        entityManager.close();
        return query.list();
    }

    //07
    @Override
    public List<ActivityListOutput> getActivityListOutput(int uid, int region, int project, String projectname, int type) {

        //根据名子查找类型ID
//    	int index = getSystemCode( projectname );
        String sql = "";
        SQLQuery query = null;
        if (type == 1) {//获取移动端当前登录用户所属项目的全部活动列表
            try {
                sql = "SELECT \"id\",\"eid\",\"uid\",\"uname\",\"title\",\"content\",\"uptime\",\"begintime\",\"endtime\",\"enrollendtime\","
                        + "\"telephone\" , \"phone\" ,\"upperlimit\",\"money\",\"isonlinepay\",\"pictures\",\"praisenum\",\"enrollnum\" ,\"status\" ,\"address\",\"result\"  FROM \"sysEventCallArticle\" " +
                        "WHERE \"status\"=1 AND \"project\" in (SELECT \"ID\" FROM \"ORGANIZATIONS\" WHERE \"ORGNAME\"=:projectname) order by \"id\" desc";
                query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

                query.setParameter("projectname", java.net.URLDecoder.decode(projectname, "utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (type == 2) {//获取移动端当前登录用户报名参加的全部活动列表；

            sql = "SELECT \"id\",\"eid\",\"uid\",\"uname\",\"title\",\"content\",\"uptime\",\"begintime\",\"endtime\",\"enrollendtime\","
                    + "\"telephone\" , \"phone\" ,\"upperlimit\",\"money\",\"isonlinepay\",\"pictures\",\"praisenum\",\"enrollnum\" ,\"status\" ,\"address\",\"result\"  FROM \"sysEventCallArticle\" "
                    + "WHERE \"status\"=1 AND \"id\" in("
                    + "SELECT \"eid\" FROM \"cliPartake\" WHERE \"uid\"=:uid) order by \"id\" desc";
            query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.setParameter("uid", uid);
        } else if (type == 3) {//获取热门活动列表；
            try {
                sql = "SELECT * FROM (SELECT \"id\",\"eid\",\"uid\",\"uname\",\"title\",\"content\",\"uptime\",\"begintime\",\"endtime\",\"enrollendtime\","
                        + "\"telephone\" , \"phone\" ,\"upperlimit\",\"money\",\"isonlinepay\",\"pictures\",\"praisenum\",\"enrollnum\" ,\"status\" ,\"address\",\"result\"  " +
                        "FROM \"sysEventCallArticle\" WHERE \"status\"=1 AND \"project\" in (SELECT \"ID\" FROM \"ORGANIZATIONS\" WHERE \"ORGNAME\"=:projectname) AND \"endtime\">sysdate order by \"id\" desc) WHERE ROWNUM<4";
                query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
                query.setParameter("projectname", java.net.URLDecoder.decode(projectname, "utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("eid", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("title", StringType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("phone", StringType.INSTANCE);
        query.addScalar("uptime", DateType.INSTANCE);
        query.addScalar("begintime", DateType.INSTANCE);
        query.addScalar("endtime", DateType.INSTANCE);
        query.addScalar("enrollendtime", DateType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("upperlimit", IntegerType.INSTANCE);
        query.addScalar("money", DoubleType.INSTANCE);
        query.addScalar("isonlinepay", IntegerType.INSTANCE);
        query.addScalar("pictures", StringType.INSTANCE);
        query.addScalar("praisenum", IntegerType.INSTANCE);
        query.addScalar("enrollnum", IntegerType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("address", StringType.INSTANCE);
        query.addScalar("result", StringType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(ActivityListOutput.class));
        List<ActivityListOutput> activityList = new ArrayList<ActivityListOutput>();
        activityList = query.list();
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (type == 2)///查询我报名的接口
                    activityList.get(i).setIsenroll(1);
                else {
                    System.out.println(uid);
                    System.out.println(activityList.get(i).getId());
                    int j = getActivityNum(uid, activityList.get(i).getId());
                    System.out.println(j);
                    //TODO  返回的值是9，数值不对
                    if (j > 0) {
                        activityList.get(i).setIsenroll(1);
                    } else {
                        activityList.get(i).setIsenroll(2);
                    }
                }
            }
        }
        entityManager.close();
        return activityList;
    }


    @Override
    public int getIsenroll(int id, int eid) {
        String sql = "";
        SQLQuery query = null;
        String selectIsEnrollSql = "SELECT COUNT(\"id\") FROM \"cliPartake\" "
                + "WHERE \"uid\"=:uid AND \"eid\"=:eid";
        query = entityManager.createNativeQuery(selectIsEnrollSql).unwrap(SQLQuery.class);
        query.setParameter("uid", id);
        query.setParameter("eid", eid);
        int result=DBUtils.getCountByHibernate(query.uniqueResult());
        entityManager.close();
        return result;

    }

    //08
    @Override
    public CliReadilyTakePost getSaveCliReadilyTakePost(CliReadilyTakePost cliReadilyTakePost, String projectname, int index) {


        System.out.println(cliReadilyTakePost.toString());
        //根据名子查找类型ID
//    	int index = getSystemCode( projectname );


        if (index <= 0) {
            return null;
        }

        String sql = "INSERT INTO \"cliReadilyTakePost\""
                + "(\"id\", \"rid\",\"uid\",\"uname\",\"telephone\",\"region\",\"project\",\"title\",\"hit\",\"cnmu\",\"content\",\"uptime\",\"audittime\",\"status\",\"pictures\",\"smallpic\",\"praisenum\",\"reportnum\")"
                + " VALUES(:id, :rid, :uid, :uname, :telephone, :region, :project, :title, :hit, :cnmu, :content, :uptime, :audittime, :status, :pictures, :smallpic, :praisenum, :reportnum)";

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        query.setParameter("id", cliReadilyTakePost.getId());
        query.setParameter("rid", cliReadilyTakePost.getRid());
        query.setParameter("uid", cliReadilyTakePost.getUid());
        query.setParameter("uname", cliReadilyTakePost.getUname());
        query.setParameter("telephone", cliReadilyTakePost.getTelephone());
        query.setParameter("region", cliReadilyTakePost.getRegion());
        query.setParameter("project", index);
        query.setParameter("title", cliReadilyTakePost.getTitle());
        query.setParameter("hit", 0);
        query.setParameter("cnmu", 0);
        query.setParameter("content", cliReadilyTakePost.getContent());
        query.setParameter("uptime", cliReadilyTakePost.getUptime());
        query.setParameter("audittime", cliReadilyTakePost.getAudittime());
        query.setParameter("pictures", cliReadilyTakePost.getPictures());
        query.setParameter("smallpic", cliReadilyTakePost.getSmallpic());
        query.setParameter("praisenum", 0);
        query.setParameter("reportnum", 0);

        int parvalue = getParameterCode(TypeUen.clireadilytake);
//      查询参数设置表parameter中随手拍文章是否需要进行审核，如果需要进行审核(parvalue为1)，在第3步中写入数据库中该文章的状态字段为0，不需要进行审核(parvalue为0)，状态字段就为1
        query.setParameter("status", parvalue == 1 ? 0 : 1);
        query.executeUpdate();
        entityManager.close();
        return cliReadilyTakePost;
    }

    //09 发布随手拍评论
    @Override
    public CliReadilyTakeComment getSaveCliReadilyCommentPost(int rid, int uid, int region, int project,
                                                              String projectname, String uname, String telephone, String content, int id, int index) {
//		int index = getSystemCode( projectname );

        if (index <= 0) {
            return null;
        }

        Date date = new Date();

        String sql = "INSERT INTO \"cliReadilyTakeComment\""
                + "(\"id\", \"rid\",\"uid\",\"region\",\"project\",\"uname\",\"telephone\",\"content\",\"auditdate\",\"update\",\"status\")"
                + " VALUES(:id, :rid, :uid, :region, :project, :uname, :telephone, :content, :auditdate, :udpate, :status)";

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", id);
        query.setParameter("rid", rid);
        query.setParameter("uid", uid);
        query.setParameter("region", region);
        query.setParameter("project", index);
        query.setParameter("status", 1);
        query.setParameter("uname", uname);
        query.setParameter("telephone", telephone);
        query.setParameter("content", content);
        query.setParameter("auditdate", date);
        query.setParameter("udpate", date);
        query.executeUpdate();


        String sql1 = "UPDATE \"cliReadilyTakePost\" SET \"cnmu\"=\"cnmu\"+1  WHERE \"id\"=:id";
        SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
        query1.setParameter("id", rid);
        query1.executeUpdate();
        entityManager.close();
        return null;
    }

    //10.发布活动文章
    @Override
    public SysEventCallArticle getSaveEventCallArticle(SysEventCallArticle sysEventCallArticle, String projectname, int pkId, int index) {

        try {
//			int index = getSystemCode( projectname );

            if (index <= 0) {
                return null;
            }
            String sql = "INSERT INTO \"sysEventCallArticle\""
                    + "(\"id\", \"eid\",\"uid\",\"uname\",\"title\",\"telephone\",\"region\",\"project\",\"begintime\",\"endtime\",\"content\","
                    + "\"uptime\",\"status\",\"address\",\"enrollendtime\",\"upperlimit\",\"phone\",\"pictures\",\"smallpic\",\"praisenum\",\"isonlinepay\",\"money\",\"enrollnum\")"
                    + " VALUES(:id, :eid, :uid, :uname, :title, :telephone, :region, :project, :begintime, :endtime, :content, "
                    + ":uptime, :status, :address, :enrollendtime, :upperlimit, :phone, :pictures, :smallpic, :praisenum, :isonlinepay, :money, :enrollnum)";

            SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.setParameter("id", pkId);
            query.setParameter("eid", sysEventCallArticle.getEid());
            query.setParameter("uid", sysEventCallArticle.getUid());
            query.setParameter("title", sysEventCallArticle.getTitle());
            query.setParameter("uname", sysEventCallArticle.getUname());
            query.setParameter("telephone", sysEventCallArticle.getTelephone());
            query.setParameter("region", sysEventCallArticle.getRegion());
            query.setParameter("project", index);
            query.setParameter("begintime", sysEventCallArticle.getBegintime());
            query.setParameter("endtime", sysEventCallArticle.getEndtime());
            query.setParameter("content", sysEventCallArticle.getContent());
            query.setParameter("uptime", new Date());

            int parvalue = getParameterCode(TypeUen.article);
            query.setParameter("status", parvalue == 1 ? 0 : 1);

            query.setParameter("address", sysEventCallArticle.getAddress() == null ? "" : sysEventCallArticle.getAddress());
            query.setParameter("enrollendtime", sysEventCallArticle.getEnrollendtime() == null ? new Date() : sysEventCallArticle.getEnrollendtime());
            query.setParameter("upperlimit", sysEventCallArticle.getUpperlimit());
            query.setParameter("phone", sysEventCallArticle.getPhone() == null ? "" : sysEventCallArticle.getPhone());
            query.setParameter("pictures", sysEventCallArticle.getPictures());
            query.setParameter("smallpic", sysEventCallArticle.getSmallpic());
            query.setParameter("praisenum", 0);
            query.setParameter("isonlinepay", sysEventCallArticle.getIsonlinepay() == null ? 1 : sysEventCallArticle.getIsonlinepay());
            query.setParameter("money", sysEventCallArticle.getMoney());
            query.setParameter("enrollnum", sysEventCallArticle.getEnrollnum() == null ? 1 : sysEventCallArticle.getEnrollnum());

            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.close();
        return sysEventCallArticle;
    }


    private boolean existByGroupNameAndProjectId(String groupName, int projectId) {
        String sql = "SELECT COUNT(\"id\") FROM \"community\" WHERE \"groupname\"=:groupname AND \"project\"=:project AND \"status\"<>3";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("groupname", groupName);
        query.setParameter("project", projectId);
        int count= DBUtils.getCountByHibernate(query.uniqueResult());
        entityManager.close();
        return count > 0;
    }


    //11.申请圈子
    @Override
    /**
     * result -4 ,圈子已经存在
     */
    public int getSvaeTeamChatCommunity(Community community, String projectname, int pkId, String username, int index) {

//		int index = getSystemCode( projectname );

        if (index <= 0) {
            return -2;
        }
        String teamName = getParameterTeam(community.getGroupname());

        //首先判断该圈子是否已存在
        if (teamName != null && "".equals(teamName)) {
            return -3;
        }

        if (this.existByGroupNameAndProjectId(community.getGroupname(), community.getProject())) {
            return -4;
        }

        String sql = "INSERT INTO \"community\""
                + "(\"id\", \"groupname\",\"groupdescribe\",\"grouppic\",\"groupowner\",\"uname\",\"telephone\",\"region\",\"project\",\"ownerrole\",\"status\",\"ismemberaudit\",\"avosid\",\"grouptype\",\"reportnum\")"
                + " VALUES(:id, :groupname, :groupdescribe,:grouppic,:groupowner, :uname, :telephone, :region, :projectId, :ownerrole, :status, :ismemberaudit, :avosid, :grouptype, :reportnum)";

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", pkId);
        query.setParameter("groupname", community.getGroupname());
        query.setParameter("groupdescribe", community.getGroupdescribe());
        query.setParameter("grouppic", community.getGrouppic());
        query.setParameter("groupowner", community.getGroupowner());
        //判断username是否为空（ios客户端2.3版本，如果该值为空，为作处理，直接传了null）
        if (null == username || "".equals(username.trim()) || "null".equals(username.trim())) {
            String telephone = community.getTelephone();
            username = telephone.substring(0, telephone.length() - (telephone.substring(3)).length()) + "****" + telephone.substring(7);
        }
        query.setParameter("uname", username);
        query.setParameter("telephone", community.getTelephone());
        query.setParameter("region", community.getRegion() == null ? 1 : community.getRegion());
        query.setParameter("projectId", index);
        query.setParameter("ownerrole", community.getOwnerrole() == null ? 1 : community.getOwnerrole());
        query.setParameter("grouptype", community.getGrouptype());
        query.setParameter("reportnum", 0);

        int parvalue = getParameterCode(TypeUen.Team);
        //parvalue ==1  需要审核,parvalue==0  不需要审核
        query.setParameter("status", parvalue == 1 ? 0 : 1);
        String avosid = "";
//        if( parvalue==0 ){
        //调用远程创建
        String key = new LeancloudSDKService().getLeancloudByCreate(community.getGroupowner() + ";" + username, community.getGroupname());
        avosid = key;
        query.setParameter("avosid", key);
//        }
//        else {
//        	query.setParameter("avosid", avosid);
//		}

        query.setParameter("ismemberaudit", community.getIsmemberaudit() == null ? 1 : community.getIsmemberaudit());

        query.executeUpdate();

        //插入成员
        Communitymember communitymember = new Communitymember();
        communitymember.setGroupid(pkId);
        communitymember.setUid(community.getGroupowner());
        communitymember.setUname(username);
        communitymember.setTelephone(community.getTelephone());
        communitymember.setRegion(community.getRegion() == null ? 1 : community.getRegion());
        //1为普通成员，2为圈主；
        communitymember.setRole(2);
        int newPkid = 0;

        synchronized (DProperties.lock) {
            String sql2 = "SELECT MAX(\"id\")+1 AS id FROM \"communitymember\"";
            SQLQuery q = entityManager.createNativeQuery(sql2).unwrap(SQLQuery.class);
            Object o = q.uniqueResult();
            if (o instanceof BigInteger) {
                newPkid = ((BigInteger) o).intValue();
            }
            else if (o instanceof BigDecimal) {
                newPkid = ((BigDecimal) o).intValue();
            } else if (o instanceof Integer) {
                newPkid = (int) o;
            } else {
                if (o == null) {
                    newPkid = 1;
                } else {
                    newPkid = Integer.parseInt(o.toString());
                }
            }
        }
        getSaveTeambyadd(communitymember, projectname, newPkid, avosid, true, index);
        entityManager.close();
        return 1;
    }

    //12.申请加入圈子
    @Override
    public boolean getSaveTeambyadd(Communitymember communitymember, String projectname, int pkid, String clientId, boolean isSave, int index) {

//		int index = getSystemCode( projectname );

        if (index <= 0) {
            return false;
        }

        clientId = getParameterTeamByPkObjectId(communitymember.getGroupid());
        if (clientId == null) {
            return false;
        }
        String sql = "INSERT INTO \"communitymember\""
                + "(\"id\", \"groupid\",\"uid\",\"uname\",\"telephone\",\"region\",\"project\",\"role\",\"status\",\"managerid\",\"applydate\",\"auditdate\",\"auditorid\",\"auditorrole\",\"groupname\")"
                + " VALUES(:id, :groupid, :uid, :uname, :telephone, :region, :project, :role, :status, :managerid, :applydate, :auditdate, :auditorid, :auditorrole, :groupname)";


        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", pkid);
        query.setParameter("groupid", communitymember.getGroupid());
        query.setParameter("uid", communitymember.getUid());
        //判断username是否为空（ios客户端2.3版本，如果该值为空，为作处理，直接传了null）
        if (null == communitymember.getUname() || "".equals(communitymember.getUname().trim()) || "null".equals(communitymember.getUname().trim())) {
            String telephone = communitymember.getTelephone();
            communitymember.setUname(telephone.substring(0, telephone.length() - (telephone.substring(3)).length()) + "****" + telephone.substring(7));
        }
        query.setParameter("uname", communitymember.getUname());
        query.setParameter("telephone", communitymember.getTelephone());
        query.setParameter("region", communitymember.getRegion());
        query.setParameter("project", index);
        query.setParameter("role", communitymember.getRole() == null ? 1 : communitymember.getRole());
        query.setParameter("auditorid", 1);
        query.setParameter("auditorrole", 1);
        query.setParameter("groupname", communitymember.getGroupname() == null ? communitymember.getUname() : communitymember.getGroupname());

        if (isSave) {
            new LeancloudSDKService().getLeancloudByAdd(clientId, false, communitymember.getUid() + ";" + communitymember.getUname());
            query.setParameter("status", 1);
        } else {
//          int ismemberaudit = getParameterTeamByPkId( communitymember.getGroupid() );
//    		if( ismemberaudit == 1 ){//需要审核
//    			query.setParameter("status",0);
//    		}else if( ismemberaudit == 0 ){
            //调用远程创建
            new LeancloudSDKService().getLeancloudByAdd(clientId, false, communitymember.getUid() + ";" + communitymember.getUname());
            query.setParameter("status", 1);
//    		}
        }

        //不需要审核
        query.setParameter("auditdate", new Date());//审核时间
        query.setParameter("managerid", 1);
        query.setParameter("applydate", new Date());//申请时间

//        query.setParameter("auditorid", communitymember.getAuditorid());//审核人
//        query.setParameter("auditorrole", communitymember.getAuditorrole());//审核人身份

        query.executeUpdate();
        entityManager.close();
        return true;
    }

    //13.发送圈子消息
    @Override
    public boolean getSaveTeambypush(Communitycontent communitycontent, int pkId) {
        String sql = "INSERT INTO \"communitycontent\""
                + "(\"id\", \"groupid\",\"uid\",\"uname\",\"telephone\",\"content\",\"status\",\"senddate\")"
                + " VALUES(:id, :groupid, :uid, :uname, :telephone, :content, :status, :senddate)";

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", pkId);
        query.setParameter("groupid", communitycontent.getGroupid());
        query.setParameter("uid", communitycontent.getUid());
        query.setParameter("uname", communitycontent.getUname());
        query.setParameter("telephone", communitycontent.getTelephone());
        query.setParameter("content", communitycontent.getContent());
        query.setParameter("status", 1);
        query.setParameter("senddate", new Date());//申请时间

        query.executeUpdate();
        entityManager.close();
        return true;
    }

    /*
        用户uid是否赞过此贴子id
     */
    private boolean existPraise(int id, int uid) {
        String sql = "SELECT COUNT(*) AS theCount FROM \"praise\" WHERE \"id\"=:id AND \"uid\"=:uid";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", id);
        query.setParameter("uid", uid);
        int count=DBUtils.getCountByHibernate(query.uniqueResult());
        entityManager.close();
        return count > 0;

    }


    //14.随手拍、活动召集文章点赞
    @Override
    public boolean getPraise(int id, int type, int uid) {
        Date date = new Date();
        switch (type) {
            case 1://1表示随手拍文章点赞  cliReadilyTakePost
                if (this.existPraise(id, uid)) {
                    return true;
                }
                String sql = "UPDATE \"cliReadilyTakePost\" SET \"praisenum\"=\"praisenum\"+1  WHERE \"id\"=:id";
                SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
                query.setParameter("id", id);
                query.executeUpdate();

                String sql1 = "insert into \"praise\" (\"id\",\"uid\",\"uptime\") values (:id,:uid,:uptime)";
                SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
                query1.setParameter("id", id);
                query1.setParameter("uid", uid);
                query1.setParameter("uptime", date);
                query1.executeUpdate();
                break;
            case 2://2表示活动文章点赞  sysEventCallArticle
                String sql2 = "UPDATE \"sysEventCallArticle\" SET \"praisenum\"=\"praisenum\"+1  WHERE \"id\"=:id";
                SQLQuery query2 = entityManager.createNativeQuery(sql2).unwrap(SQLQuery.class);
                query2.setParameter("id", id);
                query2.executeUpdate();
                break;
            default:
                break;
        }
        entityManager.close();
        return true;
    }


    //15.报名活动
    @Override
    public boolean getApplyActivity(int type, int uid, int region, String project, String projectname, String telephone, int id, int pkId, int index) {

//		int index = getSystemCode( projectname );
        if (index <= 0) {
            return false;
        }
        if (type == 0) {
            String sql = "DELETE FROM  \"cliPartake\" WHERE \"eid\"= :eid AND \"uid\"= :uid";
            SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.setParameter("eid", id);
            query.setParameter("uid", uid);
            query.executeUpdate();

            //活动报名人数减1
            String sql1 = "UPDATE  \"sysEventCallArticle\""
                    + "SET \"enrollnum\"=\"enrollnum\"-1  WHERE \"id\"=:id";
            SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
            query1.setParameter("id", id);
            query1.executeUpdate();

        } else {
            String sql = "INSERT INTO \"cliPartake\""
                    + "(\"id\", \"eid\",\"uid\",\"uname\",\"telephone\",\"region\",\"project\",\"ptime\",\"audittime\")"
                    + " VALUES(:id, :eid, :uid, :uname, :telephone, :region, :project, :ptime, :audittime)";

            SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.setParameter("id", pkId);
            query.setParameter("eid", id);
            query.setParameter("uid", uid);
            query.setParameter("uname", "unameuname");
            query.setParameter("telephone", telephone);
            query.setParameter("region", region);
            query.setParameter("project", index);
            query.setParameter("ptime", new Date());//申请时间
            query.setParameter("audittime", new Date());//申请时间
            query.executeUpdate();

            //活动报名人数加1
            String sql1 = "UPDATE  \"sysEventCallArticle\""
                    + "SET \"enrollnum\"=\"enrollnum\"+1  WHERE \"id\"=:id";
            SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
            query1.setParameter("id", id);
            query1.executeUpdate();
        }
        entityManager.close();
        return true;
    }

    // 16 获取圈子成员列表
    @Override
    public List<TeamUserListOutput> getTeamuserList(int groupid) {
        String sql = "SELECT \"id\",\"status\" ,\"role\",\"telephone\" ,\"uname\" ,\"uid\",\"groupid\",\"groupname\",\"mempic\"" +
                " FROM \"communitymember\"  where \"groupid\" = :groupid and \"status\" in (0,1)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("groupid", groupid);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("role", IntegerType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
//      query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("groupid", IntegerType.INSTANCE);
        query.addScalar("groupname", StringType.INSTANCE);
        query.addScalar("mempic", StringType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(TeamUserListOutput.class));
        entityManager.close();
        return query.list();
    }

    // 17 圈子成员删除、设置圈主、取消圈主
    @Override
    public boolean getTeamByOperation(int id, int type, int uid) {

        String sql = "SELECT \"id\",\"status\",\"role\",\"telephone\",\"uname\"," +
                "\"status\",\"uid\",\"groupid\" FROM \"communitymember\"  WHERE \"id\"=:id ";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", id);


        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("role", IntegerType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("groupid", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(TeamUserListOutput.class));

        List<TeamUserListOutput> list = query.list();
        if (list == null || list.size() <= 0) {
            return false;
        }
        TeamUserListOutput tTeamUserListOutput = list.get(0);

        switch (type) {
            case 1:
                String avosid = getParameterTeamByPkObjectId(tTeamUserListOutput.getGroupid());
                if (avosid == null) {
                    return false;
                }
                //删除平台数据
                new LeancloudSDKService().getLeancloudByAdd(avosid, true, tTeamUserListOutput.getUid() + ";" + tTeamUserListOutput.getUname());

                //删除数据库数据
                String sql2 = "DELETE FROM \"communitymember\" WHERE \"id\"=:id";
//				String sql2 = "UPDATE \"communitymember\" SET \"status\"=4 WHERE \"id\"=:id";
                SQLQuery query2 = entityManager.createNativeQuery(sql2).unwrap(SQLQuery.class);
                query2.setParameter("id", id);
                query2.executeUpdate();

                break;
            case 2:
                //设置新的圈主
                String sql4 = "UPDATE \"communitymember\" SET \"role\"=2 WHERE \"id\"=:id";
                SQLQuery query4 = entityManager.createNativeQuery(sql4).unwrap(SQLQuery.class);
                query4.setParameter("id", id);
                query4.executeUpdate();
                break;
            case 3:
                //把以前的圈主修改为不是圈主
                String sql3 = "UPDATE \"communitymember\" SET \"role\"=1 WHERE \"id\"=:id";
                SQLQuery query3 = entityManager.createNativeQuery(sql3).unwrap(SQLQuery.class);
                query3.setParameter("id", id);
                query3.executeUpdate();
                break;
            default:
                break;
        }
        entityManager.close();
        return true;
    }

    // 18 验证手机号是否是黑名单
    @Override
    public int getPhonebyStatus(String telephone) {
        String sql = "SELECT \"id\" FROM \"blacklist\" WHERE \"telephone\"=:telephone";

        //1表示是，0表示否
        Query query = (Query) entityManager.createNativeQuery(sql).setParameter("telephone", telephone);
        if (((javax.persistence.Query) query).getResultList().size() == 0) {
            entityManager.close();
            return 0;
        } else {
//			query = (Query) entityManager.createNativeQuery(sql).setParameter("telephone",telephone);
            entityManager.close();
            return 1;
        }
    }

    //19.验证当前用户是否已加入传入圈子
    @Override
    public int getIsGroupMem(int groupid, String telephone) {
        String sql = "SELECT \"id\" FROM \"communitymember\" WHERE \"groupid\"=:groupid  and  \"telephone\"=:telephone and \"status\" != 4";

        //1表示是，0表示否
        Query query = (Query) entityManager.createNativeQuery(sql).setParameter("groupid", groupid).setParameter("telephone", telephone);
        if (((javax.persistence.Query) query).getResultList().size() == 0) {
            entityManager.close();
            return 0;
        } else {
//			query = (Query) entityManager.createNativeQuery(sql).setParameter("groupid",groupid).setParameter("uid",uid);
            entityManager.close();
            return 1;
        }
    }

    //20 删除邻里帖子
    @Override
    public int delpost(int id, int uid) {
        String sql = "SELECT \"uid\" FROM \"cliReadilyTakePost\" WHERE \"id\"=:id and \"uid\"=:uid";
        int status = 0;
        //1表示成功，0表示已存在
        Query query = (Query) entityManager.createNativeQuery(sql).setParameter("id", id).setParameter("uid", uid);
        if (((javax.persistence.Query) query).getResultList().size() == 0) {
            entityManager.close();
            return 0;

        } else {
            String sql1 = "update \"cliReadilyTakePost\" SET \"status\"=3 WHERE \"id\"=:id";
            SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
            query1.setParameter("id", id);
            query1.executeUpdate();
            status = 1;
        }
        entityManager.close();
        return status;
    }


    /**
     * 21 收藏帖子
     *
     * @return
     */
    @Override
    public int collectpost(int id, int pid, int uid) {
        String sql = "SELECT \"id\" FROM \"collectPost\" WHERE \"pid\"=:pid and \"uid\"=:uid";
        int status = 0;
        Date date = new Date();
        //1表示成功，0表示已存在
        Query query = (Query) entityManager.createNativeQuery(sql).setParameter("pid", pid).setParameter("uid", uid);
        if (((javax.persistence.Query) query).getResultList().size() == 0) {
            String sql1 = "insert into  \"collectPost\" " +
                    "(\"id\",\"pid\",\"uid\",\"collecttime\")" +
                    "values(:id,:pid,:uid,:collecttime)";
            SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
            query1.setParameter("id", id);
            query1.setParameter("pid", pid);
            query1.setParameter("uid", uid);
            query1.setParameter("collecttime", date);
            query1.executeUpdate();
            status = 1;

        } else {
            entityManager.close();
            return 0;
        }
        entityManager.close();
        return status;
    }

    /**
     * 22 获取帖子举报类型
     *
     * @return
     */
    @Override
    public List<Reporttype> getreporttype(int uid, int type) {
        String sql = "SELECT \"id\",\"typename\",\"category\",\"describe\",\"status\" FROM \"reporttype\" WHERE \"status\" = 1 and \"category\" =:category order by \"id\"";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("category", type);
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("category", IntegerType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(Reporttype.class));
        entityManager.close();
        return query.list();
    }

    /**
     * 23 举报帖子或者圈子
     *
     * @return
     */
    @Override
    public int reportpost(ReportPost reportpost) {
        String sql = "SELECT \"id\" FROM \"reportPost\" WHERE \"pid\"=:pid and \"uid\"=:uid and \"category\"=:category";
        int status = 0;
        Date date = new Date();
        //1表示成功，0表示已存在
        Query query = (Query) entityManager.createNativeQuery(sql).setParameter("pid", reportpost.getId()).setParameter("uid", reportpost.getUid()).setParameter("category", reportpost.getCategory());
        if (((javax.persistence.Query) query).getResultList().size() == 0) {
            String sql1 = "insert into  \"reportPost\"" +
                    "(\"id\",\"pid\",\"category\",\"uid\", \"type\", \"content\", \"uptime\", \"pic\", \"status\")" +
                    "values (:id, :pid, :category, :uid, :type, :content, :uptime, :pic, :status)";
            SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
            System.out.println(reportpost.toString());
            query1.setParameter("id", reportpost.getId());
            query1.setParameter("pid", reportpost.getPid());
            query1.setParameter("category", reportpost.getCategory());
            query1.setParameter("uid", reportpost.getUid());
            query1.setParameter("type", reportpost.getType());
            query1.setParameter("content", reportpost.getContent());
            query1.setParameter("uptime", date);
            query1.setParameter("pic", reportpost.getPic());
            query1.setParameter("status", 0);
            query1.executeUpdate();

            if (reportpost.getCategory() == 1) {
                String sql2 = "update   \"cliReadilyTakePost\" set \"reportnum\"=\"reportnum\"+1 where \"id\"=:id";
                SQLQuery query2 = entityManager.createNativeQuery(sql2).unwrap(SQLQuery.class);
                query2.setParameter("id", reportpost.getPid());
                query2.executeUpdate();
            } else if (reportpost.getCategory() == 2) {
                String sql2 = "update   \"community\" set \"reportnum\"=\"reportnum\"+1 where \"id\"=:id";
                SQLQuery query2 = entityManager.createNativeQuery(sql2).unwrap(SQLQuery.class);
                query2.setParameter("id", reportpost.getPid());
                query2.executeUpdate();
            }
            status = 1;

        } else {
            entityManager.close();
            return 0;
        }
        entityManager.close();
        return status;
    }

    /**
     * 24 获取我的帖子
     *
     * @return
     */
    @Override
    public List<CliReadilyTakeArticleOutput> getmypost(int uid, int pagesize, int pagenum) {
        String sqlesc = String.format("WHERE RN BETWEEN %d AND %d ", pagesize, pagenum);

        String sql = String.format("SELECT \"id\",\"rid\",\"uid\",\"uname\",\"telephone\",\"region\",\"project\",\"title\",\"hit\",\"cnmu\",\"content\",\"uptime\",\"audittime\",\"status\",\"pictures\",\"smallpic\",\"praisenum\""
                + " FROM (  SELECT A.*, ROWNUM RN FROM (SELECT * FROM \"cliReadilyTakePost\" where \"uid\"=:uid and \"status\"<>3 order by \"audittime\" desc) A)  " + sqlesc);

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        System.out.println(uid);
        query.setParameter("uid", uid);
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("rid", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
//	        query.addScalar("region", IntegerType.INSTANCE);
//	        query.addScalar("project", IntegerType.INSTANCE);
        query.addScalar("title", StringType.INSTANCE);
        query.addScalar("hit", IntegerType.INSTANCE);
        query.addScalar("cnmu", IntegerType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("uptime", DateType.INSTANCE);
//	        query.addScalar("audittime", DateType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("pictures", StringType.INSTANCE);
        query.addScalar("smallpic", StringType.INSTANCE);
        query.addScalar("praisenum", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(CliReadilyTakeArticleOutput.class));
        entityManager.close();
        return query.list();
    }

    /**
     * 25 获取圈子类型
     *
     * @return
     */
    @Override
    public List<Grouptype> getgrouptype() {
        String sql = "SELECT \"id\",\"typename\",\"describe\" FROM \"grouptype\" WHERE 1 = 1";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Grouptype.class));
        entityManager.close();
        return query.list();
    }

    /**
     * 26 修改圈子成员昵称
     *
     * @return
     */
    @Override
    public int setgroupname(int gid, int uid, String groupname) {
        int status = 0;
        try {
            String sql = "update \"communitymember\" SET \"groupname\"=:groupname WHERE \"groupid\"=:groupid and \"uid\"=:uid";
            SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.setParameter("groupname", groupname);
            query.setParameter("groupid", gid);
            query.setParameter("uid", uid);
            query.executeUpdate();
            status = 1;
        } catch (Exception e) {
            status = 0;
        }
        entityManager.close();
        return status;
    }

    /**
     * 27 修改圈子信息
     *
     * @return
     */
    @Override
    public int setgroupinfo(Community community) {
        int status = 0;
        System.out.println("bbbb");
        try {
            String sql = null;
            if (community.getGroupname() != null) {
                sql = "update \"community\" SET \"groupname\"=:groupname  WHERE \"id\"=:id";
                SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
                query.setParameter("groupname", community.getGroupname());
                query.setParameter("id", community.getId());
                query.executeUpdate();
                //修改leancloud服务器上的名称
//					new LeancloudSDKService().getLeancloudByAdd( avosid  , true , tTeamUserListOutput.getUid()+";"+tTeamUserListOutput.getUname() );
                entityManager.close();
                return 1;
            }
            if (community.getGroupdescribe() != null) {
                sql = "update \"community\" SET \"groupdescribe\"=:groupdescribe  WHERE \"id\"=:id";
                SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
                query.setParameter("groupdescribe", community.getGroupdescribe());
                query.setParameter("id", community.getId());
                query.executeUpdate();
                entityManager.close();
                return 1;
            }
            if (community.getGrouptype() != null) {
                sql = "update \"community\" SET \"grouptype\"=:grouptype  WHERE \"id\"=:id";
                SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
                query.setParameter("grouptype", community.getGrouptype());
                query.setParameter("id", community.getId());
                query.executeUpdate();
                entityManager.close();
                return 1;
            }
            if (community.getIsmemberaudit() != null) {
                sql = "update \"community\" SET \"ismemberaudit\"=:ismemberaudit  WHERE \"id\"=:id";
                SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
                query.setParameter("ismemberaudit", community.getIsmemberaudit());
                query.setParameter("id", community.getId());
                query.executeUpdate();
                entityManager.close();
                return 1;
            }
            if (community.getGrouppic() != null) {
                sql = "update \"community\" SET \"grouppic\"=:grouppic  WHERE \"id\"=:id";
                SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
                query.setParameter("grouppic", community.getGrouppic());
                query.setParameter("id", community.getId());
                query.executeUpdate();
                entityManager.close();
                return 1;
            }

            status = 1;
        } catch (Exception e) {
            status = 0;
        }
        entityManager.close();
        return status;
    }

    /**
     * 28 解散圈子
     *
     * @return
     */
    @Override
    public int delgroup(int gid, int uid) {
        int status = 0;
        try {
            String sql = "delete from  \"communitymember\"  WHERE \"groupid\"=:groupid";
            SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.setParameter("groupid", gid);
            query.executeUpdate();

            String sql1 = "delete from  \"community\"  WHERE \"id\"=:id";
            SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
            query1.setParameter("id", gid);
            query1.executeUpdate();
            status = 1;
        } catch (Exception e) {
            status = 0;
        }
        entityManager.close();
        return status;
    }

    /**
     * 29 退出圈子
     *
     * @return
     */
    @Override
    public int exitgroup(int gid, int uid) {
        int status = 0;

        try {
            String sql = "delete from  \"communitymember\"  WHERE \"groupid\"=:groupid and \"uid\"=:uid";
            SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.setParameter("groupid", gid);
            query.setParameter("uid", uid);
            query.executeUpdate();

            String avosid = getParameterTeamByPkObjectId(gid);
            //删除平台数据
            new LeancloudSDKService().getLeancloudByAdd(avosid, true, uid + ";");
            status = 1;
        } catch (Exception e) {
            status = 0;
        }
        entityManager.close();
        return status;
    }

    /**
     * 32 收藏的帖子
     *
     * @return
     */
    @Override
    public List<CliReadilyTakeArticleOutput> getmycollectpost(int uid, int pagesize, int pagenum) {
        String sqlesc = String.format("WHERE RN BETWEEN %d AND %d ", pagesize, pagenum);

        String sql = String.format("SELECT \"id\",\"rid\",\"uid\",\"uname\",\"telephone\",\"region\",\"project\",\"title\",\"hit\",\"cnmu\",\"content\",\"uptime\",\"audittime\",\"status\",\"pictures\",\"smallpic\",\"praisenum\""
                + " FROM (  SELECT A.*, ROWNUM RN FROM (SELECT * FROM \"cliReadilyTakePost\" where \"id\" in(select \"pid\" from \"collectPost\" where \"uid\"=:uid) and \"status\"<>3 order by \"audittime\" desc) A)  " + sqlesc);

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        System.out.println(uid);
        query.setParameter("uid", uid);
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("rid", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
//	        query.addScalar("region", IntegerType.INSTANCE);
//	        query.addScalar("project", IntegerType.INSTANCE);
        query.addScalar("title", StringType.INSTANCE);
        query.addScalar("hit", IntegerType.INSTANCE);
        query.addScalar("cnmu", IntegerType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("uptime", DateType.INSTANCE);
//	        query.addScalar("audittime", DateType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("pictures", StringType.INSTANCE);
        query.addScalar("smallpic", StringType.INSTANCE);
        query.addScalar("praisenum", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(CliReadilyTakeArticleOutput.class));
        entityManager.close();
        return query.list();
    }

    /**
     * 33 是否点赞
     *
     * @return
     */
    @Override
    public int ispraise(int id, int uid) {
        String sql = "SELECT \"id\" FROM \"praise\" WHERE \"id\"=" + id + "  and  \"uid\"=" + uid + "";

        //1表示是，0表示否
        Query query = (Query) entityManager.createNativeQuery(sql);
        if (((javax.persistence.Query) query).getResultList().size() == 0) {
            entityManager.close();
            return 0;
        } else {
//				query = (Query) entityManager.createNativeQuery(sql).setParameter("groupid",groupid).setParameter("uid",uid);
            entityManager.close();
            return 1;
        }
    }

    @Override
    public int getSystemCode(String name) {
        String sql = "";

        try {
            sql = "SELECT max(\"ID\") FROM \"ORGANIZATIONS\" WHERE \"ORGNAME\"=:name and rownum = 1";
            // Object ob =  entityManager.createNativeQuery(sql).setParameter("name",java.net.URLDecoder.decode(name,"utf-8")).getSingleResult();
            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("name", java.net.URLDecoder.decode(name, "utf-8"));
//			Query query = (Query) entityManager.createNativeQuery(sql).setParameter("name",encodeStr(name));
            if (((javax.persistence.Query) query).getResultList().size() == 0) {
                entityManager.close();
                return 0;
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("name", java.net.URLDecoder.decode(name, "utf-8"));
//				query = (Query) entityManager.createNativeQuery(sql).setParameter("name",encodeStr(name));
                int result = getBigDecimal(query.getSingleResult()).intValue();
                entityManager.close();
                return result;
            }
        } catch (Exception e) {
            System.out.println("getSystemCode:" + sql + "   name:" + name);
            e.printStackTrace();
        }
        entityManager.close();
        return 0;
    }

    public String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public int getParameterCode(String parname) {
        try {
            String sql = "SELECT \"parvalue\" FROM \"parameter\" WHERE \"parname\"=:parname";

            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("parname", parname);
            if (((javax.persistence.Query) query).getResultList().size() == 0) {

                return 0;
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("parname", parname);
                int result = getBigDecimal(query.getSingleResult()).intValue();

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //根据玩家传的圈子主键ID  查找是否存在圈子
    public int getParameterTeamByPkId(int pkid) {
        try {
            String sql = "SELECT \"ismemberaudit\" FROM \"community\" WHERE \"id\"=:pkid";

            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("pkid", pkid);
            if (((javax.persistence.Query) query).getResultList().size() == 0) {
                entityManager.close();
                return -1;
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("pkid", pkid);
                int result = getBigDecimal(query.getSingleResult()).intValue();
                entityManager.close();
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.close();
        return -1;
    }

    //根据玩家传的圈子主键ID  查找是否存在圈子
    public String getParameterTeamByPkObjectId(int pkid) {
        try {
            String sql = "SELECT \"avosid\" FROM \"community\" WHERE \"id\"=:pkid";

            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("pkid", pkid);
            if (((javax.persistence.Query) query).getResultList().size() == 0) {

                return null;
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("pkid", pkid);

                String result = query.getSingleResult().toString();

                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    //根据玩家传的圈子名字  查找是否存在圈子
    public String getParameterTeam(String name) {
        try {
            String sql = "SELECT \"avosid\" FROM \"community\" WHERE \"groupname\"=:groupname";

            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("groupname", name);
            List list = ((javax.persistence.Query) query).getResultList();
            if (list == null) {
                return null;
            }
            if (list.size() == 0) {
                entityManager.close();
                return null;
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("groupname", name);
                String result = query.getSingleResult().toString();
                entityManager.close();
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.close();
        return null;
    }

    //获取Memstatus值
    public int getMemstatus(int id, int uid) {
        try {
            String sql = "select \"status\" from (SELECT \"status\" FROM \"communitymember\" WHERE \"groupid\"=:groupid and \"uid\"=:uid order by \"id\" desc) where rownum <2";
            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("groupid", id).setParameter("uid", uid);
            if (((javax.persistence.Query) query).getResultList().size() == 0) {

                return -1;
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("groupid", id).setParameter("uid", uid);

                return Integer.parseInt(query.getSingleResult().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    //获取圈子类型名称
    public String getGrouptypename(int grouptype) {
        try {
            System.out.println(grouptype);
            String sql = "SELECT \"typename\" FROM \"grouptype\" WHERE \"id\"=:id";

            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("id", grouptype);
            if (((javax.persistence.Query) query).getResultList().size() == 0) {

                return "";
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("id", grouptype);

                return query.getSingleResult().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    //获取圈子人数值
    public int getMemberNum(int id) {
        try {
            String sql = "SELECT count(*) FROM \"communitymember\" WHERE  \"groupid\"=:groupid and \"status\" in (0,1)";
            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("groupid", id);
            if (((javax.persistence.Query) query).getResultList().size() == 0) {

                return 0;
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("groupid", id);

                int result = Integer.parseInt(query.getSingleResult().toString());

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //获取活动人数
    public int getActivityNum(int uid, int eid) {
        try {
            String sql = "SELECT COUNT(*) FROM \"cliPartake\" "
                    + "WHERE \"uid\"=:uid  AND \"eid\"=:eid";
            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("uid", uid).setParameter("eid", eid);
            if (((javax.persistence.Query) query).getResultList().size() == 0) {

                return 0;
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("uid", uid).setParameter("eid", eid);
                int result = Integer.parseInt(query.getSingleResult().toString());

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //获取昵称
    public String getMemname(int id, int uid) {
        try {
            String sql = "select \"groupname\" from (SELECT \"groupname\" FROM \"communitymember\" WHERE \"groupid\"=:groupid and \"uid\"=:uid order by \"id\" desc) where rownum <2";

            Query query = (Query) entityManager.createNativeQuery(sql).setParameter("groupid", id).setParameter("uid", uid);
            if (((javax.persistence.Query) query).getResultList().size() == 0) {
                return "";
            } else {
                query = (Query) entityManager.createNativeQuery(sql).setParameter("groupid", id).setParameter("uid", uid);
                String result = query.getSingleResult().toString();

                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigInteger) {
                BigInteger bi = (BigInteger) value;
                ret = new BigDecimal(bi.intValue());
            } else if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
                        + " into a BigDecimal.");
            }
        }
        return ret;
    }

}
