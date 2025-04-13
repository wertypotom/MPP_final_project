package service;

import entity.User;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    // Create a new user
    public void createUser(String name, String email) throws SQLException {
        User user = new User(0, name, email);
        userRepository.createUser(user);
    }

    // Read all users
    public List<User> readUsers() throws SQLException {
        return userRepository.readUsers();
    }
}
