package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.util.Properties;
import java.util.logging.Logger;

public class ReportPanel extends JPanel {
    private JTable expenseTable;
    private DefaultTableModel tableModel;

    public ReportPanel() {
        setLayout(new BorderLayout());

        UtilDateModel fromModel = new UtilDateModel();
        UtilDateModel toModel = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl fromDatePanel = new JDatePanelImpl(fromModel, p);
        JDatePanelImpl toDatePanel = new JDatePanelImpl(toModel, p);

        JDatePickerImpl fromDatePicker = new JDatePickerImpl(fromDatePanel, new DateLabelFormatter());
        JDatePickerImpl toDatePicker = new JDatePickerImpl(toDatePanel, new DateLabelFormatter());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("From:"));
        topPanel.add(fromDatePicker);

        topPanel.add(new JLabel("To:"));
        topPanel.add(toDatePicker);

        String[] options = {"All", "Food", "Transport", "Entertainment"};
        JComboBox<String> categoryBox = new JComboBox<>(options);
        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoryBox);

        JButton previewBtn = new JButton("Preview");
        JButton downloadBtn = new JButton("Download");

        topPanel.add(previewBtn);
        topPanel.add(downloadBtn);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Date", "Category", "Amount", "Description"};
        Object[][] data = {
                {"2023-01-01", "Food", "$20", "Lunch"},
                {"2023-01-02", "Transport", "$10", "Bus fare"},
        };

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initTable() {
        String[] columns = {"ID", "Name", "Description", "Amount", "Category", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        expenseTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Preview");
        addButton.addActionListener(e -> {
            //JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            //new AddExpenseDialog(parentFrame).setVisible(true);
            //Logger.getLogger("a").info("Test");
        });
        toolbar.add(addButton);
        add(toolbar, BorderLayout.SOUTH);
    }
}
