package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategoryPanel extends JPanel {
    private JTable categoryTable;
    private DefaultTableModel tableModel;

    public CategoryPanel() {
        setLayout(new BorderLayout());
        initTable();
        initToolbar();
    }

    private void initTable() {
        String[] columns = {"ID", "Name", "Description", "Created", "Modified"};
        tableModel = new DefaultTableModel(columns, 0);
        categoryTable = new JTable(tableModel);

        tableModel.addRow(new Object[]{1, "Food", "Meals and groceries", "2025-04-01", "2025-04-10"});
        tableModel.addRow(new Object[]{2, "Education", "Books and courses", "2025-03-15", "2025-04-05"});

        JScrollPane scrollPane = new JScrollPane(categoryTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Category");
        addButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            new AddCategoryDialog(parentFrame).setVisible(true);
        });
        toolbar.add(addButton);
        add(toolbar, BorderLayout.SOUTH);
    }
}
