package Controller.builders.charts;

import Model.Charts.BarChart;
import Model.Bills.BillsDistribution;

public class BillsDistributionBarChartBuilder {
    public BarChart build(BillsDistribution billsDistribution){
        BarChart barChart = new BarChart();
        addValues(barChart,billsDistribution);
        return barChart;
    }

    private void addValues(BarChart barChart, BillsDistribution billsDistribution){
        barChart.addBar("Sales",billsDistribution.getSalesAmount());
        barChart.addBar("Purchases", billsDistribution.getPurchasesAndServicesAmount());
        barChart.addBar("Investments", billsDistribution.getInvestmentsAmount());
        barChart.addBar("Salaries", billsDistribution.getSalariesAmount());
    }

}
