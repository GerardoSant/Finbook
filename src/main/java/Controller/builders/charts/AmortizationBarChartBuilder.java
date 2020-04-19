package Controller.builders.charts;

import Model.Reports.AmortizationReport;
import Model.Charts.BarChart;
import Model.Reports.Report;

import static Controller.util.bill.BillConstants.Report.*;

public class AmortizationBarChartBuilder implements ReportBarChartBuilder {

    @Override
    public BarChart build(Report report){
        BarChart barChart = new BarChart();
        AmortizationReport amortizationReport = (AmortizationReport) report;
        addValues(barChart, amortizationReport);
        return barChart;
    }

    private void addValues(BarChart barChart, AmortizationReport amortizationReport) {
        barChart.addBar(BUILDINGS, amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getBuildings()));
        barChart.addBar(OFFICE, amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getOfficeEquipment()));
        barChart.addBar(TRANSPORT, amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getTransportEquipment()));
        barChart.addBar(COMPUTATIONAL, amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getComputationalEquipment()));
        barChart.addBar(MODELS_AND_TOOLS, amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getModelsAndTools()));
        barChart.addBar(TELEPHONE, amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getTelephoneCommunications()));
        barChart.addBar(SATELLITE, amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getSatelliteCommunications()));
        barChart.addBar(MACHINERY_AND_OTHERS, amortizationReport.calculateAccumulatedAmortizationPercentage(amortizationReport.getOtherMachineryAndEquipment()));
    }


}
