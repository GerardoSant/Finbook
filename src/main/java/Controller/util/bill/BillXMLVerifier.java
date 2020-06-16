package Controller.util.bill;

public class BillXMLVerifier {

    private final String[] billFields = {"Concept", "Currency", "Date", "Location", "SubTotal", "TaxRate", "Total", "Type", "UUID", "Use", "IssuerName", "IssuerRFC", "ReceiverName","ReceiverRFC"};

    public boolean verify(String billXML){
        for (String field : billFields){
            if (!hasXMLField(billXML, field)){
                return false;
            }
        }
        return true;
    }

    private boolean hasXMLField(String billXML, String field) {
        try{
            if (fieldIndex(billXML,field) > -1 && billFieldLength(billXML, field) >0) return true;
            return false;
        } catch (Exception e){
            return false;
        }
    }

    private int billFieldLength(String billXML, String field) {
        return billXML.substring(fieldInitialIndex(billXML, field), billXML.indexOf("\"", fieldInitialIndex(billXML, field))).toUpperCase().length();
    }

    private int fieldInitialIndex(String billXML, String field) {
        return fieldIndex(billXML, field) + field.length() + 2;
    }

    private int fieldIndex(String billXML, String field) {
        return billXML.indexOf(field + "=\"");
    }
}
