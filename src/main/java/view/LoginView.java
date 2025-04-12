package view;

import controller.AppController;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class LoginView extends JFrame {
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final Consumer<User> onLoginSuccess;

    public LoginView(AppController controller, Consumer<User> onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
        setTitle("Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI(controller);
    }

    private void initUI(AppController controller) {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Mock login logic
            if (email.equalsIgnoreCase("admin@admin.com")) {
                User admin = new User(email, "Admin");
                controller.login(admin);
                onLoginSuccess.accept(admin);
            } else {
                User user = new User(email, "Regular User");
                controller.login(user);
                onLoginSuccess.accept(user);
            }
        });

        panel.add(loginButton);
        add(panel);
    }
}
