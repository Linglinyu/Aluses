package cn.ruihe.aluses.model.readilytake.repository;

import oracle.net.aso.i;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.ruihe.utils.DProperties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @Author DHC
 * @Date 2015-07-15 13:19:23
 */
@Repository
public class CommonRepository {

    @PersistenceContext
    EntityManager entityManager;

    public int getNewId(String table) {
        return getNewId(table, "id");
    }

    public int getNewId(String table, String keyColumn) {
    	synchronized(DProperties.lock){
    		if(keyColumn == null || Objects.equals(keyColumn, "")) {
                keyColumn = "id";
            }
            String sql = String.format("SELECT MAX(\"%s\")+1 AS id FROM \"%s\"", keyColumn, table);
            SQLQuery q = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);
            Object o =  q.uniqueResult();
            if (o instanceof BigInteger){
                return ((BigInteger) o).intValue();
            }
            else if(o instanceof BigDecimal) {
                return ((BigDecimal) o).intValue();
            } else if(o instanceof Integer){
                return (int) o;
            } else {
            	if(o==null)
            	{
            		return 1;
            	}
                return Integer.parseInt(o.toString());
            }
    	}
        
    }
}
