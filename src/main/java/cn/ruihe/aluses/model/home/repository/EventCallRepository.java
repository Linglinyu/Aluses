package cn.ruihe.aluses.model.home.repository;

import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.entity.CliPartake;
import cn.ruihe.aluses.entity.Communitymember;
import cn.ruihe.aluses.entity.SysEventCallArticle;
import cn.ruihe.aluses.entity.SysEventCallTheme;
import cn.ruihe.utils.DBUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * @Author dhc
 * @Date 2015-07-21 10-16
 */
@Repository
public class EventCallRepository {

    @PersistenceContext
    EntityManager entityManager;

    // 获取主题列表
    public List<SysEventCallTheme> getThemes() {
        String sql = "SELECT * FROM \"sysEventCallTheme\" WHERE \"status\" IN(1,3) ORDER BY \"id\" ASC";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("parentid", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(SysEventCallTheme.class));
        entityManager.close();
        return query.list();
    }

    // 获取主题
    public SysEventCallTheme getTheme(int id) {
    	SysEventCallTheme sysEventCallTheme=new SysEventCallTheme();
        String sql = "SELECT * FROM \"sysEventCallTheme\" WHERE \"id\"=:id AND \"status\" IN(1,3)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", id);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("parentid", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(SysEventCallTheme.class));
        sysEventCallTheme=(SysEventCallTheme) query.uniqueResult();
        entityManager.close();
        return sysEventCallTheme;
    }

    // 新增主题
    public SysEventCallTheme addTheme(SysEventCallTheme theme) {
        String sql = "INSERT INTO \"sysEventCallTheme\"(\"id\", \"parentid\", \"typename\", \"describe\", \"status\") " +
                "VALUES(:id, :parentid, :name, :describe, :status)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("name", theme.getTypename());
        query.setParameter("describe", theme.getDescribe());
        query.setParameter("status", theme.getStatus());
        query.setParameter("id", theme.getId());
        query.setParameter("parentid", theme.getParentid());
        query.executeUpdate();
        entityManager.close();
        return theme;
    }

    // 更新主题
    public SysEventCallTheme updateTheme(SysEventCallTheme theme) {
        String sql = "UPDATE \"sysEventCallTheme\" SET \"typename\"=:name,\"describe\"=:describe,\"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("name", theme.getTypename());
        query.setParameter("describe", theme.getDescribe());
        query.setParameter("status", theme.getStatus());
        query.setParameter("id", theme.getId());

        
        query.executeUpdate();
        entityManager.close();
        return theme;
    }

    // 更新主题状态
    public int changeThemeStatus(int id, int status) {
        String sql = "UPDATE \"sysEventCallTheme\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        
        entityManager.close();

        return query.executeUpdate();
    }

    public int getArticleCount(Integer projectId, String key) {
        String sql = "SELECT COUNT(\"id\") FROM \"sysEventCallArticle\" WHERE \"status\" IN(0,1,2)";
        if(projectId != null && projectId != 0) {
            sql += " AND \"project\"=:project";
        }
        if(!StringUtils.isEmpty(key)) {
            sql += " AND \"title\" LIKE :key";
        }
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        if(projectId != null && projectId != 0) {
            query.setParameter("project", projectId);
        }
        if(!StringUtils.isEmpty(key)) {
            query.setParameter("key", "%" + key + "%");
        }
        int count = DBUtils.getCountByHibernate(query.uniqueResult());
       
        entityManager.close();
        return count;
    }

    private SQLQuery setArticleScalar(String sql) {
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("eid", IntegerType.INSTANCE);
        query.addScalar("title", StringType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("region", IntegerType.INSTANCE);
        query.addScalar("project", IntegerType.INSTANCE);
        query.addScalar("begintime", TimestampType.INSTANCE);
        query.addScalar("endtime", TimestampType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("uptime", TimestampType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("address", StringType.INSTANCE);
        query.addScalar("enrollendtime", TimestampType.INSTANCE);
        query.addScalar("upperlimit", IntegerType.INSTANCE);
        query.addScalar("phone", StringType.INSTANCE);
        query.addScalar("pictures", StringType.INSTANCE);
        query.addScalar("smallpic", StringType.INSTANCE);
        query.addScalar("praisenum", IntegerType.INSTANCE);
        query.addScalar("isonlinepay", IntegerType.INSTANCE);
        query.addScalar("money", DoubleType.INSTANCE);
        query.addScalar("enrollnum", IntegerType.INSTANCE);
        query.addScalar("result", StringType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(SysEventCallArticle.class));
        
        entityManager.close();
        return query;
    }

    // 获取活动召集文章列表
    public Pager<SysEventCallArticle> getArticles(Integer projectId, int page, int size, String key,int count) {
//        int count = getArticleCount(projectId, key);

        Pager<SysEventCallArticle> pager = new Pager<SysEventCallArticle>(page, size, count).strict();

        String sql = "SELECT * FROM \"sysEventCallArticle\" WHERE \"status\" IN (0,1,2)";

        if(projectId != null && projectId != 0) {
            sql += " AND \"project\"=:project";
        }
        if(!StringUtils.isEmpty(key)) {
            sql += " AND \"title\" LIKE :key";
        }
        sql += " ORDER BY \"id\" DESC";

        sql = String.format("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (%s) A WHERE ROWNUM <= %d) WHERE RN >= %d", sql, pager.getEndIndex(), pager.getStartIndex());

        SQLQuery query = setArticleScalar(sql);

        if(projectId != null && projectId != 0) {
            query.setParameter("project", projectId);
        }
        if(!StringUtils.isEmpty(key)) {
            query.setParameter("key", "%" + key + "%");
        }

        pager.setDatas(query.list());
        entityManager.close();
        return pager;
    }

    // 获取召集文章
    public SysEventCallArticle getArticle(int id) {
        String sql = "SELECT * FROM \"sysEventCallArticle\" WHERE \"id\"=:id AND \"status\" IN (0,1,2)";
        SQLQuery query = setArticleScalar(sql);
        query.setParameter("id", id);
        query.setResultTransformer(Transformers.aliasToBean(SysEventCallArticle.class));
        SysEventCallArticle sysEventCallArticle=new SysEventCallArticle();
        sysEventCallArticle=(SysEventCallArticle) query.uniqueResult();
        entityManager.close();
        return sysEventCallArticle;
    }

    // 新增召集文章
    public SysEventCallArticle addArticle(SysEventCallArticle article) {
    	System.out.println(article.toString());
        String sql = "INSERT INTO \"sysEventCallArticle\"(\"id\",\"eid\",\"title\",\"uid\",\"uname\",\"telephone\",\"region\",\"project\",\"begintime\",\"endtime\"," +
                "\"content\",\"uptime\",\"status\",\"address\",\"enrollendtime\",\"upperlimit\",\"phone\",\"pictures\",\"smallpic\",\"praisenum\",\"isonlinepay\"," +
                "\"money\",\"enrollnum\") VALUES (:id,:eid,:title,:uid,:uname,:telephone,:region,:project,:begintime,:endtime,:content,:uptime,:status,:address," +
                ":enrollendtime,:uplimit,:phone,:pictrues,:smallpic,:praisenum,:isonlinepay,:money,:enrollnum)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        
        query.setParameter("id", article.getId());
        query.setParameter("eid", article.getEid());
        query.setParameter("title", article.getTitle());
        query.setParameter("uid", article.getUid());
        query.setParameter("uname", article.getUname());
        query.setParameter("telephone", article.getTelephone());
        query.setParameter("region", article.getRegion());
        query.setParameter("project", article.getProject());
        query.setParameter("begintime", article.getBegintime());
        query.setParameter("endtime", article.getEndtime());
        query.setParameter("content", article.getContent());
        query.setParameter("uptime", article.getUptime());
        query.setParameter("status", article.getStatus());
        query.setParameter("address", article.getAddress());
        query.setParameter("enrollendtime", article.getEnrollendtime());
        query.setParameter("uplimit", article.getUpperlimit());
        query.setParameter("phone", article.getPhone());
        query.setParameter("pictrues", article.getPictures());
        query.setParameter("smallpic", article.getSmallpic());
        query.setParameter("praisenum", 0);
        query.setParameter("isonlinepay", article.getIsonlinepay());
        query.setParameter("money", article.getMoney());
        query.setParameter("enrollnum", 0);

        query.executeUpdate();
        entityManager.close();
        return article;
    }

    // 更新召集文章
    public SysEventCallArticle updateArticle(SysEventCallArticle article) {
    	System.out.println(article.toString());
    	if(article.getResult()!=null)
    	{
    	String sql1 = "UPDATE \"sysEventCallArticle\" SET \"result\"=:result WHERE \"id\"=:id";
    	SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
        query1.setParameter("result", article.getResult());
        query1.setParameter("id", article.getId());

        query1.executeUpdate();
        return article;
    	}
    	String sql="";
    	if(article.getPictures()!=null&&article.getPictures().length()>0)
    	{
    		sql = "UPDATE \"sysEventCallArticle\" SET \"eid\"=:eid,\"title\"=:title,\"begintime\"=:begintime," +
                "\"endtime\"=:endtime,\"content\"=:content,\"uptime\"=:uptime, \"status\"=:status,\"address\"=:address," +
                "\"enrollendtime\"=:enrollendtime,\"upperlimit\"=:uplimit,\"phone\"=:phone, \"isonlinepay\"=:isonlinepay," +
                "\"money\"=:money,\"pictures\"=:pictures,\"smallpic\"=:smallpic WHERE \"id\"=:id";
    	}else{
    		sql = "UPDATE \"sysEventCallArticle\" SET \"eid\"=:eid,\"title\"=:title,\"begintime\"=:begintime," +
                    "\"endtime\"=:endtime,\"content\"=:content,\"uptime\"=:uptime, \"status\"=:status,\"address\"=:address," +
                    "\"enrollendtime\"=:enrollendtime,\"upperlimit\"=:uplimit,\"phone\"=:phone, \"isonlinepay\"=:isonlinepay," +
                    "\"money\"=:money WHERE \"id\"=:id";	
    	}
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("eid", article.getEid());
        query.setParameter("title", article.getTitle());
        query.setParameter("begintime", article.getBegintime());
        query.setParameter("endtime", article.getEndtime());
        query.setParameter("content", article.getContent());
        query.setParameter("uptime", article.getUptime());
        query.setParameter("status", article.getStatus());
        query.setParameter("address", article.getAddress());
        query.setParameter("enrollendtime", article.getEnrollendtime());
        query.setParameter("uplimit", article.getUpperlimit());
        query.setParameter("phone", article.getPhone());
        query.setParameter("isonlinepay", article.getIsonlinepay());
        query.setParameter("money", article.getMoney());
        query.setParameter("id", article.getId());
        if(article.getPictures()!=null&&article.getPictures().length()>0)
        {
	        query.setParameter("pictures", article.getPictures());
	        query.setParameter("smallpic", article.getSmallpic());
        }
        
//        query.setParameter("result", article.getResult());

        query.executeUpdate();
        entityManager.close();
        return article;
    }

    // 改变文章状态
    public int changeArticleStatus(int id, int status) {
        String sql = "UPDATE \"sysEventCallArticle\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        int result=query.executeUpdate();
        entityManager.close();
        return result;
    }

    // 获取参与活动列表
    public List<CliPartake> getPartakes(int eid) {
        String sql = "SELECT * FROM \"cliPartake\" WHERE \"eid\"=:eid ORDER BY \"id\" DESC";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("eid", eid);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("region", IntegerType.INSTANCE);
        query.addScalar("project", IntegerType.INSTANCE);
        query.addScalar("ptime", TimestampType.INSTANCE);
        query.addScalar("audittime", TimestampType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(CliPartake.class));
        entityManager.close();
        return query.list();
    }

    // 审核成员
    public Date changeCliPartakeAudit(int id) {
        String sql = "UPDATE \"cliPartake\" SET \"audittime\"=:audittime WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        Date date = new Date();
        query.setParameter("id", id);
        query.setParameter("audittime", date);
        query.executeUpdate();
        entityManager.close();
        return date;
    }

	/**
	 * @param eid
	 * @return
	 */
	public int getCommunitiesCount(int eid) {
		String sql = "SELECT COUNT(\"id\") FROM \"cliPartake\" WHERE \"eid\"=:eid";
		SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("eid", eid);
        int count=DBUtils.getCountByHibernate(query.uniqueResult());
        entityManager.close();
        return count;
	}

	/**
	 * @param eid
	 * @param page
	 * @param size
	 * @param count
	 * @return
	 */
	public Pager<CliPartake> getPartakes(int eid, Integer page, int size,
			int count) {
		Pager<CliPartake> pager = new Pager<CliPartake>(page, size, count).strict();
		String sql = "SELECT * FROM \"cliPartake\" WHERE \"eid\"=:eid ORDER BY \"id\" DESC";
        
        sql = String.format("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (%s) A WHERE ROWNUM <= %d) WHERE RN >= %d", sql, pager.getEndIndex(), pager.getStartIndex());

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("eid", eid);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("region", IntegerType.INSTANCE);
        query.addScalar("project", IntegerType.INSTANCE);
        query.addScalar("ptime", TimestampType.INSTANCE);
        query.addScalar("audittime", TimestampType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(CliPartake.class));
        entityManager.close();
        pager.setDatas(query.list());
        return pager;
	}

	/**
	 * @param id
	 * @return
	 */
	public int deleteCliPartake(int id) {
		String sql = "DELETE from  \"cliPartake\" WHERE  \"id\"=:id";
		SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
		query.setParameter("id", id);
		query.executeUpdate();
		
		entityManager.close();
		
		//活动报名人数减1
        String sql1 = "UPDATE  \"sysEventCallArticle\""
                + "SET \"enrollnum\"=\"enrollnum\"-1  WHERE \"id\"=(select \"eid\" from  \"cliPartake\" WHERE  \"id\"=:id)";
        SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
        query1.setParameter("id", id);
        query1.executeUpdate();
        
		return query.executeUpdate();
	}
}
