package service;

import entity.Category;
import repository.CategoryRepository;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository = new CategoryRepository();
    ;

    // Create a new category
    public void createCategory(String name, String description, int createdUserId) throws SQLException {
        Category category = new Category(name, description, createdUserId);
        categoryRepository.createCategory(category);
    }

    // Read all categories
    public List<Category> listCategories() throws SQLException {
        return categoryRepository.listCategories();
    }

    // Create a new category
    public void updateCategory(int id, String name, String description, int modifiedUserId) throws SQLException {
        Category category = new Category(id, name, description, modifiedUserId);
        categoryRepository.updateCategory(category);
    }

    // Create a new category
    public void deleteCategory(int id) throws SQLException {
        categoryRepository.deleteCategory(id);
    }

}
