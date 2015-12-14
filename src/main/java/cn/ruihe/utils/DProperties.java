package cn.ruihe.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author dhc
 *         2015-02-12
 */
public class DProperties {
	
	//全局money锁
	public static Object lock = new Object();
		
		
    private final static Logger LOG = LogManager.getLogger(DProperties.class);
    private static final String EXT = ".properties";
    private static final String MAIN_FILE = "env";
    private static final String KEY_ENV = "env";
    private static final String KEY_PUBLIC = "common";

    private static DProperties pt;

    private ClassLoader clr = null;
    private Map<String, String> vks = new HashMap<String, String>();

    private DProperties() {
        clr = DProperties.class.getClassLoader();
        loadMain();
    }

    private Map<String, String> load(String file) {
        if (!file.endsWith(EXT)) {
            file += EXT;
        }
        Map<String, String> ps = new HashMap<String, String>();
        try {
            Properties pts = new Properties();
            pts.load(clr.getResourceAsStream("/" + file));

            for (Object k : pts.keySet().toArray()) {
                ps.put(k.toString(), pts.get(k).toString());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return ps;
    }

    private Map<String, String> loadAll(String files) {
        Map<String, String> ps = new HashMap<String, String>();
        String[] flist = files.split(",");
        for (String file : flist) {
            ps.putAll(load(file));
        }
        return ps;
    }

    private void loadMain() {
        vks.putAll(load(MAIN_FILE + EXT));

        if (vks.get(KEY_PUBLIC) != null) {
            vks.putAll(loadAll(vks.get(KEY_PUBLIC)));
        }

        if (vks.get(KEY_ENV) != null) {
            vks.putAll(load("system_" + vks.get(KEY_ENV)));
            if (vks.get(vks.get(KEY_ENV)) != null) {
                vks.putAll(loadAll(vks.get(vks.get(KEY_ENV))));
            }
        }
    }

    /**
     * 活取当前环境名称
     *
     * @return 当前环境名称
     */
    public static String getEnv() {
        return get(KEY_ENV);
    }

    /**
     * 根据环境配置获取指定键的值
     *
     * @param key 键名
     * @return 值
     */
    public static String get(String key) {
        if (pt == null) {
            pt = new DProperties();
        }
        if (pt.vks != null) {
            return pt.vks.get(key);
        }
        return null;
    }

    /**
     * 根据文件名查找
     *
     * @param route 路由，如conf.properties下的db.name则为：conf.db.name
     * @return 配置的值
     */
    public static String get4Route(String route) {
        if (!route.contains(".")) {
            return get(route);
        }
        String file = route.substring(0, route.indexOf("."));
        String key = route.replaceFirst(String.format("%s.", file), "");
        if (file.length() > 0 && key != null && key.length() > 0) {
            Map<String, String> map = new DProperties().load(file);
            return map.get(key);
        }
        return null;
    }
}