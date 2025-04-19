package view;

import entity.Category;
import entity.report.ExpenseReport;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import service.CategoryService;
import service.ReportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ReportPanel extends JPanel {
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private JDatePickerImpl fromDatePicker;
    private JDatePickerImpl toDatePicker;
    private JComboBox<Category> categoryBox;
    private JLabel totalAmountLabel;

    private final ReportService reportService = new ReportService();

    public ReportPanel() {
        setLayout(new BorderLayout());

        // Date Pickers
        UtilDateModel fromModel = new UtilDateModel();
        UtilDateModel toModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        fromDatePicker = new JDatePickerImpl(new JDatePanelImpl(fromModel, p), new DateLabelFormatter());
        toDatePicker = new JDatePickerImpl(new JDatePanelImpl(toModel, p), new DateLabelFormatter());

        // Top Panel (Filters)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("From:"));
        topPanel.add(fromDatePicker);
        topPanel.add(new JLabel("To:"));
        topPanel.add(toDatePicker);

        categoryBox = new JComboBox<>();
        categoryBox.addItem(new Category(0, "All", "All"));
        CategoryService categoryService = new CategoryService();
        try {
            List<Category> categories = categoryService.listCategories();
            for (Category category : categories) {
                categoryBox.addItem(category); // Add full objects
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load categories:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoryBox);

        JButton previewBtn = new JButton("Preview");
        previewBtn.addActionListener(e -> loadReport());
        topPanel.add(previewBtn);

        JButton downloadBtn = new JButton("Download");
        topPanel.add(downloadBtn); // optional functionality

        add(topPanel, BorderLayout.NORTH);

        // Table Setup
        String[] columns = {"Date", "Category", "Amount", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        expenseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        add(scrollPane, BorderLayout.CENTER);

        totalAmountLabel = new JLabel("Total Amount: 0.00");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalAmountLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(totalAmountLabel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadReport() {
        try {
            // Extract date values
            Date from = (Date) fromDatePicker.getModel().getValue();
            Date to = (Date) toDatePicker.getModel().getValue();

            if (from == null || to == null) {
                JOptionPane.showMessageDialog(this, "Please select both from and to dates.");
                return;
            }

            // Format for DB
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fromDate = sdf.format(from);
            String toDate = sdf.format(to);

            Category selectedCategory = (Category) categoryBox.getSelectedItem();
            int categoryId = selectedCategory != null ? selectedCategory.getCategoryId() : 0;

            // Fetch from service
            List<ExpenseReport> data = reportService.getExpenseReport(fromDate, toDate, categoryId);
            double totalAmount = 0.0;
            // Populate table
            tableModel.setRowCount(0);
            for (ExpenseReport d : data) {
                tableModel.addRow(new Object[]{
                        d.getDate(),
                        d.getCategoryName(),
                        d.getAmount(),
                        d.getDescription()
                });
                totalAmount += d.getAmount();
            }
            totalAmountLabel.setText(String.format("Total Amount: %.2f", totalAmount));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load report:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
