package com.wesley.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertyUtil {
	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	private static Properties props;

	static {
		loadProps();
	}

	synchronized static private void loadProps(){
        logger.info("start loading properties.......");
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("ivideo.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("property file not found");
        } catch (IOException e) {
            logger.error("IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("exception occured when closing property file");
            }
        }
        logger.info("finish loading properties......");
        logger.info("contents of properties：" + props);
    }

	public static String getProperty(String key) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key, defaultValue);
	}
}