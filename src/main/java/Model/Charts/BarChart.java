package Model.Charts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BarChart implements Iterable{

    private Map<String, Double> barChart;

    public BarChart() {
        this.barChart = new HashMap<>();
    }

    public void addBar(String name, double value){
        barChart.put(name, value);
    }

    public Object getCount(Object name) {
        return barChart.get(name);
    }

    @Override
    public Iterator iterator() {
        return barChart.keySet().iterator();
    }
}
