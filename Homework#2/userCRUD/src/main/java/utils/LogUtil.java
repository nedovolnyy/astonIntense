/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author AKrot
 */
public class LogUtil {

    private static final Logger logger;

    static {
        logger = LogManager.getLogger("AppLogger");
    }

    private LogUtil() {
    }

    public static void info(String message, Object... args) {
        logger.info(message, args);
    }

    public static void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    public static void error(String message, Object... args) {
        logger.error(message, args);
    }

    public static void error(Exception e) {
        logger.error(e);
    }

    public static void debug(String message, Object... args) {
        logger.debug(message, args);
    }
}
