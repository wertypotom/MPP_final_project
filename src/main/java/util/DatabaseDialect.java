package util;

public class DatabaseDialect {

    private static final String dbType;

    static {
        dbType = DatabaseUtil.getDbType();
    }

    public static String getProcessedTableName(String identifier) {
        return switch (dbType) {
            case "mysql" -> identifier;
            case "postgresql" -> "\"" + identifier + "\"";
            default -> identifier;
        };
    }
}