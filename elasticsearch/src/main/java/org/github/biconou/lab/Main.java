package org.github.biconou.lab;

import java.net.InetAddress;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * Created by remi on 24/04/2016.
 */
public class Main {

  public static void main(String args[]) {


    Client client = TransportClient.builder().build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getLoopbackAddress(), 9300));

    String INDEX = "test";

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
            "\"user\":\"cocula\"," +
            "\"postDate\":\"2014-01-30\"," +
            "\"message\":\"deuxième document\"" +
            "}";

    response = client.prepareIndex(INDEX, "general", "2")
            .setSource(json)
            .get();

       /* SearchResponse allHits = client.prepareSearch(INDEX)
                .addFields("title", "category")
                .setQuery(QueryBuilders.matchAllQuery())
                .execute().actionGet();
                */
  }
}
