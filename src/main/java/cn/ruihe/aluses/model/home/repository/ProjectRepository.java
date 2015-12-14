package cn.ruihe.aluses.model.home.repository;

import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.model.home.vo.SimpleProjectOutput;
import cn.ruihe.aluses.model.home.vo.SysUserOutput;

import cn.ruihe.utils.DBUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

/**
 * @Author DHC
 * @Date 2015-07-18 14:50:01
 */
@Repository
public class ProjectRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<SimpleProjectOutput> getSimpleProjects() {
        String sql = "SELECT \"ID\" AS id, \"ORGNAME\" AS name, \"CONTACTWAY\" AS contactway, \"REMARK\" AS remark FROM \"ORGANIZATIONS\" ORDER BY \"ID\" ASC";

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("name", StringType.INSTANCE);
        query.addScalar("contactway", StringType.INSTANCE);
        query.addScalar("remark", StringType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(SimpleProjectOutput.class));
        entityManager.close();
        return query.list();
    }

    public Pager<SimpleProjectOutput> getSimpleProjectsByPage(int page, int size, int count) {
//    	int count = getProjectCount();
        Pager<SimpleProjectOutput> pager = new Pager<SimpleProjectOutput>(page, size, count).strict();
        String sql = "SELECT \"ID\" AS id, \"ORGNAME\" AS name, \"CONTACTWAY\" AS contactway, \"REMARK\" AS remark FROM \"ORGANIZATIONS\" ORDER BY \"ID\" ASC";
        sql = String.format("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (%s) A WHERE ROWNUM <= %d) WHERE RN >= %d", sql, pager.getEndIndex(), pager.getStartIndex());
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("name", StringType.INSTANCE);
        query.addScalar("contactway", StringType.INSTANCE);
        query.addScalar("remark", StringType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(SimpleProjectOutput.class));
        pager.setDatas(query.list());
        entityManager.close();
        return pager;
    }

    /**
     * @return
     */
    public int getProjectCount() {
        String sql = "SELECT COUNT(\"ID\") FROM \"ORGANIZATIONS\"";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        int count = DBUtils.getCountByHibernate(query.uniqueResult());
        entityManager.close();
        return count;
    }

    public SimpleProjectOutput getSimpleProject(int id) {
        String sql = "SELECT \"ID\" AS id, \"ORGNAME\" AS name, \"CONTACTWAY\" AS contactway, \"SIMPLEPINYIN\" AS simplepinyin,\"FULLPINYIN\" AS fullpinyin, \"REMARK\" AS remark FROM \"ORGANIZATIONS\" WHERE \"ID\"=:id";

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        SimpleProjectOutput simpleProjectOutput = new SimpleProjectOutput();
        query.setParameter("id", id);

        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("name", StringType.INSTANCE);
        query.addScalar("contactway", StringType.INSTANCE);
        query.addScalar("simplepinyin", StringType.INSTANCE);
        query.addScalar("fullpinyin", StringType.INSTANCE);
        query.addScalar("remark", StringType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(SimpleProjectOutput.class));
        simpleProjectOutput = (SimpleProjectOutput) query.uniqueResult();
        entityManager.close();
        return simpleProjectOutput;
    }

    public SimpleProjectOutput addProject(SimpleProjectOutput project) {
        String sql = "INSERT INTO \"ORGANIZATIONS\"(\"ID\",\"ORGNAME\",\"CONTACTWAY\",\"SIMPLEPINYIN\",\"FULLPINYIN\",\"REMARK\",\"ORGTYPE\",\"ORGINTERNALCODE\",\"CREATEUSER\",\"CREATEDATE\") " +
                "VALUES(:id, :name, :contactway, :simplepinyin, :fullpinyin, :remark, 1,'.', 'admin',:date)";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("name", project.getName());
        query.setParameter("contactway", project.getContactway());
        query.setParameter("simplepinyin", project.getSimplepinyin());
        query.setParameter("fullpinyin", project.getFullpinyin());
        query.setParameter("remark", project.getRemark());
        query.setParameter("date", new Date());
        query.setParameter("id", project.getId());
        query.executeUpdate();
        entityManager.close();
        return project;
    }

    public SimpleProjectOutput updatePorject(SimpleProjectOutput project) {
        String sql = "UPDATE \"ORGANIZATIONS\" SET \"ORGNAME\"=:name, \"CONTACTWAY\"=:contactway,\"SIMPLEPINYIN\"=:simplepinyin,\"FULLPINYIN\"=:fullpinyin,\"REMARK\"=:remark WHERE \"ID\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("name", project.getName());
        query.setParameter("contactway", project.getContactway());
        query.setParameter("simplepinyin", project.getSimplepinyin());
        query.setParameter("fullpinyin", project.getFullpinyin());
        query.setParameter("remark", project.getRemark());
        query.setParameter("id", project.getId());
        query.executeUpdate();
        entityManager.close();
        return project;
    }

    /*
    根据项目名称得到项目id
     */
    public int getProjectIdByName(String projectName) {
        String sql = "SELECT \"ID\" AS id  FROM \"ORGANIZATIONS\" WHERE \"ORGNAME\"=:name";

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("name", projectName);
        query.addScalar("id", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(SimpleProjectOutput.class));
        entityManager.close();
        List<SimpleProjectOutput> list = query.list();
        if (list != null && list.size() > 0) {
            SimpleProjectOutput spo = list.get(0);
            if (spo != null) {
                return spo.getId();
            }
        }
        return -1;

    }

    public int deletePorject(int id) {
        String sql = "DELETE from  \"ORGANIZATIONS\"  WHERE \"ID\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("id", id);
        entityManager.close();
        return query.executeUpdate();
    }
}
