package view;

import javax.swing.*;
import java.awt.*;

public class AddExpenseDialog extends JDialog {
    private final JTextField nameField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    private final JTextField amountField = new JTextField(10);
    private final JComboBox<String> categoryBox = new JComboBox<>(new String[] {"Food", "Transport", "Education"});

    public AddExpenseDialog(JFrame parent) {
        super(parent, "Add Expense", true);
        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Name:"));
        form.add(nameField);

        form.add(new JLabel("Description:"));
        form.add(descriptionField);

        form.add(new JLabel("Amount:"));
        form.add(amountField);

        form.add(new JLabel("Category:"));
        form.add(categoryBox);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitBtn = new JButton("Submit");
        JButton cancelBtn = new JButton("Cancel");

        cancelBtn.addActionListener(e -> dispose());
        submitBtn.addActionListener(e -> {
            // Here we would validate and pass data back
            System.out.println("Expense submitted: " + nameField.getText());
            dispose();
        });

        buttons.add(cancelBtn);
        buttons.add(submitBtn);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}
