package Controller.builders.charts;

import Model.Charts.BarChart;
import Model.Reports.ProfitAndLossesReport;
import Model.Reports.Report;

public class ProfitAndLossesBarChartBuilder implements ReportBarChartBuilder {
    @Override
    public BarChart build(Report report) {
        BarChart barChart = new BarChart();
        ProfitAndLossesReport profitAndLossesReport = (ProfitAndLossesReport) report;
        addValues(barChart, profitAndLossesReport);
        return barChart;
    }

    private void addValues(BarChart barChart, ProfitAndLossesReport profitAndLossesReport) {
        barChart.addBar("Incomes", profitAndLossesReport.getSalesAndIncomesBase());
        barChart.addBar("Expenses", profitAndLossesReport.getPurchasesAndExpensesBase());
    }
}
