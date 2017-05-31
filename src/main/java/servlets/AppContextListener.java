package servlets;

/**
 * Created by vasiliev on 5/31/2017.
 */

import services.DBConnectionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;

public class AppContextListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public AppContextListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        String dbURL = ctx.getInitParameter("dbURL");
        String user = ctx.getInitParameter("dbUser");
        String pwd = ctx.getInitParameter("dbPassword");

        try {
            DBConnectionManager connectionManager = new DBConnectionManager(dbURL, user, pwd);
            ctx.setAttribute("DBConnection", connectionManager.getConnection());
            System.out.println("DB Connection initialized successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        Connection con = (Connection) sce.getServletContext().getAttribute("DBConnection");
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
