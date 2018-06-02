package test.demo.query;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import test.demo.client.*;

import java.util.Arrays;
import java.util.Map;

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
        JsonGenerator.Builder builder = new JsonGenerator.Builder();
        builder.addKeyValue("desc", "it").addKeyValue("match",JSON.toJSONString(builder.map)).addKeyValue("query",JSON.toJSONString(builder.map));
        String url = "http://localhost:9200/school/student/_search";
        Map<String,String> headers = Maps.newHashMap();
        headers.put("Content-type","application/json;charset=utf-8");
        String queryJson = builder.build().createJsonQueryString();
        System.out.println(queryJson);
        String result = HttpClientUtil.doPost(url,queryJson,headers);
        System.out.println(result);
    }

    public void matchQueryByOkHttp(){
        JsonGenerator.Builder builder = new JsonGenerator.Builder();
        builder.addKeyValue("desc", "it").addKeyValue("match",JSON.toJSONString(builder.map)).addKeyValue("query",JSON.toJSONString(builder.map));
        String url = "http://localhost:9200/school/student/_search";
        Map<String,String> headers = Maps.newHashMap();
        String queryJsonRequestBody = builder.build().createJsonQueryString();
        String result = OkHttpUtil.sychronizedPostOrPutOrDelete(url, queryJsonRequestBody, headers, HttpMethod.POST);
        System.out.println(result);
    }
}
