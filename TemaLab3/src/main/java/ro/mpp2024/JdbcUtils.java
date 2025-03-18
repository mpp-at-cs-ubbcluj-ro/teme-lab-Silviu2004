package ro.mpp2024;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private final Properties jdbcProps;

    public JdbcUtils(Properties props) {
        jdbcProps = props;
    }

    private Connection instance = null;

    private Connection getNewConnection() {
        String url = jdbcProps.getProperty("jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String password = jdbcProps.getProperty("jdbc.password");

        try {
            if (url == null || user == null || password == null) {
                throw new IllegalArgumentException("Database connection properties are missing! Please provide 'jdbc.url', 'jdbc.user' and 'jdbc.password'");
            }
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.err.println("Error getting database connection: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public Connection getConnection() {
        try {
            if (instance == null || instance.isClosed()) {
                instance = getNewConnection();
            }
        } catch (SQLException e) {
            System.err.println("Error validating database connection: " + e.getMessage());
        }
        return instance;
    }
}