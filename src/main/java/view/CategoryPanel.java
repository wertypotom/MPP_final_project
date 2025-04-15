package view;

import service.CategoryService;
import entity.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CategoryPanel extends JPanel {
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    private final CategoryService categoryService = new CategoryService();

    public CategoryPanel() {
        setLayout(new BorderLayout());
        initTable();
        initToolbar();
        loadCategories();
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
            AddCategoryDialog dialog = new AddCategoryDialog(parentFrame);
            dialog.setVisible(true);

            loadCategories();
        });

        toolbar.add(addButton);
        add(toolbar, BorderLayout.SOUTH);
    }


    private void loadCategories() {
        try {
            List<Category> categories = categoryService.listCategories();
            tableModel.setRowCount(0); // Clear existing rows

            for (Category category : categories) {
                tableModel.addRow(new Object[]{
                        category.getCategoryId(),
                        category.getCategoryName(),
                        category.getDescription(),
                        category.getCreatedDateTimeStamp(),
                        category.getModifiedDateTimeStamp()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load categories:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
