package repository;

import entity.Category;
import entity.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {


    private static final String URL = "jdbc:mysql://localhost:3306/expensedb";
    private static final String USER = "root";
    private static final String PASSWORD = "2bfr##@h0m"; //

    // Create a new user
    public void createCategory(Expense expense) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO expense (name, description, amount,createdDateTimeStamp,categoryId,userId) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, expense.getName());
            statement.setString(2, expense.getDescription());
            statement.setBigDecimal(3, expense.getAmount());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(expense.getCreatedDateTimeStamp()));
            statement.setInt(5, expense.getCategoryId());
            statement.setInt(6, expense.getUserId());

            statement.executeUpdate();
        }
    }

    // Read all categories
    public List<Category> listCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM category";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                categories.add(new Category(id, name, description));
            }
        }
        return categories;
    }
}
