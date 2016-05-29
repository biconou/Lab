package org.github.biconou.lab;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;

/**
 * Created by remi on 24/04/2016.
 */
public class Search {

  public static void main(String args[]) {


    Client client = TransportClient.builder().build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getLoopbackAddress(), 9300));

    String INDEX = "subsonic_media";

 /*   String jsonSearch = "" +
            "{" +
            "    \"constant_score\" : {" +
            "        \"filter\" : {" +
            "            \"term\" : {" +
            "                \"path\" : \"C:\\\\TEST_BASE_STREAMING\"" +
            "            }" +
            "        }" +
            "    }" +
            "}";
            */

    /*  String jsonSearch = "" +
            "{" +
            "            \"term\" : {" +
            "                \"artist\" : \"Video\"" +
            "            }" +
            "}";*/

         String jsonSearch = "" +
            "{" +
            "            \"match\" : {" +
            "                \"artist\" : \"Video\"" +
            "            }" +
            "}";


    SearchResponse response =  client.prepareSearch(INDEX)
            .setQuery(jsonSearch).execute().actionGet();

    System.out.println(response.getHits().totalHits());

  }
}
