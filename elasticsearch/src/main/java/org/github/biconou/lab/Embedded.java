package org.github.biconou.lab;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

/**
 * Created by remi on 24/04/2016.
 */
public class Embedded {

  public static final String[] allIndexes = {"music","music2"};

  public static void main(String args[]) throws IOException {

    /* Client client = TransportClient.builder().build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getLoopbackAddress(), 9300));
    */


    Map<String,String> settings = new HashMap<>();
    settings.put("path.home","D:/elasticsearch-2.3.3");
    Node node = NodeBuilder.nodeBuilder().client(false).data(true).local(true).settings(Settings.builder().put(settings)).clusterName("elasticsearch").node();
    Client client = node.client();


    String INDEX = "music";

    boolean indexExists = client.admin().indices().prepareExists(INDEX).execute().actionGet().isExists();

    System.out.println(indexExists);

    ////

    /* String RandomAlbumsJsonSearch = "{\n" +
      "    \"function_score\" : {\n" +
      "        \"filter\" : {\n" +
      "            \"bool\" : {\n" +
      "                \"must\" : [\n" +
      "                    {\"term\" : { \"mediaType\" : \"ALBUM\" }},\n" +
      "                    {\"type\" : { \"value\" : \"MEDIA_FILE\" }}\n" +
      "                ]\n" +
      "            }\n" +
      "        },\n" +
      "        \"random_score\" : {}\n" +
      "    }\n" +
      "}";
      */

      String RandomAlbumsJsonSearch = " {\"term\" : { \"mediaType\" : \"ALBUM\" }}";



    SearchResponse response =  client.prepareSearch(allIndexes)
      .setQuery(RandomAlbumsJsonSearch).execute().actionGet();

    System.out.println(response.getHits().totalHits());



  }
}
