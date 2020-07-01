package View.PubSub;

import Model.Bills.Bill;
import io.finbook.datahub.DatamartTerminal;
import io.finbook.datahub.events.ProcessedInvoice;
import io.intino.alexandria.terminal.JmsConnector;

import java.io.File;
import java.util.Date;

public class Subscriber {

    private static final String terminalsPath = "C:/Users/raull/IdeaProjects/finbook/temp/terminals";

    static {
        JmsConnector connector = new JmsConnector(
                "failover:(tcp://localhost:63001)", //BrokerURL
                "io.finbook.datamart",
                "io.finbook.datamart",
                "userenviroment-datamart",
                new File(terminalsPath)
        );
        connector.start();
        DatamartTerminal dt = new DatamartTerminal(connector);
        dt.subscribe((e) -> processInvoice(e), "userenviroment-datamart");
    }

    public static void processInvoice(ProcessedInvoice e){
        Bill bill = new Bill(e.uUID(), Date.from(e.date()), e.pC(), e.type(), e.use(), e.concept(), e.issuerName(), e.issuerRFC(), e.receiverName(), e.receiverRFC(), e.subtotal(), e.taxRate(), e.total(), e.currency(),
                e.xml());
    }






}
