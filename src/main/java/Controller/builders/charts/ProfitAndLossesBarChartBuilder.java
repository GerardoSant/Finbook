package Controller.builders.charts;

import Model.Charts.BarChart;
import Model.Reports.ProfitAndLossesReport;
import Model.Reports.Report;

import static Controller.util.bill.BillConstants.Report.EXPENSES;
import static Controller.util.bill.BillConstants.Report.INCOMES;

public class ProfitAndLossesBarChartBuilder implements ReportBarChartBuilder {
    @Override
    public BarChart build(Report report) {
        BarChart barChart = new BarChart();
        ProfitAndLossesReport profitAndLossesReport = (ProfitAndLossesReport) report;
        addValues(barChart, profitAndLossesReport);
        return barChart;
    }

    private void addValues(BarChart barChart, ProfitAndLossesReport profitAndLossesReport) {
        barChart.addBar(INCOMES, profitAndLossesReport.getSalesAndIncomesBase());
        barChart.addBar(EXPENSES, profitAndLossesReport.getPurchasesAndExpensesBase());
    }
}
