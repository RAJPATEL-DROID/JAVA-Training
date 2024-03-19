package com.server.utils;

import java.io.IOException;
import java.util.logging.*;

public class LoggingUtils {
    private static final Logger logger = Logger.getLogger(LoggingUtils.class.getName());

    static {
        try
        {
            FileHandler fileHandler = new FileHandler("Server.log", false);

            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);

            Logger rootLogger = LogManager.getLogManager().getLogger("");

            Handler[] handlers = rootLogger.getHandlers();

            for (Handler handler : handlers)
            {
                    rootLogger.removeHandler(handler);
            }
        }
        catch (IOException exception)
        {
            System.out.println("Error in initiating logger" + exception.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}