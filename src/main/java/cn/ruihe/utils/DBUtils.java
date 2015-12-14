package cn.ruihe.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by xuhui on 15-12-2.
 */
public class DBUtils {

    public static int getCountByHibernate(Object o) {
        return o instanceof BigInteger ? ((BigInteger) o).intValue() : ((BigDecimal) o).intValue();
    }
}
