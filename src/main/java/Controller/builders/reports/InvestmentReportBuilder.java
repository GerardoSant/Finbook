package Controller.builders.reports;

import Model.Bills.Bill;
import Model.Reports.InvestmentsReport;
import Controller.util.bill.BillCalculator;
import Controller.util.bill.BillFilter;

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
        return generateInvestmentReport(generateInvestmentsByUseCode("I01"),generateInvestmentsByUseCode("I02"),generateInvestmentsByUseCode("I03"),generateInvestmentsByUseCode("I04"),generateInvestmentsByUseCode("I05"),
                generateInvestmentsByUseCode("I06"),generateInvestmentsByUseCode("I07"),generateInvestmentsByUseCode("I08"));
    }

    private InvestmentsReport generateInvestmentReport(List<Bill> buildings,List<Bill> officeEquipment, List<Bill> transportEquipment, List<Bill> computationalEquipment, List<Bill> modelsAndTools,
    List<Bill> telephoneCommunications,List<Bill> satelliteCommunications, List<Bill> otherMachineryAndEquipment){
        return new InvestmentsReport(periodStart,periodEnd,RFC,buildings,calculateBase(buildings),officeEquipment, calculateBase(officeEquipment),transportEquipment, calculateBase(transportEquipment),
                computationalEquipment, calculateBase(computationalEquipment), modelsAndTools, calculateBase(modelsAndTools),telephoneCommunications, calculateBase(telephoneCommunications),
                satelliteCommunications, calculateBase(satelliteCommunications),otherMachineryAndEquipment, calculateBase(otherMachineryAndEquipment));
    }

    private double calculateBase(List<Bill> bills) {
        return BillCalculator.calculateBase(bills);
    }

    private List<Bill> generateInvestmentsByUseCode(String useCode) {
        return BillFilter.filterByInvestments(billList, useCode,RFC);
    }
}
