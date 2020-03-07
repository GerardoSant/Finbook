package Reports;

public class AmortizationBarChartBuilder {

    public BarChart build(AmortizationReport report){
        BarChart barChart = new BarChart();
        barChart.addBar("Buildings", report.calculateAccumulatedAmortizationPercentage(report.getBuildings()));
        barChart.addBar("Office", report.calculateAccumulatedAmortizationPercentage(report.getOfficeEquipment()));
        barChart.addBar("Transport", report.calculateAccumulatedAmortizationPercentage(report.getTransportEquipment()));
        barChart.addBar("Computational", report.calculateAccumulatedAmortizationPercentage(report.getComputationalEquipment()));
        barChart.addBar("Models&Tools", report.calculateAccumulatedAmortizationPercentage(report.getModelsAndTools()));
        barChart.addBar("Telephone", report.calculateAccumulatedAmortizationPercentage(report.getTelephoneCommunications()));
        barChart.addBar("Satellite", report.calculateAccumulatedAmortizationPercentage(report.getSatelliteCommunications()));
        barChart.addBar("Machinery&Others", report.calculateAccumulatedAmortizationPercentage(report.getOtherMachineryAndEquipment()));
        return barChart;
    }
}
