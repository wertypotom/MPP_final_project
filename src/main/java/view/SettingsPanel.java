package view;

import javax.swing.*;
import java.util.function.Consumer;

public class SettingsPanel {
    public static void show(JFrame parent, JButton anchor,
                            Consumer<String> onNavigate, Runnable onLogout) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem homeItem = new JMenuItem("Home");
        homeItem.addActionListener(e -> onNavigate.accept("home"));
        menu.add(homeItem);

        JMenuItem categoryItem = new JMenuItem("Category Management");
        categoryItem.addActionListener(e -> onNavigate.accept("categories"));
        menu.add(categoryItem);

        JMenuItem reportItem = new JMenuItem("Report");
        reportItem.addActionListener(e -> onNavigate.accept("report"));
        menu.add(reportItem);

        JMenuItem logoutItem = new JMenuItem("Log Out");
        logoutItem.addActionListener(e -> onLogout.run());
        menu.add(logoutItem);

        menu.show(anchor, anchor.getWidth() - 10, anchor.getHeight());
    }
}