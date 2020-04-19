package Model.Reports;

import Controller.util.date.DateCalculator;
import Model.Bills.Bill;

import java.util.Date;
import java.util.List;

public class AmortizationReport extends Report{

    private List<Bill> buildings;
    private List<Bill> officeEquipment;
    private List<Bill> transportEquipment;
    private List<Bill> computationalEquipment;
    private List<Bill> modelsAndTools;
    private List<Bill> telephoneCommunications;
    private List<Bill> satelliteCommunications;
    private List<Bill> otherMachineryAndEquipment;

    public AmortizationReport(List<Bill> buildings, List<Bill> officeEquipment, List<Bill> transportEquipment, List<Bill> computationalEquipment,
                              List<Bill> modelsAndTools, List<Bill> telephoneCommunications, List<Bill> satelliteCommunications, List<Bill> otherMachineryAndEquipment,
                              Date periodStart,Date periodEnd, String RFC) {
        super(periodStart, periodEnd,RFC);
        this.buildings = buildings;
        this.officeEquipment = officeEquipment;
        this.transportEquipment = transportEquipment;
        this.computationalEquipment = computationalEquipment;
        this.modelsAndTools = modelsAndTools;
        this.telephoneCommunications = telephoneCommunications;
        this.satelliteCommunications = satelliteCommunications;
        this.otherMachineryAndEquipment = otherMachineryAndEquipment;
    }

    public double calculateAccumulatedAmortization(Bill bill){
        double accumulatedAmortization =bill.getSubtotal()*(AmortizationMap.amortizationMap.get(bill.getUse())/365)*(new DateCalculator().daysBetween(bill.getDate(), periodEnd));
        return accumulatedAmortization>bill.getSubtotal()? bill.getSubtotal() : accumulatedAmortization;
    }

    public double calculateAccumulatedAmortization(List<Bill> billList){
        return billList.stream().map(bill -> calculateAccumulatedAmortization(bill)).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    }

    public double calculateAccumulatedAmortizationPercentage(Bill bill){
        return calculateAccumulatedAmortization(bill)/ bill.getSubtotal() * 100;
    }

    public double calculatePeriodAmortization(List<Bill> billList){
        return billList.stream().map(bill -> calculatePeriodAmortization(bill)).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    }

    public double calculatePeriodAmortization(Bill bill){
        return bill.getSubtotal()*(AmortizationMap.amortizationMap.get(bill.getUse())/365)*(new DateCalculator().dayOfYear(periodEnd));
    }

    public double calculateAccumulatedAmortizationPercentage(List<Bill> billList){
        return (calculateAccumulatedAmortization(billList) / calculateBase(billList))*100;
    }


    public List<Bill> getBuildings() {
        return buildings;
    }

    public List<Bill> getOfficeEquipment() {
        return officeEquipment;
    }

    public List<Bill> getTransportEquipment() {
        return transportEquipment;
    }

    public List<Bill> getComputationalEquipment() {
        return computationalEquipment;
    }

    public List<Bill> getModelsAndTools() {
        return modelsAndTools;
    }

    public List<Bill> getTelephoneCommunications() {
        return telephoneCommunications;
    }

    public List<Bill> getSatelliteCommunications() {
        return satelliteCommunications;
    }

    public List<Bill> getOtherMachineryAndEquipment() {
        return otherMachineryAndEquipment;
    }
}
