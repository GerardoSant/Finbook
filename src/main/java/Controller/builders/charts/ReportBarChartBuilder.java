package Controller.builders.charts;

import Model.Charts.BarChart;
import Model.Reports.Report;

public interface ReportBarChartBuilder {

    public BarChart build(Report report);
}
