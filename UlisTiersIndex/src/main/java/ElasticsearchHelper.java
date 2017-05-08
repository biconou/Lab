/**
 * Paquet de définition
 **/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        freeMarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/ESQuery");
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
                    template = freeMarkerConfiguration.getTemplate(queryName + ".ftl");
                    queryTemplates.put(queryName, template);
                }
            }
        }
        StringWriter result = new StringWriter();

        if (vars != null) {
            vars.forEach((k, v) -> vars.put(k, escapeForJson(v)));
        }
        template.process(vars, result);

        return result.toString();
    }

    /**
     * @param source
     * @return
     */
    private String escapeForJson(String source) {
        if (source == null) {
            return "";
        } else {
            String target = source.replace("\\", "\\\\");
            target = target.replace("\"", "\\\"");
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


    /**
     * A convenient method to convert a search hit returned by a search query execution
     * to an object of the Subsonic domain.
     * In a search hit, the _source field is considered to be the Json representation
     * of an object and is then turned to a real java pojo using Jackson.
     *
     * @param hit  Search hit from which to extract an object
     * @param type The specified type of the object to extract
     * @return A new java object
     * @throws RuntimeException if anything goes wrong
     */
    private <T extends Object> T convertFromHit(SearchHit hit, Class<T> type) throws RuntimeException {
        T object = null;

        if (hit != null) {
            String hitSource = hit.getSourceAsString();
            try {
                object = getMapper().readValue(hitSource, type);
            } catch (IOException e) {
                throw new RuntimeException("Error while reading MediaFile object from index. ", e);
            }
        }
        return object;
    }


    public <T extends Object> T extractUnique(String queryName, Map<String, String> vars, Class<T> type) {
        String jsonQuery;
        try {
            jsonQuery = getQuery(queryName, vars);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }

        SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(indexNames())
                .setQuery(QueryBuilders.wrapperQuery(jsonQuery)).setVersion(true);
        SearchResponse response = searchRequestBuilder.execute().actionGet();

        long totalHits = response == null ? 0 : response.getHits().totalHits();
        if (totalHits == 0) {
            return null;
        } else if (totalHits > 1) {
            throw new RuntimeException("Document is not unique " + type.getName() + " " + vars);
        } else {
            return convertFromHit(response.getHits().getHits()[0], type);
        }
    }

    private String[] indexNames() {
        return new String[]{ULIS_INDEX_NAME};

    }


    /**
     * Extract objects from all indices of all music folders using a named query.
     *
     * @param queryName The name of the query to use. It refers to the name of a .ftl file that
     *                  contains the query expressed in json.
     * @param vars A map of parameters and their value to bind to the query (can be null if the
     *             query has no parameter.
     * @param type The exact type of object being extracted.
     * @param <T> The exact type of object being extracted.
     * @return A list of all extracted objects. may be empty.
     */
    public <T extends Object> List<T> extractObjects(String queryName, Map<String, String> vars, Class<T> type) {
        return extractObjects(queryName, vars, null, null, null, type);
    }


    /**
     * Extract objects from all indices of all music folders using a named query.
     *
     * @param queryName The name of the query to use. It refers to the name of a .ftl file that
     *                  contains the query expressed in json.
     * @param vars A map of parameters and their value to bind to the query (can be null if the
     *             query has no parameter.
     * @param from The offset index of the first object to extract from the query results.
     * @param size The number of objects to extract.
     * @param sortClause A sort clause to apply to the query.
     * @param type The exact type of object being extracted.
     * @param <T> The exact type of object being extracted.
     * @return A list of all extracted objects. may be empty. The size is equal to size or less.
     */
    public <T extends Object> List<T> extractObjects(String queryName, Map<String, String> vars,
                                                                     Integer from, Integer size,
                                                                     Map<String, SortOrder> sortClause, Class<T> type) {

        String jsonQuery;
        try {
            jsonQuery = getQuery(queryName, vars);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }

        return extractObjects(jsonQuery, from, size, sortClause, type);
    }


    /**
     * Extract objects from indices of certain music folders using a json query.
     *
     * @param jsonSearch A query expressed in json.
     * @param from The offset index of the first object to extract from the query results.
     * @param size The number of objects to extract.
     * @param sortClause A sort clause to apply to the query.
     * @param type The exact type of object being extracted.
     * @param <T> The exact type of object being extracted.
     * @return A list of all extracted objects. may be empty. The size is equal to size or less.
     */
    public <T extends Object> List<T> extractObjects(String jsonSearch, Integer from, Integer size,
                                                                     Map<String, SortOrder> sortClause,
                                                                     Class<T> type) {
        SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(indexNames())
                .setQuery(QueryBuilders.wrapperQuery(jsonSearch)).setVersion(true);
        return extractObjects(searchRequestBuilder, from, size, sortClause, type);
    }

    /**
     * Extract objects from indices of certain music folders using a searchRequestBuilder.
     *
     * @param searchRequestBuilder A searchRequestBuilder that represents a query.
     * @param from The offset index of the first object to extract from the query results.
     * @param size The number of objects to extract.
     * @param sortClause A sort clause to apply to the query.
     * @param type The exact type of object being extracted.
     * @param <T> The exact type of object being extracted.
     * @return A list of all extracted objects. may be empty. The size is equal to size or less.
     */
    public <T extends Object> List<T> extractObjects(SearchRequestBuilder searchRequestBuilder,
                                                                     Integer from, Integer size,
                                                                     Map<String, SortOrder> sortClause, Class<T> type) {
        if (from != null) {
            searchRequestBuilder.setFrom(from);
        }
        if (size != null) {
            searchRequestBuilder.setSize(size);
        }
        if (sortClause != null) {
            sortClause.keySet().forEach(sortField -> searchRequestBuilder.addSort(sortField, sortClause.get(sortField)));
        }
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        List<T> returnedSongs = Arrays.stream(response.getHits().getHits()).map(hit -> convertFromHit(hit, type)).collect(Collectors.toList());
        return returnedSongs;
    }



}
 
