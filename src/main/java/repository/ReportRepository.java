package repository;

import entity.Category;
import entity.report.ExpenseReport;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportRepository {

    public List<ExpenseReport> getExpenseReport(int userId, String fromDate, String toDate, int catId) throws SQLException {
        List<ExpenseReport> reportData = new ArrayList<>();

        String query =
                "SELECT DATE_FORMAT(e.createdOn, '%d %b %Y') AS createdDate, " +
                        "e.name, e.description, e.amount, " +
                        "c.name AS categoryName " +
                        "FROM expense e " +
                        "JOIN category c ON e.categoryId = c.id " +
                        "WHERE e.userId = ? AND Date(e.createdOn) BETWEEN ? AND ?";

        if (catId != 0) {
            query += " AND e.categoryId = ?";
        }

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setString(2, fromDate);
            statement.setString(3, toDate);
            if (catId != 0) {
                statement.setInt(4, catId);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String date = resultSet.getString("createdDate");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                String categoryName = resultSet.getString("categoryName");

                reportData.add(new ExpenseReport(date, name, description, amount, categoryName));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching report: " + e.getMessage());
        }

        return reportData;
    }
}
