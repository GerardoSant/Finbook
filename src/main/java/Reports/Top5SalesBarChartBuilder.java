package Reports;

import Bills.Bill;

public class Top5SalesBarChartBuilder implements BarChartBuilder{
    @Override
    public BarChart build(Object object) {
        BarChart barChart = new BarChart();
        Top5Sales sales = (Top5Sales) object;
        addValues(barChart,sales);
        return barChart;
    }

    private void addValues(BarChart barChart, Top5Sales sales) {
        for (Bill bill : sales.getTop5()) {
            barChart.addBar(bill.getConcept(), bill.getTotal());
        }
    }
}
