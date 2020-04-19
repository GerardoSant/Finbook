package Controller.builders.charts;

import Model.Charts.BarChart;
import Model.Reports.InvestmentsReport;
import Model.Reports.Report;

import static Controller.util.bill.BillConstants.Report.*;

public class InvestmentsBarChartBuilder implements ReportBarChartBuilder {
    @Override
    public BarChart build(Report report) {
        BarChart barChart = new BarChart();
        InvestmentsReport investmentsReport = (InvestmentsReport) report;
        addValues(barChart, investmentsReport);
        return barChart;
    }

    private void addValues(BarChart barChart, InvestmentsReport investmentsReport) {
        barChart.addBar(BUILDINGS, investmentsReport.calculateBase(investmentsReport.getBuildings()));
        barChart.addBar(OFFICE,investmentsReport.calculateBase(investmentsReport.getOfficeEquipment()));
        barChart.addBar(TRANSPORT, investmentsReport.calculateBase(investmentsReport.getTransportEquipment()));
        barChart.addBar(COMPUTATIONAL, investmentsReport.calculateBase(investmentsReport.getComputationalEquipment()));
        barChart.addBar(MODELS_AND_TOOLS, investmentsReport.calculateBase(investmentsReport.getModelsAndTools()));
        barChart.addBar(TELEPHONE, investmentsReport.calculateBase(investmentsReport.getTelephoneCommunications()));
        barChart.addBar(SATELLITE, investmentsReport.calculateBase(investmentsReport.getSatelliteCommunications()));
        barChart.addBar(MACHINERY_AND_OTHERS, investmentsReport.calculateBase(investmentsReport.getOtherMachineryAndEquipment()));
    }


}
