package view;

import entity.Expense;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.SQLException;

public class ExpenseTableButtons extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel editorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
    private final JPanel renderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
    private final JButton deleteButton = new JButton("x");
    private final JButton editButton = new JButton("✎");

    private final ExpensePanel expensePanel;
    private int currentRow;

    public ExpenseTableButtons(ExpensePanel expensePanel) {
        this.expensePanel = expensePanel;

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
            int modelRow = expensePanel.expenseTable.getEditingRow();
            fireEditingStopped();

            Object rawValue = expensePanel.tableModel.getValueAt(modelRow, 0);
            if (!(rawValue instanceof Expense expense)) {
                JOptionPane.showMessageDialog(expensePanel, "Unable to delete: invalid expense data", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showOptionDialog(
                    expensePanel,
                    "Do you want to delete expense \"" + expense.getName() + "\" - \"" + expense.getDescription() + "\"?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[]{"Delete", "Cancel"},
                    "Cancel"
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    expensePanel.expenseService.deleteExpense(expense.getExpenseId());
                    expensePanel.removeExpenseFromTable(modelRow, expense.getExpenseId());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(expensePanel, "Failed to delete expense:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        editButton.addActionListener(e -> {
            Object rawValue = expensePanel.tableModel.getValueAt(currentRow, 0);
            if (!(rawValue instanceof Expense expense)) {
                JOptionPane.showMessageDialog(expensePanel, "Unable to edit: invalid expense data", "Error", JOptionPane.ERROR_MESSAGE);
                fireEditingStopped();
                return;
            }

            try {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(expensePanel);
                new AddExpenseDialog(parentFrame, expensePanel, expense).setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(expensePanel, "Failed to open edit dialog: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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