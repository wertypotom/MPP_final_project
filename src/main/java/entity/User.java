package entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public userType getRole() {
        return role;
    }

    public void setRole(userType role) {
        this.role = role;
    }

    public BigDecimal getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(BigDecimal budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Expense> getExpenses() {
        return this.expenses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Category> getCategories() {
        return this.categories;
    }


    private Integer userId;
    private String userName;
    private String email;
    private String password;
    private userType role;
    private BigDecimal budgetLimit;

    private Set<Category> categories = new HashSet<Category>(0);
    private Set<Expense> expenses = new HashSet<Expense>(0);


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
