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
