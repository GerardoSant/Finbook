package Reports;

import Bills.Bill;
import Bills.SQLiteBillsLoader;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class InvestmentsReport extends Report {
    private double base;
    private List<Bill> buildings;
    private double buildingsBase;
    private List<Bill> officeEquipment;
    private double officeEquipmentBase;
    private List<Bill> transportEquipment;
    private double transportEquipmentBase;
    private List<Bill> computationalEquipment;
    private double computationalEquipmentBase;
    private List<Bill> modelsAndTools;
    private double modelsAndToolsBase;
    private List<Bill> telephoneCommunications;
    private double telephoneCommunicationsBase;
    private List<Bill> satelliteCommunications;
    private double satelliteCommunicationsBase;
    private List<Bill> otherMachineryAndEquipment;
    private double otherMachineryAndEquipmentBase;



    public InvestmentsReport(List<Bill> billList, String RFC, Date periodStart, Date periodEnd) {
        super(periodStart, periodEnd,RFC);
        billList= billsFromPeriod(billList);
        calculateReportFields(billList);
    }

    private void calculateReportFields(List<Bill> billList) {
        this.buildings = filterByUseCode(billList, "I01");
        this.buildingsBase = calculateBase(this.buildings);
        this.officeEquipment = filterByUseCode(billList, "I02");
        this.officeEquipmentBase = calculateBase(this.officeEquipment);
        this.transportEquipment = filterByUseCode(billList, "I03");
        this.transportEquipmentBase = calculateBase(this.transportEquipment);
        this.computationalEquipment = filterByUseCode(billList, "I04");
        this.computationalEquipmentBase = calculateBase(this.computationalEquipment);
        this.modelsAndTools = filterByUseCode(billList, "I05");
        this.modelsAndToolsBase = calculateBase(this.modelsAndTools);
        this.telephoneCommunications = filterByUseCode(billList, "I06");
        this.telephoneCommunicationsBase = calculateBase(this.telephoneCommunications);
        this.satelliteCommunications = filterByUseCode(billList, "I07");
        this.satelliteCommunicationsBase = calculateBase(this.satelliteCommunications);
        this.otherMachineryAndEquipment = filterByUseCode(billList, "I08");
        this.otherMachineryAndEquipmentBase = calculateBase(this.otherMachineryAndEquipment);
        this.base= buildingsBase+officeEquipmentBase+transportEquipmentBase+computationalEquipmentBase+modelsAndToolsBase+telephoneCommunicationsBase+satelliteCommunicationsBase+otherMachineryAndEquipmentBase;
    }

    private List<Bill> filterByUseCode(List<Bill> billList, String useCode) {
        return billList.stream().filter(bill -> bill.getUse().equals(useCode) && bill.getReceiverRFC().equals(this.RFC)).collect(Collectors.toList());
    }


    public List<Bill> getBuildings() {
        return buildings;
    }

    public double getBuildingsBase() {
        return buildingsBase;
    }

    public List<Bill> getOfficeEquipment() {
        return officeEquipment;
    }

    public double getOfficeEquipmentBase() {
        return officeEquipmentBase;
    }

    public List<Bill> getTransportEquipment() {
        return transportEquipment;
    }

    public double getTransportEquipmentBase() {
        return transportEquipmentBase;
    }

    public List<Bill> getComputationalEquipment() {
        return computationalEquipment;
    }

    public double getComputationalEquipmentBase() {
        return computationalEquipmentBase;
    }

    public List<Bill> getModelsAndTools() {
        return modelsAndTools;
    }

    public double getModelsAndToolsBase() {
        return modelsAndToolsBase;
    }

    public List<Bill> getTelephoneCommunications() {
        return telephoneCommunications;
    }

    public double getTelephoneCommunicationsBase() {
        return telephoneCommunicationsBase;
    }

    public List<Bill> getSatelliteCommunications() {
        return satelliteCommunications;
    }

    public double getSatelliteCommunicationsBase() {
        return satelliteCommunicationsBase;
    }

    public List<Bill> getOtherMachineryAndEquipment() {
        return otherMachineryAndEquipment;
    }

    public double getOtherMachineryAndEquipmentBase() {
        return otherMachineryAndEquipmentBase;
    }

    public double getBase() {
        return base;
    }

}
