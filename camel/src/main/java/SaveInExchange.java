import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://camel.apache.org/pojo-producing.html
 */
public class SaveInExchange {

    private static Logger log = LoggerFactory.getLogger(SaveInExchange.class.getName());


    /**
     * Main method
     *
     * @param args arguments
     */
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:in").setProperty("myProperty", constant("totoLiToto")).log(LoggingLevel.INFO, "${exchangeProperty.myProperty}");
            }
        });
        context.start();


        ProducerTemplate template = context.createProducerTemplate();

        template.sendBody("direct:in", "test message");
    }
}
