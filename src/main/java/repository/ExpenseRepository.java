package repository;

import entity.Category;
import entity.Expense;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {


    private static final String URL = "jdbc:mysql://localhost:3306/expensedb";
    private static final String USER = "root";
    private static final String PASSWORD = "2bfr##@h0m"; //


    public void createExpense(Expense expense) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO expense (name, description, amount,categoryId) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, expense.getName());
            statement.setString(2, expense.getDescription());
            statement.setBigDecimal(3, expense.getAmount());
            //statement.setTimestamp(4, java.sql.Timestamp.valueOf(expense.getCreatedDateTimeStamp()));
            statement.setInt(4, expense.getCategoryId());

            statement.executeUpdate();
        }
    }

    // Read all expenses
    public List<Expense> listExpenses() throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM expense";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                BigDecimal amount = resultSet.getBigDecimal("amount");
                int categoryId = resultSet.getInt("categoryId");

                expenses.add(new Expense(id, name, description,amount,categoryId));
            }
        }
        return expenses;
    }

}
