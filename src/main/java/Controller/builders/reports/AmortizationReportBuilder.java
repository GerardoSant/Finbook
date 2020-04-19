package Controller.builders.reports;

import Model.Reports.AmortizationReport;
import Model.Bills.Bill;
import Controller.util.bill.BillFilter;

import java.util.Date;
import java.util.List;


public class AmortizationReportBuilder extends ReportBuilder {


    public AmortizationReportBuilder(List<Bill> billList, String RFC, Date periodStart, Date periodEnd) {
        super(billList, RFC, periodStart, periodEnd);
    }

    public AmortizationReportBuilder(List<Bill> billList, String RFC) {
        super(billList, RFC);
    }

    public AmortizationReport buildReport(){
        billList= generateBillsFromPeriod(billList);
        return new AmortizationReport(generateInvestmentsByUseCode("I01"),generateInvestmentsByUseCode("I02"),generateInvestmentsByUseCode("I03"),
                generateInvestmentsByUseCode("I04"),generateInvestmentsByUseCode("I05"),generateInvestmentsByUseCode("I06"),generateInvestmentsByUseCode("I07"),
                generateInvestmentsByUseCode("I08"), periodStart, periodEnd, RFC);
    }

    private List<Bill> generateInvestmentsByUseCode(String useCode) {
        return BillFilter.filterByInvestments(billList, useCode,RFC);
    }



}
