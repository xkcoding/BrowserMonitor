package ncm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configure {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configure.class);
    private static Properties config = null;
    private static final String DEFAULT_CONFIG = "/account.properties.properties";

    /**
     * 系统环境参数
     */
    public static String systemEnv;

    static {
        loadConfig();       //加载系统配置

        systemEnv = config.getProperty("system_env");
        if (systemEnv == null) {
            systemEnv = "unknown";
        }
        LOGGER.info("System environment is:" + systemEnv);
    }


    /**
     * 读取默认配置
     */
    private static void loadConfig() {
        config = new Properties();

        try {
            InputStream input = Configure.class.getResourceAsStream(DEFAULT_CONFIG);
            config.load(input);
        } catch (IOException e) {
            LOGGER.error("[loadConfig] load " + DEFAULT_CONFIG + " error.", e);
        } catch (Exception e) {
            LOGGER.error("[loadConfig] unknown error.", e);
        }
        LOGGER.info("[loadConfig] load " + DEFAULT_CONFIG + " successfully!!!");
    }


    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * {@code null} if the property is not found.
     *
     * @param key the property key.
     * @return the value in this property list with the specified key value.
     */
    public static String get(String key) {
        return config.getProperty(key);
    }

    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns the
     * default value argument if the property is not found.
     *
     * @param key          the property key.
     * @param defaultValue a default value.
     * @return the value in this property list with the specified key value.
     */
    public static String get(String key, String defaultValue) {
        String val = get(key);
        return (val == null) ? defaultValue : val;
    }
}
