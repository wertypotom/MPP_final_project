package service;

import entity.user.User;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public User loginUser(String email, String password) throws SQLException {
        return userRepository.getUserByEmailAndPassword(email, password);
    }
}
