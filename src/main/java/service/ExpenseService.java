package service;

import entity.Expense;
import repository.ExpenseRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ExpenseService {
    private final ExpenseRepository expenseRepository = new ExpenseRepository();;

    // Create a new category
    public Expense createExpense(String name, String description, BigDecimal amount,Integer categoryId, Integer userId) throws SQLException {
        Expense expense = new Expense(name,description,amount,categoryId,userId);
        return expenseRepository.createExpense(expense);
    }

    // delete expense with specific id
    public void deleteExpense(int expenseId) throws SQLException {
        expenseRepository.deleteExpenseById(expenseId);
    }

    // Read all expenses of user
    public List<Expense> listExpenses(int userId) throws SQLException {
        return expenseRepository.listExpenses(userId);
    }

    public String getCategoryName(Integer categoryId) throws SQLException {
        return expenseRepository.getCategoryName(categoryId);
    }
}
