package View.builders.charts;

import Model.Charts.BarChart;
import Model.Bills.BillsDistribution;

import static Controller.util.bill.BillConstants.Type.*;

public class BillsDistributionBarChartBuilder {
    public BarChart build(BillsDistribution billsDistribution){
        BarChart barChart = new BarChart();
        addValues(barChart,billsDistribution);
        return barChart;
    }

    private void addValues(BarChart barChart, BillsDistribution billsDistribution){
        barChart.addBar(SALES,billsDistribution.getSalesAmount());
        barChart.addBar(PURCHASES, billsDistribution.getPurchasesAndServicesAmount());
        barChart.addBar(INVESTMENTS, billsDistribution.getInvestmentsAmount());
        barChart.addBar(SALARIES, billsDistribution.getSalariesAmount());
    }

}
