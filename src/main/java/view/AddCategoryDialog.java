package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddCategoryDialog extends JDialog {
    private final JTextField nameField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    JButton submitBtn = new JButton("Submit");

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

        JButton cancelBtn = new JButton("Cancel");

        cancelBtn.addActionListener(e -> dispose());

        submitBtn.addActionListener(e -> {
            // Handle data collection and validation
            System.out.println("Category submitted: " + nameField.getText());
            dispose();
        });

        buttons.add(cancelBtn);
        buttons.add(submitBtn);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
    public void addSubmitListener(ActionListener listener) {
        submitBtn.addActionListener(listener);
    }

    public String getCategoryName() {
        return nameField.getText().trim();
    }

    public String getCategoryDescription() {
        return descriptionField.getText().trim();
    }

}
