import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class WeatherWebService {

    private static Logger log = LoggerFactory.getLogger(WeatherWebService.class.getName());

    /**
     * Invoque web service described here : http://www.webservicex.com/globalweather.asmx?op=GetCitiesByCountry
     *
     * @param args arguments
     */
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:in")
                .to("spring-ws:http://www.webservicex.com/globalweather.asmx?soapAction=http://www.webserviceX.NET/GetCitiesByCountry")
                .log("${body}");
            }
        });
        context.start();

        ProducerTemplate template = context.createProducerTemplate();

        String body = "<GetCitiesByCountry xmlns=\"http://www.webserviceX.NET\"><CountryName>France</CountryName></GetCitiesByCountry>";
        template.sendBody("direct:in", body);
    }
}
