package view;

import entity.Expense;
import entity.user.User;
import service.CategoryService;
import entity.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;

public class CategoryPanel extends JPanel {
    JTable categoryTable;
    DefaultTableModel tableModel;
    final CategoryService categoryService = new CategoryService();
    private List<Category> allCategories = new ArrayList<>();

    public CategoryPanel(User user) {
        setLayout(new BorderLayout());
        initTable();
        initToolbar();
        loadCategories();
    }

//    private void initTable() {
//        String[] columns = {"ID", "Name", "Description", "Update", "Delete"};
//        tableModel = new DefaultTableModel(columns, 0);
//        categoryTable = new JTable(tableModel);
//
//        // Set up custom button renderer and editor for "Update" and "Delete"
//        categoryTable.getColumn("Update").setCellRenderer(new ButtonRenderer());
//        categoryTable.getColumn("Update").setCellEditor(new ButtonEditor(new JCheckBox(), "Update"));
//
//        categoryTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
//        categoryTable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete"));
//
//        JScrollPane scrollPane = new JScrollPane(categoryTable);
//        add(scrollPane, BorderLayout.CENTER);
//    }

    private void initTable() {
        String[] columns = {"ID", "Name", "Description", ""};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        categoryTable = new JTable(tableModel);
        categoryTable.setRowHeight(32);
        categoryTable.getColumnModel().getColumn(3).setMinWidth(70);
        categoryTable.getColumnModel().getColumn(3).setMaxWidth(80);
        categoryTable.getColumnModel().getColumn(3).setPreferredWidth(75);

        CategoryTableButtons buttonCell = new CategoryTableButtons(this);
        categoryTable.getColumnModel().getColumn(3).setCellRenderer(buttonCell);
        categoryTable.getColumnModel().getColumn(3).setCellEditor(buttonCell);

        categoryTable.getColumnModel().getColumn(0).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel();
            if (value instanceof Expense expense) {
                label.setText(expense.getName());
            } else {
                label.setText(String.valueOf(value));
            }
            return label;
        });

        JScrollPane scrollPane = new JScrollPane(categoryTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Category");

        addButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddCategoryDialog dialog = null;
            try {
                dialog = new AddCategoryDialog(parentFrame, this);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            dialog.setVisible(true);

            loadCategories();
        });

        toolbar.add(addButton);
        add(toolbar, BorderLayout.SOUTH);
    }


    private void loadCategories() {
        try {
            allCategories = categoryService.listCategories();
            tableModel.setRowCount(0); // Clear existing rows

            for (Category category : allCategories) {
                tableModel.addRow(new Object[]{
                        category,
                        category.getCategoryName(),
                        category.getDescription(),
                        null
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load categories:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateCategoryInTable(Category updatedCategory) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object obj = tableModel.getValueAt(i, 0);
            if (obj instanceof Category c && c.getCategoryId() == updatedCategory.getCategoryId()) {
                tableModel.setValueAt(updatedCategory, i, 0);
                tableModel.setValueAt(updatedCategory.getCategoryName(), i, 1);
                tableModel.setValueAt(updatedCategory.getDescription(), i, 2);
                break;
            }
        }
        allCategories.removeIf(e -> e.getCategoryId() == updatedCategory.getCategoryId());
        allCategories.add(updatedCategory);
    }
}
