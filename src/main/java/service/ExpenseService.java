package service;

import entity.Category;
import entity.Expense;
import repository.CategoryRepository;
import repository.ExpenseRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ExpenseService {
    private final ExpenseRepository expenseRepository = new ExpenseRepository();;

    // Create a new category
    public void createExpense(String name, String description, BigDecimal amount,Integer categoryId, Integer userId) throws SQLException {
        Expense expense = new Expense(name,description,amount,categoryId,userId);
        expenseRepository.createExpense(expense);
    }

    // Read all categories
    public List<Expense> listExpenses() throws SQLException {
        return expenseRepository.listExpenses();
    }

    public String getCategoryName(Integer categoryId) throws SQLException {
        return expenseRepository.getCategoryName(categoryId);
    }
}
