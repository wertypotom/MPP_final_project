package repository;

import entity.Expense;
import util.DatabaseUtil;
import util.DatabaseDialect;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    private final String expenseTable = DatabaseDialect.getProcessedTableName("Expense");
    private final String categoryTable = DatabaseDialect.getProcessedTableName("Category");


    public Expense createExpense(Expense expense) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO " + expenseTable + " (name, description, amount, categoryId, userId) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, expense.getName());
            statement.setString(2, expense.getDescription());
            statement.setBigDecimal(3, expense.getAmount());
            statement.setInt(4, expense.getCategoryId());
            statement.setInt(5, expense.getUserId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                expense.setExpenseId(generatedId);
            }
            return expense;
        }
    }

    // Read all expenses of user
    public List<Expense> listExpenses(int userId) throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM " + expenseTable + " WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                BigDecimal amount = resultSet.getBigDecimal("amount");
                int categoryId = resultSet.getInt("categoryId");

                expenses.add(new Expense(id, name, description, amount, categoryId));
            }
        }
        return expenses;
    }


    public String getCategoryName(Integer catId) throws SQLException {
        String categoryName = "";

        String query = "SELECT name FROM " + categoryTable + " WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, catId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                categoryName = resultSet.getString("name");
            }
        }

        return categoryName;
    }

    public void deleteExpenseById(int expenseId) throws SQLException {
        String query = "DELETE FROM " + expenseTable + " WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, expenseId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting expense failed, no rows affected. Expense ID: " + expenseId);
            }

        } catch (SQLException e) {
            throw new SQLException("Error deleting expense with ID " + expenseId + ": " + e.getMessage(), e);
        }
    }
}
