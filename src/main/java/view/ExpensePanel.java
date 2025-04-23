package view;

import entity.Expense;
import entity.user.User;
import service.CategoryService;
import service.ExpenseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ExpensePanel extends JPanel {
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private final ExpenseService expenseService = new ExpenseService();
    private User user;

    public ExpensePanel() {
        this.user = user;
        setLayout(new BorderLayout());
        initTable();
        initToolbar();
        loadExpenses();
    }

    private void initTable() {
        String[] columns = {"ID", "Name", "Description", "Amount", "Category"};
        tableModel = new DefaultTableModel(columns, 0);
        expenseTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            try {
                new AddExpenseDialog(parentFrame).setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        toolbar.add(addButton);
        add(toolbar, BorderLayout.SOUTH);
    }

    private void loadExpenses() {
        try {
            List<Expense> expenses = expenseService.listExpenses();
            tableModel.setRowCount(0);

            for (Expense expense : expenses) {
                tableModel.addRow(new Object[]{
                        expense.getExpenseId(),
                        expense.getName(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expenseService.getCategoryName(expense.getCategoryId())

                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load expenses:\n" + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
