package entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category(int id, String categoryName, String description) {
        this.categoryId = id;
        this.categoryName = categoryName;
        this.description = description;
    }

    private int categoryId;
    private String categoryName;
    private String description;
    private LocalDate createdDateTimeStamp;
    private LocalDate modifiedDateTimeStamp;
    private Integer createdUserId;
    private Integer modifiedUserId;
    private Set<Expense> expenses = new HashSet<Expense>(0);

}
