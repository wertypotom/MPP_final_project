package entity.user;

import entity.Category;
import entity.Expense;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
public abstract class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "budgetLimit")
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

    private int userId;
    private String name;
    private String email;
    private String password;
    private BigDecimal budgetLimit;

    private Set<Category> categories = new HashSet<Category>(0);
    private Set<Expense> expenses = new HashSet<Expense>(0);

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", budgetLimit=" + budgetLimit +
                ", categories=" + categories +
                ", expenses=" + expenses +
                '}';
    }
}
