package view;

import entity.Category;
import entity.Expense;
import entity.UserSession;
import entity.user.User;
import service.CategoryService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddCategoryDialog extends JDialog {
    private final CategoryService categoryService = new CategoryService();
    private final JTextField nameField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    private final Category editingCategory;
    private final CategoryPanel categoryPanel;

    public AddCategoryDialog(JFrame parent, CategoryPanel categoryPanel) throws SQLException {
        this(parent, categoryPanel, null);
    }

    public AddCategoryDialog(JFrame parent, CategoryPanel categoryPanel, Category categoryToEdit) {
        super(parent, categoryToEdit == null ? "Add Category" : "Edit Category", true);
        this.editingCategory = categoryToEdit;
        this.categoryPanel = categoryPanel;
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Name:"));
        form.add(nameField);

        form.add(new JLabel("Description:"));
        form.add(descriptionField);

        // Prefill fields if editing
        if (editingCategory != null) {
            nameField.setText(editingCategory.getCategoryName());
            descriptionField.setText(editingCategory.getDescription());
        }

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitBtn = new JButton("Submit");
        JButton cancelBtn = new JButton("Cancel");

        cancelBtn.addActionListener(e -> dispose());

        submitBtn.addActionListener(e -> {
            String name = getCategoryName();
            String description = getCategoryDescription();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Category name is required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (editingCategory == null) {
                    Category category = new Category(name,description,UserSession.getInstance().getUserId());
                    categoryService.createCategory(category);
                    JOptionPane.showMessageDialog(this, "Category added successfully!");
                } else {
                    editingCategory.setCategoryName(name);
                    editingCategory.setDescription(description);
                    editingCategory.setModifiedUserId(UserSession.getInstance().getUserId());
                    categoryService.updateCategory(editingCategory);
                    categoryPanel.updateCategoryInTable(editingCategory);
                    JOptionPane.showMessageDialog(this, "Category updated successfully!");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


            dispose();
        });

        buttons.add(cancelBtn);
        buttons.add(submitBtn);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    public String getCategoryName() {
        return nameField.getText().trim();
    }

    public String getCategoryDescription() {
        return descriptionField.getText().trim();
    }

}
