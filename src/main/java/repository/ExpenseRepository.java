package repository;

import entity.Expense;
import util.DatabaseUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    public void createExpense(Expense expense) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO expense (name, description, amount, categoryId, userId) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, expense.getName());
            statement.setString(2, expense.getDescription());
            statement.setBigDecimal(3, expense.getAmount());
            //statement.setTimestamp(4, java.sql.Timestamp.valueOf(expense.getCreatedDateTimeStamp()));
            statement.setInt(4, expense.getCategoryId());
            statement.setInt(5,expense.getUserId());
            statement.executeUpdate();
        }
    }

    // Read all expenses
    public List<Expense> listExpenses() throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM expense";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

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

        String query = "SELECT name FROM category WHERE id = ?";

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


}
