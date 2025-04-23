package view;

import entity.Category;
import entity.Expense;
import entity.UserSession;
import service.CategoryService;
import service.ExpenseService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AddExpenseDialog extends JDialog {
    private final ExpenseService expenseService = new ExpenseService();
    private final CategoryService categoryService = new CategoryService();
    private final JTextField nameField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    private final JTextField amountField = new JTextField(10);
    private final JComboBox<Category> categoryList = new JComboBox<>();
    private final ExpensePanel expensePanel;

    public AddExpenseDialog(JFrame parent, ExpensePanel expensePanel) throws SQLException {
        super(parent, "Add Expense", true);
        this.expensePanel = expensePanel;
        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Load categories from the service and populate dropdown
        List<Category> categories = categoryService.listCategories();
        for (Category category : categories) {
            categoryList.addItem(category);
        }

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Name:"));
        form.add(nameField);

        form.add(new JLabel("Description:"));
        form.add(descriptionField);

        form.add(new JLabel("Amount:"));
        form.add(amountField);

        form.add(new JLabel("Category:"));
        form.add(categoryList);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitBtn = new JButton("Submit");
        JButton cancelBtn = new JButton("Cancel");

        cancelBtn.addActionListener(e -> dispose());
        submitBtn.addActionListener(e -> {
            String name = getExpenseName();
            String description = getExpenseDescription();
            Category category = getSelectedCategory();

            if (name.isEmpty() || description.isEmpty() || category == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BigDecimal amount = getExpenseAmount();
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than zero.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Expense expense = expenseService.createExpense(name, description, amount, category.getCategoryId(), UserSession.getInstance().getUserId());
                expensePanel.addExpenseToTable(expense);
                JOptionPane.showMessageDialog(this, "Expense added successfully!");
                dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttons.add(cancelBtn);
        buttons.add(submitBtn);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    public String getExpenseName() {
        return nameField.getText().trim();
    }

    public String getExpenseDescription() {
        return descriptionField.getText().trim();
    }

    public BigDecimal getExpenseAmount() {
        try {
            return new BigDecimal(amountField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return BigDecimal.ZERO; // or null depending on how you want to handle it
        }
    }

    public Category getSelectedCategory() {
        return (Category) categoryList.getSelectedItem();
    }


}
