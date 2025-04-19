package repository;

import entity.Category;
import entity.User;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    // Create a new user
    public void createCategory(Category category) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO category (name, description, createdUserId, modifiedUserId) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getCreatedUserId());
            statement.setInt(4, category.getCreatedUserId());
            statement.executeUpdate();
        }
    }

    // Read all categories
    public List<Category> listCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM category";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                categories.add(new Category(id, name, description));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }
}
