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
    private final Expense editingExpense;

    public AddExpenseDialog(JFrame parent, ExpensePanel expensePanel) throws SQLException {
        this(parent, expensePanel, null);
    }

    public AddExpenseDialog(JFrame parent, ExpensePanel expensePanel, Expense expenseToEdit) throws SQLException {
        super(parent, expenseToEdit == null ? "Add Expense" : "Edit Expense", true);
        this.expensePanel = expensePanel;
        this.editingExpense = expenseToEdit;
        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Load categories
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

        // Prefill fields if editing
        if (editingExpense != null) {
            nameField.setText(editingExpense.getName());
            descriptionField.setText(editingExpense.getDescription());
            amountField.setText(editingExpense.getAmount().toString());

            for (int i = 0; i < categoryList.getItemCount(); i++) {
                if (categoryList.getItemAt(i).getCategoryId() == editingExpense.getCategoryId()) {
                    categoryList.setSelectedIndex(i);
                    break;
                }
            }
        }

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitBtn = new JButton("Submit");
        JButton cancelBtn = new JButton("Cancel");

        cancelBtn.addActionListener(e -> dispose());

        submitBtn.addActionListener(e -> {
            String name = getExpenseName();
            String description = getExpenseDescription();
            BigDecimal amount = getExpenseAmount();
            Category category = getSelectedCategory();

            if (name.isEmpty() || description.isEmpty() || category == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than zero.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (editingExpense == null) {
                    Expense newExpense = new Expense(name, description, amount, category.getCategoryId(), UserSession.getInstance().getUserId());
                    Expense created = expenseService.createOrUpdateExpense(newExpense);
                    expensePanel.addExpenseToTable(created);
                } else {
                    editingExpense.setName(name);
                    editingExpense.setDescription(description);
                    editingExpense.setAmount(amount);
                    editingExpense.setCategoryId(category.getCategoryId());
                    expenseService.createOrUpdateExpense(editingExpense);
                    expensePanel.updateExpenseInTable(editingExpense);
                }
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saving expense: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
            return BigDecimal.ZERO;
        }
    }

    public Category getSelectedCategory() {
        return (Category) categoryList.getSelectedItem();
    }
}