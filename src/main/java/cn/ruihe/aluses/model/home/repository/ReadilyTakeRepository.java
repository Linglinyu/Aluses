package cn.ruihe.aluses.model.home.repository;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.entity.*;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeTagOutput;
import cn.ruihe.utils.DBUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @Author DHC
 * @Date 2015-07-18 14:50:01
 */
@Repository
public class ReadilyTakeRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<CliReadilyTakeTagOutput> getCliReadilyTakeTagList() {
        String sql = "SELECT \"id\",\"parentid\",\"typename\",\"describe\",\"color\",\"status\" FROM \"sysReadilyTtake\" WHERE \"status\" IN(1, 3) ORDER BY \"id\" ASC";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);
        query.addScalar("parentid", IntegerType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("color", StringType.INSTANCE);

        query.setResultTransformer(Transformers
                .aliasToBean(CliReadilyTakeTagOutput.class));
        entityManager.close();
        return query.list();
    }

    // 增加标签
    public SysReadilyTtake addTag(SysReadilyTtake tag) {
        String sql = "INSERT INTO \"sysReadilyTtake\"(\"id\",\"parentid\",\"typename\",\"describe\", \"color\",\"status\") "
                + "VALUES(:id, 0, :typename, :describe, :color, :status)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("id", tag.getId());
        query.setParameter("typename", tag.getTypename());
        query.setParameter("describe", tag.getDescribe());
        query.setParameter("color", tag.getColor());
        query.setParameter("status", tag.getStatus());

        query.executeUpdate();
        entityManager.close();
        return tag;
    }

    // 修改标签
    public SysReadilyTtake updateTag(SysReadilyTtake tag) {
        String sql = "UPDATE \"sysReadilyTtake\" SET \"typename\"=:typename,\"describe\"=:describe,\"color\"=:color,\"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("id", tag.getId());
        query.setParameter("typename", tag.getTypename());
        query.setParameter("describe", tag.getDescribe());
        query.setParameter("color", tag.getColor());
        query.setParameter("status", tag.getStatus());

        query.executeUpdate();
        entityManager.close();
        return tag;
    }

    // 改变标签状态
    public int changeTagStatus(int id, int status) {
        String sql = "UPDATE \"sysReadilyTtake\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        entityManager.close();
        return result;
    }

    public SysReadilyTtake getTag(int id) {
        String sql = "SELECT * FROM \"sysReadilyTtake\" WHERE \"id\"=:id AND \"status\" IN(1,3)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("id", id);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("parentid", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("color", StringType.INSTANCE);

        query.setResultTransformer(Transformers
                .aliasToBean(SysReadilyTtake.class));
        Object o = query.uniqueResult();
        if (o != null) {
            entityManager.close();
            return (SysReadilyTtake) o;
        }
        entityManager.close();
        return null;
    }

    public int getPostsCount(Integer projectId, String key) {
        String sql = "SELECT COUNT(\"id\") FROM \"cliReadilyTakePost\" WHERE \"status\" IN(0,1,2)";
        if (projectId != null) {
            sql += " AND \"project\"=:project";
        }
        if (key != null) {
            sql += " AND \"title\" LIKE :key";
        }
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        if (projectId != null) {
            query.setParameter("project", projectId);
        }
        if (key != null) {
            query.setParameter("key", "%" + key + "%");
        }

        int count = DBUtils.getCountByHibernate(query.uniqueResult());
        entityManager.close();
        return count;
    }

    private SQLQuery setCliReadilyTakePostScalar(String sql) {
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("rid", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("region", IntegerType.INSTANCE);
        query.addScalar("project", IntegerType.INSTANCE);
        query.addScalar("title", StringType.INSTANCE);
        query.addScalar("hit", IntegerType.INSTANCE);
        query.addScalar("cnmu", IntegerType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("uptime", DateType.INSTANCE);
        query.addScalar("audittime", DateType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("pictures", StringType.INSTANCE);
        query.addScalar("smallpic", StringType.INSTANCE);
        query.addScalar("praisenum", IntegerType.INSTANCE);
        query.addScalar("reportnum", IntegerType.INSTANCE);
        query.setResultTransformer(Transformers
                .aliasToBean(CliReadilyTakePost.class));
        entityManager.close();
        return query;
    }

    // 获取随手拍列表
    public Pager<CliReadilyTakePost> getPosts(Integer projectId, int page,
                                              int size, String key, int count) {
        // int count = getPostsCount(projectId, key);

        Pager<CliReadilyTakePost> pager = new Pager<CliReadilyTakePost>(page,
                size, count).strict();

        String sql = "SELECT * FROM \"cliReadilyTakePost\" WHERE \"status\" IN (0,1,2)";
        if (projectId != null) {
            sql += " AND \"project\"=:project";
        }
        if (key != null) {
            sql += " AND \"title\" LIKE :key";
        }
        sql += "  ORDER BY \"id\" DESC";

        sql = String
                .format("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (%s) A WHERE ROWNUM <= %d) WHERE RN >= %d",
                        sql, pager.getEndIndex(), pager.getStartIndex());

        SQLQuery query = setCliReadilyTakePostScalar(sql);

        if (projectId != null) {
            query.setParameter("project", projectId);
        }
        if (key != null) {
            query.setParameter("key", "%" + key + "%");
        }
        pager.setDatas(query.list());
        entityManager.close();
        return pager;
    }

    // 获取单个随手拍
    public CliReadilyTakePost getPost(int id) {
        String sql = "SELECT * FROM \"cliReadilyTakePost\" WHERE \"id\"=:id AND \"status\" IN (0,1,2)";
        SQLQuery query = setCliReadilyTakePostScalar(sql);
        query.setParameter("id", id);
        CliReadilyTakePost cliReadilyTakePost = (CliReadilyTakePost) query
                .uniqueResult();
        entityManager.close();
        return cliReadilyTakePost;
    }

    // 改变随手拍状态
    public int changePostStatus(int id, int status) {
        String sql = "UPDATE \"cliReadilyTakePost\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        entityManager.close();
        return result;
    }

    // 修改随手拍
    public CliReadilyTakePost updateCliReadilyTakePost(CliReadilyTakePost post) {
        String sql = "UPDATE \"cliReadilyTakePost\" SET \"rid\"=:rid,\"uname\"=:uname,\"telephone\"=:telephone,\"region\"=:region,\"project\"=:project,\"title\"=:title,\"content\"=:content,\"status\"=:status,\"uptime\"=:uptime WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);

        query.setParameter("rid", post.getRid());
        query.setParameter("uname", post.getUname());
        query.setParameter("telephone", post.getTelephone());
        query.setParameter("region", post.getRegion());
        query.setParameter("project", post.getProject());
        query.setParameter("title", post.getTitle());
        query.setParameter("content", post.getContent());
        query.setParameter("status", post.getStatus());
        query.setParameter("uptime", post.getUptime());
        query.setParameter("id", post.getId());

        query.executeUpdate();
        entityManager.close();
        return post;
    }

    // 获取随手拍评论
    public List<CliReadilyTakeComment> getPostComments(int rid) {
        String sql = "SELECT * FROM \"cliReadilyTakeComment\" WHERE \"rid\"=:rid AND \"status\" IN(0,1,2) ORDER BY \"id\" ASC";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("rid", rid);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("rid", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("uname", StringType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("region", IntegerType.INSTANCE);
        query.addScalar("project", IntegerType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("update", DateType.INSTANCE);
        query.addScalar("auditdate", DateType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers
                .aliasToBean(CliReadilyTakeComment.class));
        entityManager.close();
        return query.list();
    }

    // 改变随手拍评论状态
    public int changeCommentStatus(int id, int status) {
        String sql = "UPDATE \"cliReadilyTakeComment\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        if (status == 3) {
            String sql1 = "UPDATE \"cliReadilyTakePost\" SET \"cnmu\"=\"cnmu\"-1  WHERE \"id\"=(select \"rid\" from \"cliReadilyTakeComment\"  WHERE \"id\"=:id)";
            SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(
                    SQLQuery.class);
            query1.setParameter("id", id);
            query1.executeUpdate();
        }
        int result = query.executeUpdate();
        entityManager.close();
        return result;
    }

    // 获取举报标签
    public List<Reporttype> getReportPostTagList() {
        String sql = "SELECT \"id\",\"typename\",\"category\",\"describe\",\"status\" FROM \"reporttype\" WHERE \"status\" IN(1, 3) ORDER BY \"id\" ASC";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("category", IntegerType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Reporttype.class));
        entityManager.close();
        return query.list();
    }

    // 增加举报标签
    public Reporttype addReportPostTag(Reporttype tag) {
        String sql = "INSERT INTO \"reporttype\"(\"id\",\"typename\",\"category\",\"describe\",\"status\") "
                + "VALUES(:id, :typename, :category, :describe, :status)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("id", tag.getId());
        query.setParameter("typename", tag.getTypename());
        query.setParameter("category", tag.getCategory());
        query.setParameter("describe", tag.getDescribe());
        query.setParameter("status", tag.getStatus());

        query.executeUpdate();
        entityManager.close();
        return tag;
    }

    // 修改举报标签
    public Reporttype updateReportPostTag(Reporttype tag) {
        String sql = "UPDATE \"reporttype\" SET \"typename\"=:typename,\"describe\"=:describe,\"category\"=:category,\"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("id", tag.getId());
        query.setParameter("typename", tag.getTypename());
        query.setParameter("describe", tag.getDescribe());
        query.setParameter("category", tag.getCategory());
        query.setParameter("status", tag.getStatus());

        query.executeUpdate();
        entityManager.close();
        return tag;
    }

    // 改变举报标签状态
    public int changeReportPostTagStatus(int id, int status) {
        String sql = "UPDATE \"reporttype\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        entityManager.close();
        return result;
    }

    public Reporttype getReportPostTag(int id) {
        String sql = "SELECT * FROM \"reporttype\" WHERE \"id\"=:id AND \"status\" IN(1,3)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("id", id);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("typename", StringType.INSTANCE);
        query.addScalar("category", IntegerType.INSTANCE);
        query.addScalar("describe", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Reporttype.class));
        Object o = query.uniqueResult();
        if (o != null) {
            entityManager.close();
            return (Reporttype) o;
        }
        entityManager.close();
        return null;
    }

    // 获取举报列表
    public List<ReportPost> getPostReportposts(int rid, int category) {
        String sql = "SELECT * FROM \"reportPost\" WHERE \"pid\"=:pid AND \"category\"=:category ORDER BY \"id\" ASC";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("pid", rid);
        query.setParameter("category", category);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("pid", IntegerType.INSTANCE);
        query.addScalar("uid", IntegerType.INSTANCE);
        query.addScalar("type", IntegerType.INSTANCE);
        query.addScalar("content", StringType.INSTANCE);
        query.addScalar("uptime", DateType.INSTANCE);
        query.addScalar("pic", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("category", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(ReportPost.class));
        entityManager.close();
        return query.list();
    }

    // 改变举报状态
    public int changeReportpostStatus(int id, int status) {
        if (status == 9) {
            //获取删除投诉关联的帖子
            String sql = "SELECT \"pid\" FROM \"reportPost\" WHERE \"id\"=:id";
            SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            query.setParameter("id", id);
            //query.setResultTransformer(Transformers.aliasToBean(ReportPost.class));
            List<Object> oList = query.list();
            if (null == oList && oList.isEmpty() && oList.size() != 1) {
                throw new MessageException("操作的举报记录不存在或不唯一", 400);
            }
            int pId = Integer.valueOf(String.valueOf(oList.get(0)));

            String sql1 = "DELETE from  \"reportPost\" WHERE  \"id\"=:id";
            SQLQuery query1 = entityManager.createNativeQuery(sql1).unwrap(SQLQuery.class);
            query1.setParameter("id", id);
            int result = query1.executeUpdate();

            //处理帖子投诉数量
            String sql2 = "update \"cliReadilyTakePost\" set \"reportnum\"=\"reportnum\"-1 where \"id\"=:id";
            SQLQuery query2 = entityManager.createNativeQuery(sql2).unwrap(SQLQuery.class);
            query2.setParameter("id", pId);
            int result2 = query2.executeUpdate();

            entityManager.close();
            return result;
        }
        String sql = "UPDATE \"reportPost\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(
                SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        entityManager.close();
        return result;
    }

}
