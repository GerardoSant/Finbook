package Model.Reports;

import Model.Bills.Bill;

import java.util.Date;
import java.util.List;

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


    public InvestmentsReport(Date periodStart, Date periodEnd, String RFC, List<Bill> buildings, double buildingsBase, List<Bill> officeEquipment, double officeEquipmentBase, List<Bill> transportEquipment, double transportEquipmentBase, List<Bill> computationalEquipment, double computationalEquipmentBase, List<Bill> modelsAndTools, double modelsAndToolsBase, List<Bill> telephoneCommunications, double telephoneCommunicationsBase, List<Bill> satelliteCommunications, double satelliteCommunicationsBase, List<Bill> otherMachineryAndEquipment, double otherMachineryAndEquipmentBase) {
        super(periodStart, periodEnd, RFC);
        this.buildings = buildings;
        this.buildingsBase = buildingsBase;
        this.officeEquipment = officeEquipment;
        this.officeEquipmentBase = officeEquipmentBase;
        this.transportEquipment = transportEquipment;
        this.transportEquipmentBase = transportEquipmentBase;
        this.computationalEquipment = computationalEquipment;
        this.computationalEquipmentBase = computationalEquipmentBase;
        this.modelsAndTools = modelsAndTools;
        this.modelsAndToolsBase = modelsAndToolsBase;
        this.telephoneCommunications = telephoneCommunications;
        this.telephoneCommunicationsBase = telephoneCommunicationsBase;
        this.satelliteCommunications = satelliteCommunications;
        this.satelliteCommunicationsBase = satelliteCommunicationsBase;
        this.otherMachineryAndEquipment = otherMachineryAndEquipment;
        this.otherMachineryAndEquipmentBase = otherMachineryAndEquipmentBase;
        this.base= buildingsBase+officeEquipmentBase+transportEquipmentBase+computationalEquipmentBase+modelsAndToolsBase+telephoneCommunicationsBase+satelliteCommunicationsBase+otherMachineryAndEquipmentBase;

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
