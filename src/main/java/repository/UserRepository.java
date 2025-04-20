package repository;

import entity.Category;
import entity.user.User;
import factory.UserFactory;
import util.DatabaseUtil;
import util.DatabaseDialect;

import java.math.BigDecimal;
import java.sql.*;

public class UserRepository {
    private final String table = DatabaseDialect.getProcessedTableName("User");

    public User getUserByEmailAndPassword(String email, String password) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM " + table + " WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            boolean hasUser = resultSet.next();

            if (!hasUser) {
                throw new SQLException("User does not exist.");
            }

            String storedPassword = resultSet.getString("password");
            if (!storedPassword.equals(password)) {
                throw new SQLException("Incorrect password.");
            }

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String userType = resultSet.getString("userType");
            BigDecimal budgetLimit = resultSet.getBigDecimal("budgetLimit");

            User user = UserFactory.createUser(userType, id, name, email);
            user.setBudgetLimit(budgetLimit);

            return user;
        }
    }
}
