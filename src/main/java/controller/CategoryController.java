package controller;
import service.CategoryService;
import view.AddCategoryDialog;

import javax.swing.*;
import java.sql.SQLException;

public class CategoryController {
    private AddCategoryDialog addCategoryDialog;
    private final CategoryService categoryService;
        public CategoryController(AddCategoryDialog dialog, CategoryService service) {
            this.addCategoryDialog = dialog;
            this.categoryService = service;

            this.addCategoryDialog.addSubmitListener(e -> {
                String name = dialog.getCategoryName();
                String description = dialog.getCategoryDescription();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Category name is required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    categoryService.createCategory(name,description);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(dialog, "Category added successfully!");
                dialog.dispose();
            });
        }


}
