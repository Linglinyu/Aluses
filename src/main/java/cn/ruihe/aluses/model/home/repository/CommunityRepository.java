package cn.ruihe.aluses.model.home.repository;

import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.entity.Community;
import cn.ruihe.aluses.entity.Communitycontent;
import cn.ruihe.aluses.entity.Communitymember;
import cn.ruihe.aluses.entity.Grouptype;
import cn.ruihe.aluses.model.readilytake.repository.CliReadilyTakeRepositoryImpl;
import cn.ruihe.leancloud.LeancloudSDKService;

import cn.ruihe.utils.DBUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

/**
 * @Author dhc
 * @Date 2015-07-20 16-18
 */
@Repository
public class CommunityRepository{
	
    @PersistenceContext
    EntityManager entityManager;

    private SQLQuery setCommunityScalar(String sql) {
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("groupname", StringType.INSTANCE);
        query.addScalar("groupdescribe", StringType.INSTANCE);
        query.addScalar("grouppic", StringType.INSTANCE);
        query.addScalar("groupowner", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("region", IntegerType.INSTANCE);
        query.addScalar("project", IntegerType.INSTANCE);
        query.addScalar("ownerrole", IntegerType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("ismemberaudit", IntegerType.INSTANCE);
        query.addScalar("avosid", StringType.INSTANCE);
        query.addScalar("grouptype", IntegerType.INSTANCE);
        query.addScalar("reportnum", IntegerType.INSTANCE);
        
        query.setResultTransformer(Transformers.aliasToBean(Community.class));
        entityManager.close();
        return query;
    }

    public int getCommunitiesCount(Integer projectId, String key) {
        String sql = "SELECT COUNT(\"id\") FROM \"community\" WHERE \"status\" IN(0,1,2,4)";
        if (projectId != null) {
            sql += " AND \"project\"=:project";
        }
        if(!StringUtils.isEmpty(key)) {
            sql += " AND \"groupname\" LIKE :key";
        }
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        if(projectId != null) {
            query.setParameter("project", projectId);
        }
        if(!StringUtils.isEmpty(key)) {
            query.setParameter("key", "%" + key + "%");
        }
        int count = DBUtils.getCountByHibernate(query.uniqueResult());
        entityManager.close();
        return count;
    }
    
    

    // 获取圈子列表
    public Pager<Community> getCommunities(Integer projectId, int page, int size, String key,int count) {
//        int count = getCommunitiesCount(projectId, key);

        Pager<Community> pager = new Pager<Community>(page, size, count).strict();

        String sql = "SELECT * FROM \"community\" WHERE \"status\" IN (0,1,2,4)";
        if (projectId != null) {
            sql += " AND \"project\"=:project";
            //sql += " AND \"project\"=" +projectId+ "";
        }
        if(!StringUtils.isEmpty(key)) {
            sql += " AND \"groupname\" LIKE :key";
            //sql += " AND \"groupname\" LIKE "+ key +"";
        }
        sql += " ORDER BY \"id\" DESC";
        sql = String.format("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (%s) A WHERE ROWNUM <= %d) WHERE RN >= %d", sql, pager.getEndIndex(), pager.getStartIndex());

        SQLQuery query = setCommunityScalar(sql);

        if(projectId != null) {
            query.setParameter("project", projectId);
        }
        if(!StringUtils.isEmpty(key)) {
            query.setParameter("key", "%" + key + "%");
        }
        pager.setDatas(query.list());
        System.out.println(pager.getDatas().toString());
        entityManager.close();
        return pager;
    }

    // 获取圈子内容
    public Community getCommunity(int id) {
//        String sql = "SELECT * FROM \"community\" WHERE \"id\"=:id AND \"status\" IN (0,1,2,4)";
        String sql = "SELECT \"id\",\"groupname\",\"groupdescribe\",\"grouppic\",\"groupowner\",\"groupowner\",\"uname\",\"telephone\",\"region\"," +
        		"\"project\",\"ownerrole\",\"status\",\"ismemberaudit\",\"avosid\",\"grouptype\",\"reportnum\" FROM \"community\" WHERE \"id\"=:id AND \"status\" IN (0,1,2,4)";
        SQLQuery query = setCommunityScalar(sql);
        query.setParameter("id", id);
        
        entityManager.close();
        return (Community) query.uniqueResult();
    }

    // 改变圈子状态
    public int changeCommunityStatus(int id, int status) {
        String sql = "UPDATE \"community\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        //调用创建圈子接口
        if( status == 1 ){
        	Community community = getCommunity( id );
        	if( community != null ){
        		new LeancloudSDKService().getLeancloudByCreate( community.getGroupname(), community.getGroupname());
        	}
        }
        
        entityManager.close();
        return query.executeUpdate();
    }

    // 改变圈子是否需要审核
    public int changeCommunityAudit(int id, int audit) {
        String sql = "UPDATE \"community\" SET \"ismemberaudit\"=:audit WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("audit", audit);
        query.setParameter("id", id);
        
        entityManager.close();
        return query.executeUpdate();
    }

    // 改变圈子信息
    public Community updateCommunity(Community community) {
        String sql = "UPDATE \"community\" SET \"groupname\"=:groupname,\"project\"=:project,\"uname\"=:uname,\"telephone\"=:telephone,\"groupdescribe\"=:groupdescribe,\"region\"=:region,\"status\"=:status,\"ismemberaudit\"=:ismemberaudit," +
        		"\"grouptype\"=:grouptype WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("groupname", community.getGroupname());
        query.setParameter("project", community.getProject());
        query.setParameter("uname", community.getUname());
        query.setParameter("telephone", community.getTelephone());
        query.setParameter("groupdescribe", community.getGroupdescribe());
        query.setParameter("region", community.getRegion());
        query.setParameter("status", community.getStatus());
        query.setParameter("ismemberaudit", community.getIsmemberaudit());
        query.setParameter("grouptype", community.getGrouptype());
        query.setParameter("id", community.getId());
        query.executeUpdate();
        
        entityManager.close();
        return community;
    }
    
    // 添加圈子信息
    public Community addCommunity(Community community) {
        if (this.existByGroupNameAndProjectId(community.getGroupname(),community.getProject())){
            return null;

        }
        String sql = "UPDATE \"community\" SET \"groupname\"=:groupname,\"project\"=:project,\"uname\"=:uname,\"telephone\"=:telephone,\"groupdescribe\"=:groupdescribe,\"region\"=:region,\"status\"=:status,\"ismemberaudit\"=:ismemberaudit WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("groupname", community.getGroupname());
        query.setParameter("project", community.getProject());
        query.setParameter("uname", community.getUname());
        query.setParameter("telephone", community.getTelephone());
        query.setParameter("groupdescribe", community.getGroupdescribe());
        query.setParameter("region", community.getRegion());
        query.setParameter("status", community.getStatus());
        query.setParameter("ismemberaudit", community.getIsmemberaudit());
        query.setParameter("id", community.getId());
        query.executeUpdate();
        
        entityManager.close();
        return community;
    }

    // 获取成员列表
    public List<Communitymember> getCommunityMembers(int cid) {
        String sql = "SELECT * FROM \"communitymember\" WHERE \"groupid\"=:cid AND \"status\" IN(0,1,2,3) ORDER BY \"role\" DESC, \"id\" ASC";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("cid", cid);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("groupid", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("role", IntegerType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("applydate", DateType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Communitymember.class));
        
        entityManager.close();
        return query.list();
    }

    // 更改成员状态
    public int changeCommunityMemberStatus(int id, int status , int groupid , String uname ) {
        String sql = "UPDATE \"communitymember\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        
        //调用创建圈子接口
        if( status == 1 ){
        	Community community = getCommunity( groupid );
        	if( community != null ){
        		//把玩家加入到圈子里
        		new LeancloudSDKService().getLeancloudByAdd( community.getAvosid(), false , uname );
        	}  		
        }
        
        entityManager.close();
        return query.executeUpdate();
    }

    //根据圈子成员主键ID查找圈子ID
	 public int  getCommunitymemberByPkId( int pkid ) {
		try {
			String sql = "SELECT \"groupid\" FROM \"communitymember\" WHERE \"id\"=:pkid";
			
			Query query = (Query) entityManager.createNativeQuery(sql).setParameter("pkid",pkid);
			if (((javax.persistence.Query) query).getResultList().size() == 0) {
				return -1;
			}else {
				query = (Query) entityManager.createNativeQuery(sql).setParameter("pkid",pkid);
				return CliReadilyTakeRepositoryImpl.getBigDecimal( query.getSingleResult() ).intValue();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		entityManager.close();
		return -1;
	 }
    // 获取聊天记录
    public Pager<Communitycontent> getCommunityChats(int cid, int page, int size) {
        String ctsql = "SELECT COUNT(*) FROM \"communitycontent\" WHERE \"groupid\"=:cid AND \"status\" IN(1,3)";
        SQLQuery cq = entityManager.createNativeQuery(ctsql).unwrap(SQLQuery.class);
        cq.setParameter("cid", cid);
        int count = DBUtils.getCountByHibernate(cq.uniqueResult());

        Pager<Communitycontent> pager = new Pager<Communitycontent>(page, size, count).strict();

        String sql = "SELECT * FROM \"communitycontent\" WHERE \"groupid\"=:cid AND \"status\" IN(1,3) ORDER BY \"senddate\" ASC";
        sql = String.format("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (%s) A WHERE ROWNUM <= %d) WHERE RN >= %d", sql, pager.getEndIndex(), pager.getStartIndex());

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("cid", cid);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("senddate", TimestampType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Communitycontent.class));

        pager.setDatas(query.list());
        
        entityManager.close();
        return pager;
    }

    public boolean existByGroupNameAndProjectId(String groupName,int projectId){
        String sql="SELECT COUNT(ID) FROM community WHERE groupname=:groupname AND project=:project";
        SQLQuery query=entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("groupname",groupName);
        query.setParameter("project",projectId);
        int count = DBUtils.getCountByHibernate(query.uniqueResult());
        entityManager.close();
        return count>0;
    }

    // 修改聊天状态
    public int changeCommunityChatStatus(int id, int status) {
        String sql = "UPDATE \"communitycontent\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        
        entityManager.close();
        return query.executeUpdate();
    }

	//圈子标签列表
	public List<Grouptype> getGrouptypeTagList() {
		String sql = "SELECT \"id\",\"typename\",\"describe\" FROM \"grouptype\"  ORDER BY \"id\" ASC";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Grouptype.class));
        
        entityManager.close();
        return query.list();
	}

	public Grouptype addGrouptypeTag(Grouptype tag) {
		String sql = "INSERT INTO \"grouptype\"(\"id\",\"typename\",\"describe\") " +
                "VALUES(:id, :typename, :describe)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", tag.getId());
        query.setParameter("typename", tag.getTypename());
        query.setParameter("describe", tag.getDescribe());

        query.executeUpdate();
        
        entityManager.close();
        return tag;
	}

	public Grouptype getGrouptypeTag(int id) {
		String sql = "SELECT * FROM \"grouptype\" WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", id);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Grouptype.class));
        Object o = query.uniqueResult();
        if (o != null) {
            return (Grouptype) o;
        }
        
        entityManager.close();
        return null;
	}

	public Grouptype getGrouptypeTag(Grouptype tag) {
		String sql = "UPDATE \"grouptype\" SET \"typename\"=:typename,\"describe\"=:describe WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", tag.getId());
        query.setParameter("typename", tag.getTypename());
        query.setParameter("describe", tag.getDescribe());

        query.executeUpdate();
        
        entityManager.close();
        return tag;
	}

	public int deleteTag(int id) {
		String sql = "DELETE from  \"reportPost\" WHERE  \"id\"=:id";
		SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
		query.setParameter("id", id);
		query.executeUpdate();
		
		entityManager.close();
		return query.executeUpdate();
	}

	/**
	 * @param cid
	 * @param page
	 * @param size
	 * @return
	 */
	public Pager<Communitymember> getCommunityMembers(int cid, Integer page,
			int size,int count) {
		Pager<Communitymember> pager = new Pager<Communitymember>(page, size, count).strict();
		String sql = "SELECT * FROM \"communitymember\" WHERE \"groupid\"=:cid AND \"status\" IN(0,1,2,3) ORDER BY \"role\" DESC, \"id\" ASC";
        
        sql = String.format("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (%s) A WHERE ROWNUM <= %d) WHERE RN >= %d", sql, pager.getEndIndex(), pager.getStartIndex());

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("cid", cid);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("groupid", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("role", IntegerType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("applydate", DateType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Communitymember.class));
        
        entityManager.close();
        pager.setDatas(query.list());
        return pager;
	}

	/**
	 * @param cid
	 * @return
	 */
	public int getCommunitiesCount(int cid) {
		String sql = "SELECT COUNT(\"id\") FROM \"communitymember\" WHERE \"groupid\"=:cid AND \"status\" IN(0,1,2,3)";
		SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("cid", cid);
        int count = DBUtils.getCountByHibernate(query.uniqueResult());
        entityManager.close();
        return count;
	}
}
