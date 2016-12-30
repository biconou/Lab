/**
 * Paquet de définition
 **/

import com.google.code.geocoder.AdvancedGeoCoder;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

import java.io.IOException;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 */
public class GeoCodeTest {

    public static void main(String[] args) {

        HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        httpClient.getHostConfiguration().setProxy("lyon.proxy.corp.sopra", 8080);
        //httpClient.getHostConfiguration().setParams();

        Geocoder geocoder = new AdvancedGeoCoder(httpClient);
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress("66 rue Etienne Richerand 69003 LYON").setLanguage("fr").getGeocoderRequest();

        try {
            GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
            System.out.println(geocoderResponse.getStatus());
            if (geocoderResponse.getResults().size() > 0) {
                GeocoderResult result = geocoderResponse.getResults().get(0);
                result.getGeometry().getLocation().getLat();
                result.getGeometry().getLocation().getLng();
            }

        } catch (IOException e) {
// TODO: Le traitement de cette exception a été générée automatiquement. Merci de vérifier
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
 
