package factory;

import entity.user.Admin;
import entity.user.StandardUser;
import entity.user.User;

public class UserFactory {
    public static User createUser(String userType, int id, String name, String email) {
        return switch (userType) {
            case "Admin" -> new Admin(id, name, email);
            default -> new StandardUser(id, name, email);
        };
    }
}