package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static final Properties props = new Properties();

    static {
        try {
            InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("db.properties file not found in classpath");
            }

            props.load(input);

            String dbType = props.getProperty("db.type").toLowerCase();
            String driverClass = props.getProperty(dbType + ".driver");

            Class.forName(driverClass);
            System.out.println("DatabaseUtil loaded config for: " + dbType);
        } catch (Exception e) {
            throw new RuntimeException("Error loading DB config", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String dbType = props.getProperty("db.type").toLowerCase();
        return DriverManager.getConnection(
                props.getProperty(dbType + ".url"),
                props.getProperty(dbType + ".user"),
                props.getProperty(dbType + ".password")
        );
    }
}
