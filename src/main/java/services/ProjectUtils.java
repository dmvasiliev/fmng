package services;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by vasiliev on 5/23/2017.
 */
public class ProjectUtils {
    private ProjectUtils() {
    }

    public static String getDefaultPath(HttpServlet httpServlet) throws IOException {
        String pathFromConfig;
        InputStream propertiesAsStream = httpServlet.getClass().getClassLoader().getResourceAsStream("config.properties");
        if (propertiesAsStream != null) {
            Properties properties = new Properties();
            properties.load(propertiesAsStream);
            pathFromConfig = (String) properties.get("defaultPath");
        } else {
            pathFromConfig = httpServlet.getServletContext().getRealPath("/");
            if (pathFromConfig.endsWith(File.separator) && !pathFromConfig.endsWith(":" + File.separator)) {
                pathFromConfig = pathFromConfig.substring(0, pathFromConfig.length() - 1);
            }
        }
        return pathFromConfig;
    }
}
