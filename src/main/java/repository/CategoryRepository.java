package repository;

import entity.Category;
import util.DatabaseUtil;
import util.DatabaseDialect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private final String table = DatabaseDialect.getProcessedTableName("Category");

    public void createCategory(Category category) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO " + table + " (name, description, createdUserId, modifiedUserId) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getCreatedUserId());
            statement.setInt(4, category.getCreatedUserId());

            System.out.println("Category statement: " + statement.toString());

            statement.executeUpdate();

            System.out.println("Category created successfully");
        }
    }

    public List<Category> listCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM " + table;
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

    public void updateCategory(Category category) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "UPDATE " + table + " SET name = ?, description = ?, modifiedUserId = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getModifiedUserId());
            statement.setInt(4, category.getCategoryId());

            System.out.println("Category statement: " + statement.toString());

            statement.executeUpdate();

            System.out.println("Category updated successfully");
        }
    }
    public void deleteCategory(int id) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "DELETE FROM " + table + " WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
//            statement.setInt(3, category.getCreatedUserId());
//            statement.setInt(4, category.getCreatedUserId());

            System.out.println("Category statement: " + statement.toString());

            statement.executeUpdate();

            System.out.println("Category updated successfully");
        }
    }
}
