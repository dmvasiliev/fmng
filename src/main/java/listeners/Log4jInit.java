package listeners;

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


        //ToDo Delete
        File config = new File(homeDir, "WEB-INF/classes/config.properties");
        if (!config.exists()) {
            File workAreaDir = new File(homeDir, "workarea");
            if (!workAreaDir.exists()) {
                boolean workArea = workAreaDir.mkdir();
                if (workArea) {
                    File customers = new File(workAreaDir, "customers");
                    customers.mkdir();
                    File suppliers = new File(workAreaDir, "suppliers");
                    suppliers.mkdir();
                    new File(workAreaDir, "global").mkdir();
                    for (int i = 0; i < 6; i++) {
                        File customerId = new File(customers, i + 1 + "");
                        boolean mkdir = customerId.mkdir();
                        if (mkdir) {
                            new File(customerId, "global").mkdir();
                            new File(customerId, "temp").mkdir();
                        }
                        File supplierId = new File(suppliers, i + 1 + "");
                        boolean mkdir1 = supplierId.mkdir();
                        if (mkdir1) {
                            new File(supplierId, "global").mkdir();
                            new File(supplierId, "temp").mkdir();
                        }
                    }
                }
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }
}
