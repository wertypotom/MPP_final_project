package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

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
}
