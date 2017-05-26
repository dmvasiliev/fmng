package servlets;

/**
 * Created by vasiliev on 5/23/2017.
 */

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class Log4jInit implements ServletContextListener {

    // Public constructor is required by servlet spec
    public Log4jInit() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        String homeDir = sce.getServletContext().getRealPath("/");
        File propertiesFile = new File(homeDir, "WEB-INF/log4j.properties");
        PropertyConfigurator.configure(propertiesFile.toString());
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }
}
