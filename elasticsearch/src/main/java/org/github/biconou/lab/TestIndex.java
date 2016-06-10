package org.github.biconou.lab;

import java.net.InetAddress;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by remi on 24/04/2016.
 */
public class TestIndex {

  public static String INDEX = "testindex";


  public static void main(String args[]) throws InterruptedException {


    Client client = TransportClient.builder().build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getLoopbackAddress(), 9300));
    index(client);
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
  }
}
