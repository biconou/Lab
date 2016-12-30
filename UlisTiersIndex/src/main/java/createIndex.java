import com.google.code.geocoder.AdvancedGeoCoder;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import freemarker.template.TemplateException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class createIndex {

    private static ElasticsearchHelper elasticsearchHelper = new ElasticsearchHelper();

    private static Geocoder geocoder;
    static  {
        HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        httpClient.getHostConfiguration().setProxy("ancy.proxy.corp.sopra", 8080);

        geocoder = new AdvancedGeoCoder(httpClient);
    }



    public static class LocalRowMapper<T extends Tiers> extends BeanPropertyRowMapper<T> {

        public LocalRowMapper(Class<T> mappedClass) {
            super(mappedClass);
        }

        @Override
        public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
            T mapped = super.mapRow(rs, rowNumber);

            // Geocodate
            String adressToGeocode = mapped.getAdresse();
            if (StringUtils.isNotBlank(adressToGeocode)) {
                GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(adressToGeocode).setLanguage("fr").getGeocoderRequest();
                try {
                    GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
                    if (geocoderResponse.getResults().size() > 0) {
                        GeocoderResult result = geocoderResponse.getResults().get(0);
                        mapped.setAdresseGeoPointLat(result.getGeometry().getLocation().getLat());
                        mapped.setAdresseGeoPointLng(result.getGeometry().getLocation().getLng());
                    } else {
                        logger.warn("Aucun geocodage pour l'adresse : " + adressToGeocode);
                    }
                } catch (IOException e) {
                    logger.error("Erreur lors du geocodage", e);
                }
            }

            elasticsearchHelper.indexObject(mapped,ElasticsearchHelper.ULIS_INDEX_NAME,false);
            return mapped;
        }
    }



    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            String qBailleurs = elasticsearchHelper.getQuery("bailleurs",null);
            UlisDBConnectionFactory.jdbcTemplate().query(qBailleurs, new LocalRowMapper(Bailleur.class));

            String qSignataires = elasticsearchHelper.getQuery("signataires",null);
            UlisDBConnectionFactory.jdbcTemplate().query(qSignataires, new LocalRowMapper(Signataire.class));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TemplateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


}
