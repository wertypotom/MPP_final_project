package view;

import entity.Category;
import entity.Expense;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.SQLException;

public class CategoryTableButtons extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel editorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
    private final JPanel renderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
    private final JButton deleteButton = new JButton("x");
    private final JButton editButton = new JButton("✎");

    private final CategoryPanel categoryPanel;
    private int currentRow;

    public CategoryTableButtons(CategoryPanel categoryPanel) {
        this.categoryPanel = categoryPanel;

        Dimension buttonSize = new Dimension(24, 24);

        deleteButton.setPreferredSize(buttonSize);
        editButton.setPreferredSize(buttonSize);
        deleteButton.setFocusable(false);
        editButton.setFocusable(false);

        // Editor panel (active when clicked)
        editorPanel.add(deleteButton);
        editorPanel.add(editButton);

        editorPanel.setOpaque(true);
        editorPanel.setBackground(Color.WHITE);

        renderPanel.setOpaque(true);
        renderPanel.setBackground(Color.WHITE);

        editorPanel.setBorder(new EmptyBorder(4, 0, 4, 0));
        renderPanel.setBorder(new EmptyBorder(4, 0, 4, 0));

        // Renderer panel (always looks like buttons)
        JButton deleteRenderButton = new JButton("x");
        JButton editRenderButton = new JButton("✎");
        deleteRenderButton.setPreferredSize(buttonSize);
        editRenderButton.setPreferredSize(buttonSize);
        deleteRenderButton.setFocusable(false);
        editRenderButton.setFocusable(false);

        renderPanel.add(deleteRenderButton);
        renderPanel.add(editRenderButton);

        deleteButton.addActionListener(e -> {
            int modelRow = categoryPanel.categoryTable.getEditingRow();
            fireEditingStopped(); // 🔐 Stop editing BEFORE removing the row

            Object rawValue = categoryPanel.tableModel.getValueAt(modelRow, 0);
            if (!(rawValue instanceof Category category)) {
                JOptionPane.showMessageDialog(categoryPanel, "Unable to delete: invalid category data", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showOptionDialog(
                    categoryPanel,
                    "Do you want to delete category \"" + category.getCategoryName() + "\" - \"" + category.getDescription() + "\"?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[]{"Delete", "Cancel"},
                    "Cancel"
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    categoryPanel.categoryService.deleteCategory(category.getCategoryId());
                    categoryPanel.tableModel.removeRow(modelRow);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(categoryPanel, "Failed to delete expense:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        editButton.addActionListener(e -> {
            Object rawValue = categoryPanel.tableModel.getValueAt(currentRow, 0);
            if (!(rawValue instanceof Category category)) {
                JOptionPane.showMessageDialog(categoryPanel, "Unable to edit: invalid expense data", "Error", JOptionPane.ERROR_MESSAGE);
                fireEditingStopped();
                return;
            }

            try {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(categoryPanel);
                new AddCategoryDialog(parentFrame, categoryPanel, category).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(categoryPanel, "Failed to open edit dialog: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return renderPanel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        return editorPanel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}