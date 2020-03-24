package Model.Bills;

import java.util.Date;

public class Bill {
    private String UUID;
    private Date date;
    private int PC;
    private String type;
    private String use;
    private String concept;
    private String issuerName;
    private String issuerRFC;
    private String receiverName;
    private String receiverRFC;
    private double subtotal;
    private double taxRate;
    private double total;
    private String currency;
    private String xmlFile;

    public Bill(String UUID, Date date, int PC, String type, String use, String concept, String issuerName, String issuerRFC, String receiverName, String receiverRFC,double subtotal, double taxRate,
                double total, String currency, String xmlFile) {
        this.UUID = UUID;
        this.date = date;
        this.PC = PC;
        this.type = type;
        this.use=use;
        this.concept=concept;
        this.issuerName = issuerName;
        this.issuerRFC = issuerRFC;
        this.receiverName = receiverName;
        this.receiverRFC = receiverRFC;
        this.subtotal= subtotal;
        this.taxRate= taxRate;
        this.total = total;
        this.currency = currency;
        this.xmlFile=xmlFile;
    }

    public String getXmlFile() {
        return xmlFile;
    }

    public String getUUID() {
        return UUID;
    }

    public Date getDate() {
        return date;
    }

    public int getPC() {
        return PC;
    }

    public String getType() {
        return type;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public String getIssuerRFC() {
        return issuerRFC;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverRFC() {
        return receiverRFC;
    }

    public double getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public String getUse() {
        return use;
    }

    public String getConcept() {
        return concept;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTaxRate() {
        return taxRate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "UUID='" + UUID + '\'' +
                ", date=" + date +
                ", PC=" + PC +
                ", type='" + type + '\'' +
                ", use='" + use + '\'' +
                ", concept='" + concept + '\'' +
                ", issuerName='" + issuerName + '\'' +
                ", issuerRFC='" + issuerRFC + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverRFC='" + receiverRFC + '\'' +
                ", subtotal=" + subtotal +
                ", taxRate=" + taxRate +
                ", total=" + total +
                ", currency='" + currency + '\'' +
                ", xmlFile='" + xmlFile + '\'' +
                '}';
    }

}
