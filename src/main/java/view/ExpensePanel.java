package view;

import entity.Expense;
import entity.UserSession;
import entity.user.Admin;
import entity.user.User;
import service.ExpenseService;
import service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpensePanel extends JPanel {
    JTable expenseTable;
    DefaultTableModel tableModel;
    private JProgressBar budgetProgressBar;
    final ExpenseService expenseService = new ExpenseService();
    final User user;
    private List<Expense> allExpenses = new ArrayList<>();
    private BigDecimal budgetLimit = BigDecimal.ZERO;

    public ExpensePanel(User user) {
        this.user = user;
        setLayout(new BorderLayout());
        add(new ExpenseSearchBar(this::filterTable), BorderLayout.NORTH);
        if (!(user instanceof Admin)) {
            this.budgetLimit = user.getBudgetLimit();
            initBudgetProgressBar();
        }
        initToolbar();
        initTable();
        loadExpenses();
    }

    //    private void initToolbar() {
//        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        JButton addButton = new JButton("Add Expense");
//        addButton.addActionListener(e -> {
//            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
//            try {
//                new AddExpenseDialog(parentFrame, this).setVisible(true);
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(this, "Failed to open dialog: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//        toolbar.add(addButton);
//        add(toolbar, BorderLayout.SOUTH);
//    }
    private void initToolbar() {
        JPanel toolbar = new JPanel(new BorderLayout());
        if (!(user instanceof Admin)) {
            initBudgetProgressBar();
            toolbar.add(budgetProgressBar, BorderLayout.NORTH);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            try {
                new AddExpenseDialog(parentFrame, this).setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Failed to open dialog: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(addButton);

        toolbar.add(buttonPanel, BorderLayout.SOUTH);
        add(toolbar, BorderLayout.SOUTH);
    }

    private void initTable() {
        String[] columns = {"Name", "Description", "Amount", "Category", "Date", ""};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        expenseTable = new JTable(tableModel);
        expenseTable.setRowHeight(32);
        expenseTable.getColumnModel().getColumn(5).setMinWidth(70);
        expenseTable.getColumnModel().getColumn(5).setMaxWidth(80);
        expenseTable.getColumnModel().getColumn(5).setPreferredWidth(75);

        ExpenseTableButtons buttonCell = new ExpenseTableButtons(this);
        expenseTable.getColumnModel().getColumn(5).setCellRenderer(buttonCell);
        expenseTable.getColumnModel().getColumn(5).setCellEditor(buttonCell);

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
                    tableModel.setValueAt("?", i, 3);
                }
                tableModel.setValueAt(updatedExpense.getCreatedDateTimeStamp().toLocalDate(), i, 4);
                break;
            }
        }
        allExpenses.removeIf(e -> e.getExpenseId().equals(updatedExpense.getExpenseId()));
        allExpenses.add(updatedExpense);
        if (!(user instanceof Admin)) {
            updateBudgetProgressBar();
        }
    }

    public void removeExpenseFromTable(int modelRow, int expenseId) {
        tableModel.removeRow(modelRow);
        allExpenses.removeIf(e -> e.getExpenseId().equals(expenseId));
        if (!(user instanceof Admin)) {
            updateBudgetProgressBar();
        }
    }

    private void loadExpenses() {
        try {
            allExpenses = expenseService.listExpenses(UserSession.getInstance().getUserId());
            updateTable(allExpenses);
            if (!(user instanceof Admin)) {
                updateBudgetProgressBar();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load expenses:\n" + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Expense> expenses) {
        tableModel.setRowCount(0);
        for (Expense expense : expenses) {
            try {
                tableModel.addRow(new Object[]{
                        expense,
                        expense.getDescription(),
                        expense.getAmount(),
                        expenseService.getCategoryName(expense.getCategoryId()),
                        expense.getCreatedDateTimeStamp().toLocalDate(),
                        null
                });
            } catch (SQLException e) {
                tableModel.addRow(new Object[]{
                        expense,
                        expense.getDescription(),
                        expense.getAmount(),
                        "?",
                        expense.getCreatedDateTimeStamp().toLocalDate(),
                        null
                });
            }
        }
    }

    private String safeCategoryName(Expense e) {
        try {
            return expenseService.getCategoryName(e.getCategoryId());
        } catch (Exception ex) {
            return "";
        }
    }

    private void filterTable(String text) {
        if (text.isEmpty()) {
            updateTable(allExpenses);
            return;
        }

        List<Expense> filtered = allExpenses.stream().filter(e ->
                e.getName().toLowerCase().contains(text) ||
                        e.getDescription().toLowerCase().contains(text) ||
                        safeCategoryName(e).toLowerCase().contains(text) ||
                        e.getCreatedDateTimeStamp().toLocalDate().toString().contains(text)
        ).toList();

        updateTable(filtered);
    }

    public void addExpenseToTable(Expense expense) {
        allExpenses.add(expense);

        try {
            tableModel.addRow(new Object[]{
                    expense,
                    expense.getDescription(),
                    expense.getAmount(),
                    expenseService.getCategoryName(expense.getCategoryId()),
                    expense.getCreatedDateTimeStamp().toLocalDate(),
                    null
            });
            if (!(user instanceof Admin)) {
                updateBudgetProgressBar();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load category name:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initBudgetProgressBar() {
        budgetProgressBar = new JProgressBar(0, budgetLimit.intValue());
        budgetProgressBar.setStringPainted(true);
    }

    private void updateBudgetProgressBar() {
        BigDecimal total = allExpenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalInt = total.intValue(); // for progress bar (truncates decimals)
        budgetProgressBar.setMaximum(budgetLimit.intValue()); // budgetLimit is BigDecimal
        budgetProgressBar.setValue(totalInt);
        budgetProgressBar.setString("Budget Used: $" + total + " / $" + budgetLimit);
        budgetProgressBar.setForeground(total.compareTo(budgetLimit) > 0
                ? Color.RED
                : new Color(0, 128, 0));
    }

}