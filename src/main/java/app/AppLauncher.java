package app;

import controller.AppController;
import view.LoginView;
import view.MainView;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppController controller = new AppController();

            LoginView[] loginViewRef = new LoginView[1];
            loginViewRef[0] = new LoginView(controller, user -> {
                MainView mainView = new MainView(controller, user);
                mainView.setVisible(true);
                loginViewRef[0].dispose();
            });

            loginViewRef[0].setVisible(true);
        });
    }
}