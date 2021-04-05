package com.portal.app.util;

import org.slf4j.Logger;

public class LoggerUtil {

    public static void debug(Logger logger, String message, Object additionalInfo) {
        if (logger.isDebugEnabled()) {
            if (additionalInfo != null) {
                logger.debug(message, additionalInfo);
            } else {
                logger.debug(message);
            }
        }
    }
}
