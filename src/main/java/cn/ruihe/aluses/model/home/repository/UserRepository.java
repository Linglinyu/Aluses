package cn.ruihe.aluses.model.home.repository;

import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.entity.Community;
import cn.ruihe.aluses.entity.SysUser;
import cn.ruihe.aluses.model.home.vo.SysUserOutput;
import cn.ruihe.utils.DBUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author DHC
 * @Date 2015-07-18 14:50:01
 */
@Repository
public class UserRepository {
    @PersistenceContext
    EntityManager entityManager;
    private int countByHibernate;

    private SQLQuery setSysUserScalar(String sql) {
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("username", StringType.INSTANCE);
        query.addScalar("region", IntegerType.INSTANCE);
        query.addScalar("project", IntegerType.INSTANCE);
        query.addScalar("truename", StringType.INSTANCE);
        query.addScalar("position", StringType.INSTANCE);
        query.addScalar("role", IntegerType.INSTANCE);
        query.addScalar("telephone", StringType.INSTANCE);
        query.addScalar("status", IntegerType.INSTANCE);
        query.addScalar("hasdelpost", IntegerType.INSTANCE);

        return query;
    }

    /**
     * 登陆
     *
     * @param username
     * @param userpwd
     * @return
     */
    public SysUser getByLogin(String username, String userpwd) {
        String sql = "SELECT \"id\",\"username\",\"region\",\"project\",\"truename\",\"position\",\"role\",\"telephone\",\"status\",\"hasdelpost\" FROM \"sysUser\" WHERE \"username\"=:username AND \"userpwd\"=:userpwd AND \"status\"=1";

        SQLQuery query = setSysUserScalar(sql);

        query.setParameter("username", username);
        query.setParameter("userpwd", userpwd);

        query.setResultTransformer(Transformers.aliasToBean(SysUser.class));
        SysUser sysUser = (SysUser) query.uniqueResult();
        entityManager.close();
        return sysUser;
    }

    /**
     * 获取单个信息
     *
     * @param id
     * @return
     */
    public SysUser getById(int id) {
        String sql = "SELECT \"id\",\"username\",\"region\",\"project\",\"truename\",\"position\",\"role\",\"telephone\",\"status\",\"hasdelpost\" FROM \"sysUser\" WHERE \"id\"=:id AND \"status\"<>3";
        SQLQuery query = setSysUserScalar(sql);

        query.setParameter("id", id);

        query.setResultTransformer(Transformers.aliasToBean(SysUser.class));
        SysUser sysUser = (SysUser) query.uniqueResult();
        entityManager.close();
        return sysUser;
    }

    /**
     * 获取密码
     *
     * @param id
     * @return
     */
    public String getPasswordById(int id) {
        String sql = "SELECT \"userpwd\" FROM \"sysUser\" WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", id);
        Object result = query.uniqueResult();
        entityManager.close();
        return result == null ? null : result.toString();
    }

    /**
     * 修改密码
     *
     * @param id
     * @param password
     * @return
     */
    public int changePassword(int id, String password) {
        String sql = "UPDATE \"sysUser\" SET \"userpwd\"=:password WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("password", password);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        entityManager.close();
        return result;
    }

    /**
     * 改变管理员状态
     *
     * @param id
     * @param status
     * @return
     */
    public int changeStatus(int id, int status) {
        String sql = "UPDATE \"sysUser\" SET \"status\"=:status WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("status", status);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        entityManager.close();
        return result;
    }

    /**
     * 添加管理员
     *
     * @param user
     * @return
     */
    public SysUser addSysUser(SysUser user) {

        String sql = "INSERT INTO \"sysUser\"(\"id\",\"username\",\"userpwd\",\"region\",\"project\",\"truename\",\"position\",\"role\",\"telephone\",\"status\",\"hasdelpost\")" +
                "VALUES(:id, :username, :userpwd, :region, :project, :truename, :position, :role, :telephone, :status, :hasdelpost)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", user.getId());
        query.setParameter("username", user.getUsername());
        query.setParameter("userpwd", user.getUserpwd());
        query.setParameter("region", user.getRegion());
        query.setParameter("project", user.getProject());
        query.setParameter("truename", user.getTruename());
        query.setParameter("position", user.getProject());
        query.setParameter("role", user.getRole());
        query.setParameter("telephone", user.getTelephone());
        query.setParameter("status", user.getStatus());
        query.setParameter("hasdelpost", user.getHasdelpost());
        query.executeUpdate();
        entityManager.close();
        return user;
    }

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    public int deleteSysUser(int id) {
        String sql = "delete  FROM \"sysUser\" WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        entityManager.close();
        return result;
    }

    /**
     * 更新管理员资料
     *
     * @param user
     * @return
     */
    public SysUser updateSysUser(SysUser user) {
        String sql = "UPDATE \"sysUser\" SET " +
                "\"username\"=:username,\"userpwd\"=:userpwd,\"region\"=:region,\"project\"=:project,\"truename\"=:truename," +
                "\"position\"=:position,\"role\"=:role,\"telephone\"=:telephone,\"status\"=:status,\"hasdelpost\"=:hasdelpost " +
                "WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", user.getId());
        query.setParameter("username", user.getUsername());
        query.setParameter("userpwd", user.getUserpwd());
        query.setParameter("region", user.getRegion());
        query.setParameter("project", user.getProject());
        query.setParameter("truename", user.getTruename());
        query.setParameter("position", user.getPosition());
        query.setParameter("role", user.getRole());
        query.setParameter("telephone", user.getTelephone());
        query.setParameter("status", user.getStatus());
        query.setParameter("hasdelpost", user.getHasdelpost());
        query.executeUpdate();
        entityManager.close();
        return user;
    }

    /**
     * 检查账号是否存在
     *
     * @param username
     * @return
     */
    public boolean hasUser(String username, int id) {
        String sql = "SELECT COUNT(\"id\") AS isHas FROM \"sysUser\" WHERE \"username\"=:username AND \"id\"<>:id";
//    	String sql = "SELECT COUNT(\"id\") AS isHas FROM \"sysUser\" WHERE \"username\"=:username AND \"id\"<>:id and \"status\"<>3";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("username", username);
        query.setParameter("id", id);
        Object o = query.uniqueResult();
        int c = 0;
        if (o instanceof Integer) {
            Integer result = (Integer) o;
            c = result.intValue();
        } else {
            BigDecimal result = (BigDecimal) o;
            c = result.intValue();
        }

        entityManager.close();
        return c > 0;
    }

    /**
     * 获取管理员列表
     *
     * @return
     */
    public Pager<SysUserOutput> getUsers(int page, int size) {
        int count = getUsersCount();
        Pager<SysUserOutput> pager = new Pager<SysUserOutput>(page, size, count).strict();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	u.\"id\", ");
        sql.append("	u.\"username\", ");
        sql.append("	u.\"region\", ");
        sql.append("	u.\"project\", ");
        sql.append("	p.\"ORGNAME\" AS projectname, ");
        sql.append("	u.\"truename\", ");
        sql.append("	u.\"position\", ");
        sql.append("	u.\"role\", ");
        sql.append("	u.\"telephone\", ");
        sql.append("	u.\"status\", ");
        sql.append("	u.\"hasdelpost\" ");
        sql.append("FROM \"sysUser\" u ");
        sql.append("LEFT JOIN \"ORGANIZATIONS\" p ON u.\"project\" = p.\"ID\" ");
        sql.append("WHERE u.\"status\"<>3 ");
        sql.append("ORDER BY \"id\" ASC");
        String sql1 = sql.toString();
        sql1 = String.format("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (%s) A WHERE ROWNUM <= %d) WHERE RN >= %d", sql1, pager.getEndIndex(), pager.getStartIndex());
        SQLQuery query = setSysUserScalar(sql1.toString());

        query.addScalar("projectname", StringType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(SysUserOutput.class));
        pager.setDatas(query.list());
        entityManager.close();
        return pager;
    }

    /**
     * @return
     */
    private int getUsersCount() {
        String sql = "SELECT COUNT(\"id\") FROM \"sysUser\" WHERE \"status\"<>3";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        int count = countByHibernate;
        entityManager.close();
        return count;
    }

    /**
     * 获取单个信息
     *
     * @param id
     * @return
     */
    public SysUser getByName(String username) {
        String sql = "SELECT \"id\",\"username\",\"region\",\"project\",\"truename\",\"position\",\"role\",\"telephone\",\"status\",\"hasdelpost\" FROM \"sysUser\" WHERE \"username\"=:username AND \"status\"<>3";
        SQLQuery query = setSysUserScalar(sql);

        query.setParameter("username", username);

        query.setResultTransformer(Transformers.aliasToBean(SysUser.class));
        SysUser sysUser = (SysUser) query.uniqueResult();
        entityManager.close();
        return sysUser;
    }


}
