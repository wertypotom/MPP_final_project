package entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

public class Expense {
    private Integer expenseId;
    private String name;
    private String description;

    public Expense(String name, String description, BigDecimal amount,Integer categoryId) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.createdDateTimeStamp = LocalDateTime.now();
        this.categoryId = categoryId;
    }

    public Expense(Integer expenseId, String name, String description, BigDecimal amount,Integer categoryId) {
        this(name,description,amount,categoryId);
        this.expenseId = expenseId;

    }

    public Expense(String name, String description, BigDecimal amount,Integer categoryId, Integer userId) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.createdDateTimeStamp = LocalDateTime.now();
        this.categoryId = categoryId;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false, length = 19)
    public LocalDateTime getCreatedDateTimeStamp() {
        return createdDateTimeStamp;
    }

    public void setCreatedDateTimeStamp(LocalDateTime createdDateTimeStamp) {
        this.createdDateTimeStamp = createdDateTimeStamp;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate", nullable = false, length = 19)
    public LocalDateTime getModifiedDateTimeStamp() {
        return modifiedDateTimeStamp;
    }

    public void setModifiedDateTimeStamp(LocalDateTime modifiedDateTimeStamp) {
        this.modifiedDateTimeStamp = modifiedDateTimeStamp;
    }

    private BigDecimal amount;
    private LocalDateTime createdDateTimeStamp;
    private LocalDateTime modifiedDateTimeStamp;
    private Integer categoryId;
    private Integer userId;

}
