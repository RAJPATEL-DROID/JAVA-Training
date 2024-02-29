
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LoggerExample {
    private static Logger logger = Logger.getLogger("LoggerExample");
    
    public static void init()
    {
        FileHandler fh;
        try
        {
            // fh = new FileHandler("/home/raj/Work/JavaExamples/LogFile.log", true);

            fh = new FileHandler(System.getProperty("user.dir") + System.getProperty("file.separator") + "MyLogFile.log", true);
            
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info("Logger Initialsed");
        }
        catch (Exception e)
        {
            logger.log(Level.WARNING, "Exception ::", e);
        }
    }
    
    
    public static void main(String[] args) {
            init();
            try {
                int a = 10/0;
            } catch (Exception e) {
                logger.log(Level.WARNING, "Exception ::", e);
            }

            logger.info("End of Logger");
        }
}
