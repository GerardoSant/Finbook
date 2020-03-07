package Reports;

public class ProfitAndLossesBarChartBuilder implements ReportBarChartBuilder{
    @Override
    public BarChart build(Report report) {
        BarChart barChart = new BarChart();
        ProfitAndLossesReport profitAndLossesReport = (ProfitAndLossesReport) report;
        addValues(barChart, profitAndLossesReport);
        return barChart;
    }

    private void addValues(BarChart barChart, ProfitAndLossesReport profitAndLossesReport) {
        barChart.addBar("Sales and Incomes", profitAndLossesReport.getSalesAndIncomesBase());
        barChart.addBar("Purchases and Expenses", profitAndLossesReport.getPurchasesAndExpensesBase());
    }
}
