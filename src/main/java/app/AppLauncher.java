package app;

import view.LoginView;
import view.MainView;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView[] loginViewRef = new LoginView[1];
            loginViewRef[0] = new LoginView(user -> {
                MainView mainView = new MainView(user);
                mainView.setVisible(true);
                loginViewRef[0].dispose();
            });

            loginViewRef[0].setVisible(true);
        });
    }
}