package service;

import entity.Expense;
import repository.ExpenseRepository;

import java.sql.SQLException;
import java.util.List;

public class ExpenseService {
    private final ExpenseRepository expenseRepository = new ExpenseRepository();;

    // Create a new category
    public Expense createOrUpdateExpense(Expense expense) throws SQLException {
        if (expense.getExpenseId() == null) {
            return expenseRepository.createExpense(expense);
        } else {
            expenseRepository.updateExpense(expense);
            return expense;
        }
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
