package view;

import controller.AppController;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainView extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final AppController controller;
    private final Map<String, JPanel> views = new HashMap<>();

    public MainView(AppController controller, User user) {
        this.controller = controller;
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
            SettingsPanel.show(this, settingsButton, controller, this::navigateTo, this::logout);
        });

        topPanel.add(new JLabel("Welcome, " + user.getUserName()), BorderLayout.WEST);
        topPanel.add(settingsButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Views
        views.put("home", new ExpensePanel());
        views.put("categories", new CategoryPanel());
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
        controller.logout();
        dispose();
        new LoginView(controller, user -> {
            MainView mv = new MainView(controller, user);
            mv.setVisible(true);
        }).setVisible(true);
    }
}