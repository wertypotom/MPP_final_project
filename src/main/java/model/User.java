package model;

public class User {
    private int id;
    private String name;
    private String email;
    private String userType;

    public User(int id, String name, String email, String userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getUserType() { return userType; }
}
