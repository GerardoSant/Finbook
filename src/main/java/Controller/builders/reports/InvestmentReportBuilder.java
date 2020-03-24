package Controller.builders.reports;

import Model.Bills.Bill;
import Model.Reports.InvestmentsReport;
import Controller.util.BillCalculator;
import Controller.util.BillFilter;

import java.util.Date;
import java.util.List;

public class InvestmentReportBuilder extends ReportBuilder {


    public InvestmentReportBuilder(List<Bill> billList, String RFC, Date periodStart, Date periodEnd) {
        super(billList, RFC, periodStart, periodEnd);
    }

    public InvestmentReportBuilder(List<Bill> billList, String RFC) {
        super(billList, RFC);
    }

    public InvestmentsReport buildReport() {
        billList=generateBillsFromPeriod(billList);
        return new InvestmentsReport(periodStart, periodEnd, RFC,
                generateInvestmentsByUseCode("I01"), BillCalculator.calculateBase(generateInvestmentsByUseCode("I01")),generateInvestmentsByUseCode("I02"), BillCalculator.calculateBase(generateInvestmentsByUseCode("I02")),
                generateInvestmentsByUseCode("I03"),BillCalculator.calculateBase(generateInvestmentsByUseCode("I03")), generateInvestmentsByUseCode("I04"),BillCalculator.calculateBase(generateInvestmentsByUseCode("I04")),
                generateInvestmentsByUseCode("I05"),BillCalculator.calculateBase(generateInvestmentsByUseCode("I05")), generateInvestmentsByUseCode("I06"),BillCalculator.calculateBase(generateInvestmentsByUseCode("I06")),
                generateInvestmentsByUseCode("I07"),BillCalculator.calculateBase(generateInvestmentsByUseCode("I07")),
                generateInvestmentsByUseCode("I08"),BillCalculator.calculateBase(generateInvestmentsByUseCode("I08")));
    }

    private List<Bill> generateInvestmentsByUseCode(String useCode) {
        return BillFilter.filterByInvestments(billList, useCode,RFC);
    }
}
