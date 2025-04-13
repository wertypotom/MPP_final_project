package controller;

import entity.User;
import service.CategoryService;
import service.ExpenseService;

public class AppController {
    private User currentUser;
    private final CategoryService categoryService;
    private final ExpenseService expenseService;

    public AppController() {
        this.categoryService = new CategoryService();
        this.expenseService = new ExpenseService();
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public ExpenseService getExpenseService() {
        return expenseService;
    }

    public void login(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

//    public boolean isAdmin() {
//        return currentUser != null && "Admin".equalsIgnoreCase(currentUser.getUserType());
//    }
}
