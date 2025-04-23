package service;

import entity.Category;
import entity.report.ExpenseReport;
import repository.CategoryRepository;
import repository.ReportRepository;

import java.sql.SQLException;
import java.util.List;

public class ReportService {
    private final ReportRepository reportRepository = new ReportRepository();

    public List<ExpenseReport> getExpenseReport(int userId, String fromDate, String toDate, int catId) throws SQLException {
        return reportRepository.getExpenseReport(userId, fromDate, toDate, catId);
    }

}
