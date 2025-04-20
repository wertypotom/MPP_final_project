package view;

import javax.swing.*;
import java.awt.*;

public class LoginFailedDialog extends JDialog {

    public LoginFailedDialog(JFrame parent) {
        super(parent, "Login Failed", true);
        setLayout(new BorderLayout());
        setSize(350, 150);
        setLocationRelativeTo(parent);

        JLabel messageLabel = new JLabel("User login or password was incorrect. Please try again.", JLabel.CENTER);
        JButton tryAgainButton = new JButton("Try Again");
        tryAgainButton.addActionListener(e -> dispose());

        add(messageLabel, BorderLayout.CENTER);
        add(tryAgainButton, BorderLayout.SOUTH);
    }
}