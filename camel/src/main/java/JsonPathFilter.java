import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://camel.apache.org/pojo-producing.html
 */
public class JsonPathFilter {

    private static Logger log = LoggerFactory.getLogger(JsonPathFilter.class.getName());


    /**
     * A simple POJO representing a person identity.
     */
    private static class Person {
        private String firstName;
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    /**
     * Main method
     *
     * @param args arguments
     */
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:in").filter().jsonpath("$[?(@.firstName == 'Amadeus')]").to("log:JsonPathFilter?level=INFO");
            }
        });
        context.start();

        // Create two objects
        Person amadeus = new Person();
        amadeus.setFirstName("Amadeus");
        amadeus.setLastName("Mozart");
        Person bach = new Person();
        bach.setFirstName("Johan");
        bach.setLastName("Bach");

        ObjectMapper mapper = new ObjectMapper();
        ProducerTemplate template = context.createProducerTemplate();

        log.info("Message with firstName = 'Amadeus'; should be logged");
        template.sendBody("direct:in", mapper.writeValueAsString(amadeus));
        log.info("Message with firstName = 'Johan'; should NOT be logged");
        template.sendBody("direct:in", mapper.writeValueAsString(bach));
    }
}
