package view;

import entity.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainView extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final Map<String, JPanel> views = new HashMap<>();

    public MainView(User user) {
        setTitle("Expense Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        initUI(user);
    }

    private void initUI(User user) {
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton settingsButton = new JButton("⚙");
        settingsButton.addActionListener(e -> {
            SettingsPanel.show(settingsButton, this::navigateTo, this::logout, user);
        });

        topPanel.add(new JLabel("Welcome, " + user.getName()), BorderLayout.WEST);
        topPanel.add(settingsButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Views
        views.put("home", new ExpensePanel(user));
        views.put("categories", new CategoryPanel(user));
        views.put("report", new ReportPanel());

        contentPanel.add(views.get("home"), "home");
        contentPanel.add(views.get("categories"), "categories");
        contentPanel.add(views.get("report"), "report");

        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, "home");
    }

    private void navigateTo(String viewName) {
        cardLayout.show(contentPanel, viewName);
    }

    private void logout() {
        dispose();
        new LoginView(user -> {
            MainView mv = new MainView(user);
            mv.setVisible(true);
        }).setVisible(true);
    }
}