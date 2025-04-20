package view;

import entity.user.User;
import entity.user.Admin;

import javax.swing.*;
import java.util.function.Consumer;

public class SettingsPanel {
    public static void show(JButton anchor,
                            Consumer<String> onNavigate, Runnable onLogout, User user) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem homeItem = new JMenuItem("Home");
        homeItem.addActionListener(e -> onNavigate.accept("home"));
        menu.add(homeItem);

        if (user instanceof Admin) {
            JMenuItem categoryItem = new JMenuItem("Category Management");
            categoryItem.addActionListener(e -> onNavigate.accept("categories"));
            menu.add(categoryItem);
        }

        JMenuItem reportItem = new JMenuItem("Report");
        reportItem.addActionListener(e -> onNavigate.accept("report"));
        menu.add(reportItem);

        JMenuItem logoutItem = new JMenuItem("Log Out");
        logoutItem.addActionListener(e -> onLogout.run());
        menu.add(logoutItem);

        menu.show(anchor, anchor.getWidth() - 10, anchor.getHeight());
    }
}