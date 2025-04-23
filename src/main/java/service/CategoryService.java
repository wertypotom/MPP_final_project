package service;

import entity.Category;
import repository.CategoryRepository;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository = new CategoryRepository();
    ;

    // Create a new category
    public void createCategory(Category category) throws SQLException {

        categoryRepository.createCategory(category);
    }

    // Read all categories
    public List<Category> listCategories() throws SQLException {
        return categoryRepository.listCategories();
    }

    // Create a new category
    public void updateCategory(Category category) throws SQLException {
        categoryRepository.updateCategory(category);
    }

    // Create a new category
    public void deleteCategory(int id) throws SQLException {
        categoryRepository.deleteCategory(id);
    }

}
