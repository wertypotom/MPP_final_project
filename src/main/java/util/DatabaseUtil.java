package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) throw new RuntimeException("db.properties file not found");
            props.load(input);

            String dbType = props.getProperty("db.type").toLowerCase();
            String driverClass = props.getProperty(dbType + ".driver");

            Class.forName(driverClass);
        } catch (Exception e) {
            throw new RuntimeException("Error loading DB config", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String dbType = props.getProperty("db.type").toLowerCase();
        String url = props.getProperty(dbType + ".url");
        String user = props.getProperty(dbType + ".user");
        String pass = props.getProperty(dbType + ".password");
        return DriverManager.getConnection(url, user, pass);
    }
}
