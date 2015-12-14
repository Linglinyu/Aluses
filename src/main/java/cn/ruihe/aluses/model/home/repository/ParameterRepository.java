package cn.ruihe.aluses.model.home.repository;

import cn.ruihe.aluses.entity.Parameter;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @Author DHC
 * @Date 2015-07-21 21:24
 */
@Repository
public class ParameterRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<Parameter> findParmeters() {
        String sql = "SELECT * FROM \"parameter\"";

        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.addScalar("id", IntegerType.INSTANCE);
        query.addScalar("parname", StringType.INSTANCE);
        query.addScalar("parvalue", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(Parameter.class));
        entityManager.close();
        return query.list();
    }

    public int changeParameterValue(int id, int value) {
        String sql = "UPDATE \"parameter\" SET \"parvalue\"=:value WHERE \"id\"=:id";
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setParameter("value", value);
        query.setParameter("id", id);
        entityManager.close();
        return query.executeUpdate();
    }
}
