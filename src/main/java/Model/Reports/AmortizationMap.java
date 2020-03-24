package Model.Reports;

import java.util.HashMap;
import java.util.Map;

public class AmortizationMap {

    public static HashMap<String, Double> amortizationMap;
    static {
        amortizationMap= new HashMap<>();
        amortizationMap.put("I01",0.02);
        amortizationMap.put("I02",0.1);
        amortizationMap.put("I03",0.1);
        amortizationMap.put("I04",0.25);
        amortizationMap.put("I05",0.12);
        amortizationMap.put("I06",0.1);
        amortizationMap.put("I07",0.1);
        amortizationMap.put("I08",0.12);
    }

}
