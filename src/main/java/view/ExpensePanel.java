package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ExpensePanel extends JPanel {
    private JTable expenseTable;
    private DefaultTableModel tableModel;

    public ExpensePanel() {
        setLayout(new BorderLayout());
        initTable();
        initToolbar();
    }

    private void initTable() {
        String[] columns = {"ID", "Name", "Description", "Amount", "Category", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        expenseTable = new JTable(tableModel);

        tableModel.addRow(new Object[]{1, "Lunch", "Food at restaurant", 12.5, "Food", "2025-04-12"});
        tableModel.addRow(new Object[]{2, "Book", "Java Programming", 35.0, "Education", "2025-04-10"});

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            new AddExpenseDialog(parentFrame).setVisible(true);
        });
        toolbar.add(addButton);
        add(toolbar, BorderLayout.SOUTH);
    }
}
