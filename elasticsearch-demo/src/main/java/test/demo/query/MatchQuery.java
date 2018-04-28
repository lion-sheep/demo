package test.demo.query;

import org.apache.http.client.HttpClient;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import test.demo.client.RestfulClient;
import test.demo.client.TransportClientBuild;

import java.util.Arrays;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/26 下午9:50
 */
public class MatchQuery {

    public void matchQueryByTransportClient(){
        TransportClient client = TransportClientBuild.build();
        /*
            GET /school/student/_search
            {
              "query":{
                "match":{
                   "age":18
                }
              }
            }
         */
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("school").setTypes("student")
                .setQuery(QueryBuilders.matchQuery("desc", "it"))
                .setFrom(0).setSize(10);
        SearchResponse searchResponse = searchRequestBuilder.get();

        SearchHit[] searchHits = searchResponse.getHits().getHits();
        Arrays.stream(searchHits).forEach(e-> System.out.println(e.getSourceAsString()));
    }

    public void matchQueryByHttpClient(){
        HttpClient client = RestfulClient.buildHttpClient();
        
    }
}
