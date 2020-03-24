package Controller.builders.charts;

import Model.Reports.AmortizationReport;
import Model.Charts.BarChart;
import Model.Reports.Report;

public class AmortizationBarChartBuilder implements ReportBarChartBuilder {

    @Override
    public BarChart build(Report report){
        BarChart barChart = new BarChart();
        AmortizationReport amortizationReport = (AmortizationReport) report;
        addValues(barChart, amortizationReport);
        return barChart;
    }

    private void addValues(BarChart barChart, AmortizationReport amortizationReport) {
        barChart.addBar("Buildings", amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getBuildings()));
        barChart.addBar("Office", amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getOfficeEquipment()));
        barChart.addBar("Transport", amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getTransportEquipment()));
        barChart.addBar("Computational", amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getComputationalEquipment()));
        barChart.addBar("Models&Tools", amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getModelsAndTools()));
        barChart.addBar("Telephone", amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getTelephoneCommunications()));
        barChart.addBar("Satellite", amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getSatelliteCommunications()));
        barChart.addBar("Machinery&Others", amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getOtherMachineryAndEquipment()));
    }


}
