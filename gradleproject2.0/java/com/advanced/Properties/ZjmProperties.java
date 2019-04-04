package com.advanced.Properties;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author hongsw,2015-11-13
 */
public class ZjmProperties {


    private static Properties properties = new Properties();

    static {
        // upload-temp-folder
        try {
            InputStream inputStream=ZjmProperties.class.getResourceAsStream("application.properties");//这样不行,inputStream会读取不到
            inputStream=ZjmProperties.class.getClassLoader().getResourceAsStream("application.properties");

            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }


    public static String getKeyValue(String key) {
        return properties.getProperty(key);
    }
}