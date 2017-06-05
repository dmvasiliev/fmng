package utils;

import dao.UserDAO;
import org.apache.log4j.Logger;
import domain.User;
import domain.UserGroups;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by vasiliev on 5/23/2017.
 */

public class ProjectUtils {
    private final static Logger logger = Logger.getLogger(ProjectUtils.class);

    private ProjectUtils() {
    }

    public static String getDefaultPath(HttpServlet httpServlet, HttpServletRequest request) throws IOException {
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
        return pathFromConfig + getNextPartOfRootPath(request);
    }

    public static String getNextPartOfRootPath(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String loginName = (String) session.getAttribute("user");
        UserDAO userDAO = new UserDAO();
        User userInfo = userDAO.getUserByLogin(loginName);
        String folderName = getFolderName(userInfo);
        return File.separator + "workarea" + (folderName.isEmpty() ? "" : File.separator + folderName);
    }

    public static String getFolderName(User userFromDB) {
        String customerId = userFromDB.getCustomerId();
        String supplierId = userFromDB.getSupplierId();
        if (customerId == null && supplierId == null) {
            return "";
        } else {
            return customerId == null ? UserGroups.SUPPLIER.getFolderName() + File.separator + supplierId :
                    UserGroups.CUSTOMER.getFolderName() + File.separator + customerId;
        }
    }
}
