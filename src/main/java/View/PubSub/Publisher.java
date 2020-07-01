package View.PubSub;

import io.finbook.TextGenerator;
import io.finbook.datahub.SensorTerminal;
import io.finbook.datahub.events.Invoice;
import io.intino.alexandria.terminal.JmsConnector;

import java.io.File;
import java.time.Instant;

public class Publisher {

    private static SensorTerminal terminal;
    private static final String terminalsPath = "C:/Users/raull/IdeaProjects/finbook/temp/terminals";

    static {
        JmsConnector connector = new JmsConnector(
                "failover:(tcp://localhost:63001)",
                "io.finbook.sensor",
                "io.finbook.sensor",
                "userenviroment-sensor",
                new File(terminalsPath)
        );
        connector.start();
        terminal = new SensorTerminal(connector);
    }

    public static void publish (String invoice){
        terminal.publish(new Invoice().ts(Instant.now()).ss("simulator-sensor").xml(TextGenerator.getBase64TextFrom(invoice)));
    }
}
