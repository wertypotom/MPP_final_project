package view;

import entity.Expense;
import entity.UserSession;
import service.ExpenseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ExpensePanel extends JPanel {
    JTable expenseTable;
    DefaultTableModel tableModel;
    final ExpenseService expenseService = new ExpenseService();

    public ExpensePanel() {
        setLayout(new BorderLayout());
        initToolbar();
        initTable();
        loadExpenses();
    }

    private void initToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            try {
                new AddExpenseDialog(parentFrame, this).setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Failed to open dialog: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        toolbar.add(addButton);
        add(toolbar, BorderLayout.SOUTH);
    }

    private void initTable() {
        String[] columns = {"Name", "Description", "Amount", "Category", ""};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        expenseTable = new JTable(tableModel);
        expenseTable.setRowHeight(32);
        expenseTable.getColumnModel().getColumn(4).setMinWidth(70);
        expenseTable.getColumnModel().getColumn(4).setMaxWidth(80);
        expenseTable.getColumnModel().getColumn(4).setPreferredWidth(75);

        ExpenseTableButtons buttonCell = new ExpenseTableButtons(this);
        expenseTable.getColumnModel().getColumn(4).setCellRenderer(buttonCell);
        expenseTable.getColumnModel().getColumn(4).setCellEditor(buttonCell);

        expenseTable.getColumnModel().getColumn(0).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel();
            if (value instanceof Expense expense) {
                label.setText(expense.getName());
            } else {
                label.setText(String.valueOf(value));
            }
            return label;
        });

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateExpenseInTable(Expense updatedExpense) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object obj = tableModel.getValueAt(i, 0);
            if (obj instanceof Expense exp && exp.getExpenseId().equals(updatedExpense.getExpenseId())) {
                tableModel.setValueAt(updatedExpense, i, 0);
                tableModel.setValueAt(updatedExpense.getDescription(), i, 1);
                tableModel.setValueAt(updatedExpense.getAmount(), i, 2);
                try {
                    tableModel.setValueAt(expenseService.getCategoryName(updatedExpense.getCategoryId()), i, 3);
                } catch (SQLException e) {
                    tableModel.setValueAt("?", i, 3); // fallback if lookup fails
                }
                break;
            }
        }
    }

    private void loadExpenses() {
        try {
            List<Expense> expenses = expenseService.listExpenses(UserSession.getInstance().getUserId());
            tableModel.setRowCount(0);
            for (Expense expense : expenses) {
                tableModel.addRow(new Object[]{
                        expense,
                        expense.getDescription(),
                        expense.getAmount(),
                        expenseService.getCategoryName(expense.getCategoryId()),
                        null
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load expenses:\n" + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addExpenseToTable(Expense expense) {
        try {
            tableModel.addRow(new Object[]{
                    expense,
                    expense.getDescription(),
                    expense.getAmount(),
                    expenseService.getCategoryName(expense.getCategoryId()),
                    null
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load category name:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}