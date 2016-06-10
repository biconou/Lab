package org.github.biconou.lab;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class EmbeddedESTest {

  private static final String INDEX = "testindex";

  public static void main(String args[]) throws IOException {

    /* Client client = TransportClient.builder().build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getLoopbackAddress(), 9300));
    */

    Map<String,String> settings = new HashMap<>();
    settings.put("path.home","D:/elasticsearch-2.3.3");
    Node node = NodeBuilder.nodeBuilder().client(false).data(true).local(true).settings(Settings.builder().put(settings)).clusterName("elasticsearch").node();
    Client client = node.client();

    //index(client);

    search(client);
  }


  public static void search(Client client) {
    SearchResponse allHits = client.prepareSearch(INDEX)
      .setQuery(QueryBuilders.typeQuery("general"))
      .execute().actionGet();

    allHits.getHits().forEach(hit -> System.out.println(hit.getSourceAsString()));
  }


  /**
   *
   * @param client
   * @return
   */
  public static void index(Client client) {


    boolean indexExists = client.admin().indices().prepareExists(INDEX).execute().actionGet().isExists();
    if (indexExists) {
      client.admin().indices().prepareDelete(INDEX).execute().actionGet();
    }
    client.admin().indices().prepareCreate(INDEX).execute().actionGet();

    String json = "{" +
      "\"user\":\"kimchy\"," +
      "\"postDate\":\"2013-01-30\"," +
      "\"message\":\"trying out Elasticsearch\"" +
      "}";

    IndexResponse response = client.prepareIndex(INDEX, "general")
      .setSource(json)
      .get();

    json = "{" +
      "\"user\":\"Rémi\"," +
      "\"postDate\":\"2014-01-30\"," +
      "\"message\":\"Hello Hello again\"" +
      "}";

    response = client.prepareIndex(INDEX, "general")
      .setSource(json)
      .get();

    try {
      Thread.sleep(10000);
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
