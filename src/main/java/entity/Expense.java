package entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

public class Expense {
    private Integer expenseId;
    private String name;
    private String description;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false, length = 19)
    public LocalDate getCreatedDateTimeStamp() {
        return createdDateTimeStamp;
    }

    public void setCreatedDateTimeStamp(LocalDate createdDateTimeStamp) {
        this.createdDateTimeStamp = createdDateTimeStamp;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate", nullable = false, length = 19)
    public LocalDate getModifiedDateTimeStamp() {
        return modifiedDateTimeStamp;
    }

    public void setModifiedDateTimeStamp(LocalDate modifiedDateTimeStamp) {
        this.modifiedDateTimeStamp = modifiedDateTimeStamp;
    }

    private BigDecimal amount;
    private LocalDate createdDateTimeStamp;
    private LocalDate modifiedDateTimeStamp;
    private Integer categoryId;
    private Integer userId;

}
