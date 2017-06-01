package services;

import org.apache.log4j.Logger;
import servlets.Directory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by vasiliev on 5/23/2017.
 */

public class ProjectUtils {
    private final static Logger logger = Logger.getLogger(Directory.class);

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
        return pathFromConfig + getNextPartOfRootPath(httpServlet, request);
    }

    public static User getUserInfo(HttpServlet httpServlet, String loginName) {
        User userFromDB = new User();

        Connection con = (Connection) httpServlet.getServletContext().getAttribute("DBConnection");
        try {
            PreparedStatement ps = con.prepareStatement("SELECT id, CustomerId, SupplierId FROM Users WHERE loginName=?");
            ps.setString(1, loginName);

            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                userFromDB.setId(rs.getString("id"));
                userFromDB.setCustomerId(rs.getString("CustomerId"));
                userFromDB.setSupplierId(rs.getString("SupplierId"));
            } else {
                logger.debug("User not found!!!!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userFromDB;
    }

    public static String getNextPartOfRootPath(HttpServlet httpServlet, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String loginName = (String) session.getAttribute("user");

        User userInfo = getUserInfo(httpServlet, loginName);
        String folderName = getFolderName(userInfo);
        return File.separator + "workarea" + (folderName.isEmpty() ? "" : File.separator + folderName + File.separator + userInfo.getId());
    }

    public static String getFolderName(User userFromDB) {
        String customerId = userFromDB.getCustomerId();
        String supplierId = userFromDB.getSupplierId();
        if (customerId == null && supplierId == null) {
            return "";
        } else {
            return customerId == null ? UserGroups.SUPPLIER.getFolderName() : UserGroups.CUSTOMER.getFolderName();
        }
    }
}
