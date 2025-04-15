package service;

import entity.Category;
import entity.Expense;
import entity.User;
import repository.CategoryRepository;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository = new CategoryRepository();;

    // Create a new category
    public void createCategory(String name, String description) throws SQLException {
        Category category = new Category(name, description);
        categoryRepository.createCategory(category);
    }

    // Read all categories
    public List<Category> listCategories() throws SQLException {
        return categoryRepository.listCategories();
    }

}
