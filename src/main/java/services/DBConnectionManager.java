package services;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Created by vasiliev on 5/31/2017.
 */
public class DBConnectionManager {
    private final static Logger logger = Logger.getLogger(DBConnectionManager.class);

    private static DataSource ds = null;

    public static DataSource getDataSource() {

        if (ds != null) {
            return ds;
        } else {
            try {
                Context initCtx = new InitialContext();
                ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/fmdb");
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
            return ds;
        }

    }
}
