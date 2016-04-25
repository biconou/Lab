package org.github.biconou.lab;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.NodeBuilder;

/**
 * Created by remi on 24/04/2016.
 */
public class Main {

    public static void main(String args[]) {


        Client client = NodeBuilder.nodeBuilder()
                .client(true)
                .node()
                .client();

        String INDEX = "testIndex";

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

        IndexResponse response = client.prepareIndex(INDEX, "general","1")
                .setSource(json)
                .get();

       /* SearchResponse allHits = client.prepareSearch(INDEX)
                .addFields("title", "category")
                .setQuery(QueryBuilders.matchAllQuery())
                .execute().actionGet();
                */
    }
}
