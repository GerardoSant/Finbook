package Reports;

import Bills.Bill;
import util.PeriodFinder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AmortizationReportGenerator {

    private List<Bill> billList;
    private String RFC;
    private Date periodStart;
    private Date periodEnd;

    public AmortizationReportGenerator(List<Bill> billList, String RFC, Date periodStart, Date periodEnd) {
        this.billList = billList;
        this.RFC = RFC;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public AmortizationReportGenerator(List<Bill> billList, String RFC) {
        this.billList = billList;
        this.RFC = RFC;
        this.periodStart= new PeriodFinder(billList).findPeriodStart();
        this.periodEnd= new PeriodFinder(billList).findPeriodEnd();
    }

    public AmortizationReport generateReport(){
        billList= generateBillsFromPeriod(billList);
        return new AmortizationReport(generateInvestmentsByUseCode("I01"),generateInvestmentsByUseCode("I02"),generateInvestmentsByUseCode("I03"),
                generateInvestmentsByUseCode("I04"),generateInvestmentsByUseCode("I05"),generateInvestmentsByUseCode("I06"),generateInvestmentsByUseCode("I07"),
                generateInvestmentsByUseCode("I08"), periodStart, periodEnd, RFC);
    }

    private List<Bill> generateInvestmentsByUseCode(String useCode) {
        return BillFilter.filterByInvestments(billList, useCode,RFC);
    }


    private List<Bill> generateBillsFromPeriod(List<Bill> billList){
        return BillFilter.filterByPeriod(billList, periodStart, periodEnd);
    }


}
