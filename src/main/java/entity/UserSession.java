package entity;

import java.util.Optional;

public class UserSession {
    private static UserSession session = null;
    private int userId;
    private String userName;

    private UserSession() {
        System.out.println("A user session created");
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public static UserSession getInstance() {
        return Optional.ofNullable(session)
                .orElseGet(() -> {
                    session = new UserSession();
                    return session;
                });
    }

    public void setUser(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public void clear() {
        this.userId = 0;
        this.userName = null;
    }
}
