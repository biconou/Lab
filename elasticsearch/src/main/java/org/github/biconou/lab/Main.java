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
public class Main {

  public static void main(String args[]) throws InterruptedException {


    Client client = TransportClient.builder().build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getLoopbackAddress(), 9300));

    String INDEX = "testindex";

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

    IndexResponse response = client.prepareIndex(INDEX, "general", "1")
      .setSource(json)
      .get();

    json = "{" +
      "\"user\":\"Rémi\"," +
      "\"postDate\":\"2014-01-30\"," +
      "\"message\":\"nom cocula\"" +
      "}";

    response = client.prepareIndex(INDEX, "general2", "2")
      .setSource(json)
      .get();

    Thread.sleep(10000);

    SearchResponse allHits = client.prepareSearch(INDEX)
      .setQuery(QueryBuilders.typeQuery("general"))
      .execute().actionGet();



    System.out.println(allHits.getHits().totalHits());

    allHits.getHits().forEach(hit -> System.out.println(hit.getId()));
  }
}
