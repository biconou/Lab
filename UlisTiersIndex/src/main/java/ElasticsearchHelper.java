/**
 * Paquet de définition
 **/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 */
public class ElasticsearchHelper {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ElasticsearchHelper.class);

    public static String ULIS_INDEX_NAME = "ulis";
    public static final String ULIS_TYPE = "ULIS";

    private Client elasticSearchClient = null;

    private ObjectMapper mapper = new ObjectMapper();
    private Map<String, Template> queryTemplates = new HashMap<>();
    private Configuration freeMarkerConfiguration = null;

    public ElasticsearchHelper() {
        freeMarkerConfiguration = new Configuration(Configuration.VERSION_2_3_23);
        freeMarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/query");
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    private Client obtainESClient() {

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getLoopbackAddress(), 9300));
        return client;

    }

    public Client getClient() {
        if (elasticSearchClient == null) {
            synchronized (this) {
                if (elasticSearchClient == null) {
                    elasticSearchClient = obtainESClient();

                    boolean indexExists = elasticSearchClient.admin().indices().prepareExists(ULIS_INDEX_NAME)
                            .execute().actionGet().isExists();
                    if (indexExists) {
                        elasticSearchClient.admin().indices().prepareDelete(ULIS_INDEX_NAME).execute().actionGet();
                    }
                    elasticSearchClient.admin().indices()
                            .prepareCreate(ULIS_INDEX_NAME)
                            .addMapping(ULIS_TYPE,
                                    "codeTiers", "type=string,index=not_analyzed",
                                    "qualiteTiers", "type=string,index=not_analyzed",
                                    "role", "type=string,index=not_analyzed",
                                    "adresseGeoPoint","type=geo_point")
                            .execute().actionGet();

                }
            }
        }
        return elasticSearchClient;
    }

    /**
     * @param queryName
     * @param vars
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String getQuery(String queryName, Map<String, String> vars) throws IOException, TemplateException {

        Template template = queryTemplates.get(queryName);
        if (template == null) {
            synchronized (queryTemplates) {
                template = queryTemplates.get(queryName);
                if (template == null) {
                    template = freeMarkerConfiguration.getTemplate(queryName + ".sql");
                    queryTemplates.put(queryName, template);
                }
            }
        }
        StringWriter result = new StringWriter();

        if (vars != null) {
            vars.forEach((k, v) -> vars.put(k, escapeForSQL(v)));
        }
        template.process(vars, result);

        return result.toString();
    }

    /**
     * @param source
     * @return
     */
    private String escapeForSQL(String source) {
        if (source == null) {
            return "";
        } else {
            String target = source.replace("'", "''");
            return target;
        }
    }


    public void indexObject(Object obj, String indexName, boolean synchrone) {
        log.debug("Object does not exist -> create");
        try {
            // Convert the object to a json string representation.
            String mediaFileAsJson = getMapper().writeValueAsString(obj);
            IndexResponse indexResponse = getClient().prepareIndex(
                    indexName,
                    ULIS_TYPE)
                    .setSource(mediaFileAsJson).setVersionType(VersionType.INTERNAL).get();
            if (synchrone) {
                long l = 0;
                while (l == 0) {
                    l = getClient().prepareSearch(indexName)
                            .setQuery(QueryBuilders.idsQuery().addIds(indexResponse.getId())).execute().actionGet().getHits().totalHits();
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error trying indexing object " + e);
        }
    }


}
 
