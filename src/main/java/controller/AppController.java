package controller;

import entity.user.User;
import service.CategoryService;
import service.ExpenseService;
import service.ReportService;

public class AppController {
    private User currentUser;
    private final CategoryService categoryService;
    private final ExpenseService expenseService;
    private final ReportService reportService;

    public AppController() {
        this.categoryService = new CategoryService();
        this.expenseService = new ExpenseService();
        this.reportService = new ReportService();
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public ExpenseService getExpenseService() {
        return expenseService;
    }

    public ReportService getReportService() {
        return reportService;
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
