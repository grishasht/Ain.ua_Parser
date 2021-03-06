package model.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPool {

    private static final String DRIVER_CLASS_NAME;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static volatile DataSource dataSource;

    static {
        ResourceBundle resBund = ResourceBundle.getBundle("data_base");
        DRIVER_CLASS_NAME = resBund.getString("driver.class.name");
        URL = resBund.getString("db.url");
        USERNAME = resBund.getString("db.username");
        PASSWORD = resBund.getString("db.password");
    }

    private ConnectionPool() {
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPool.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setDriverClassName(DRIVER_CLASS_NAME);
                    ds.setUrl(URL);
                    ds.setUsername(USERNAME);
                    ds.setPassword(PASSWORD);
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxTotal(25);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }

}
