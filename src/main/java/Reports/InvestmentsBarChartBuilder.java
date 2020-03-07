package Reports;

public class InvestmentsBarChartBuilder implements ReportBarChartBuilder {
    @Override
    public BarChart build(Report report) {
        BarChart barChart = new BarChart();
        InvestmentsReport investmentsReport = (InvestmentsReport) report;
        addValues(barChart, investmentsReport);
        return barChart;
    }

    private void addValues(BarChart barChart, InvestmentsReport investmentsReport) {
        barChart.addBar("Buildings", investmentsReport.calculateBase(investmentsReport.getBuildings()));
        barChart.addBar("Office",investmentsReport.calculateBase(investmentsReport.getOfficeEquipment()));
        barChart.addBar("Transport", investmentsReport.calculateBase(investmentsReport.getTransportEquipment()));
        barChart.addBar("Computational", investmentsReport.calculateBase(investmentsReport.getComputationalEquipment()));
        barChart.addBar("Models&Tools", investmentsReport.calculateBase(investmentsReport.getModelsAndTools()));
        barChart.addBar("Telephone", investmentsReport.calculateBase(investmentsReport.getTelephoneCommunications()));
        barChart.addBar("Satellite", investmentsReport.calculateBase(investmentsReport.getSatelliteCommunications()));
        barChart.addBar("Machinery&Others", investmentsReport.calculateBase(investmentsReport.getOtherMachineryAndEquipment()));
    }


}
