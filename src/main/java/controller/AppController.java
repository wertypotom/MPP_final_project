package controller;

import entity.User;

public class AppController {
    private User currentUser;

    public void login(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

//    public boolean isAdmin() {
//        return currentUser != null && "Admin".equalsIgnoreCase(currentUser.getUserType());
//    }
}
