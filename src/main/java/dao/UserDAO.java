package dao;

import domain.User;
import org.apache.log4j.Logger;
import services.DBConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by vasiliev on 6/5/2017.
 */
public class UserDAO {
    private final static Logger logger = Logger.getLogger(UserDAO.class);

    public User getUserByLogin(String loginName) {
        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;
        User userFromDB = new User();
        try {
            connection = DBConnectionManager.getDataSource().getConnection();

            String query = "SELECT id, CustomerId, SupplierId FROM Users WHERE loginName='" + loginName + "'";
            st = connection.createStatement();

            rs = st.executeQuery(query);
            if (rs != null && rs.next()) {
                userFromDB.setId(rs.getString("id"));
                userFromDB.setCustomerId(rs.getString("CustomerId"));
                userFromDB.setSupplierId(rs.getString("SupplierId"));
            } else {
                logger.debug("User not found!!!!!!");
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }

        return userFromDB;
    }
}