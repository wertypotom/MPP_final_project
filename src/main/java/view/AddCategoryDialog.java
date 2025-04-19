package view;

import service.CategoryService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddCategoryDialog extends JDialog {
    private final CategoryService categoryService = new CategoryService();
    private final JTextField nameField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);

    public AddCategoryDialog(JFrame parent) {
        super(parent, "Add Category", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Name:"));
        form.add(nameField);

        form.add(new JLabel("Description:"));
        form.add(descriptionField);

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
                categoryService.createCategory(name,description,1);//1 is temporary, will replace once user id is available from login
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(this, "Category added successfully!");
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
