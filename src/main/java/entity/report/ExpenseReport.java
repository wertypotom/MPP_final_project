package entity.report;

public class ExpenseReport {
    private String date;
    private String name;
    private String description;
    private double amount;
    private String categoryName;

    public ExpenseReport(String date, String name, String description, double amount, String categoryName) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.categoryName = categoryName;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategoryName() {
        return categoryName;
    }
}

