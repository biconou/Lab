package org.github.biconou.lab;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by remi on 24/04/2016.
 */
public class SetMapping {

  public static void main(String args[]) throws IOException {


    Client client = TransportClient.builder().build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getLoopbackAddress(), 9300));

    String INDEX = "test";

    boolean indexExists = client.admin().indices().prepareExists(INDEX).execute().actionGet().isExists();
    if (indexExists) {
      client.admin().indices().prepareDelete(INDEX).execute().actionGet();
    }
    client.admin().indices()
            .prepareCreate(INDEX)
            .addMapping("DIRECTORY","path","type=string,index=not_analyzed")
            .execute().actionGet();

  }
}
