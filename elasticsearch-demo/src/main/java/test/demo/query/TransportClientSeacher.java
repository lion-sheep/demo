package test.demo.query;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import test.demo.client.TransportClientBuild;
import test.demo.po.Student;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author maguowei
 * @desc
 * @date 2018/6/11 下午6:59
 */
public class TransportClientSeacher implements QuerySearch {

    private TransportClient transportClient = TransportClientBuild.build();

    @Override
    public void matchQuery(String index, String type, String queryCondition, String field, int pageIndex, int pageSize) {
        SearchRequestBuilder searchRequestBuilder =
                transportClient.prepareSearch(index)
                        .setTypes(type)
                        .setQuery(QueryBuilders.matchQuery(field, queryCondition))
                        .setFrom(pageIndex)
                        .setSize(pageSize);
        SearchResponse searchResponse = searchRequestBuilder.get();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        Arrays.stream(searchHits).map(e -> e.getSourceAsString()).collect(Collectors.toList()).forEach(System.out::println);
    }

    @Override
    public void matchRangeQuery(
            String index,
            String type,
            String queryCondition,
            String matchField,
            String rangeField,
            int[] rangeCondition,
            int pageIndex,
            int pageSize) {
        /*
            GET /school/student/_search
            {
              "query": {
                "bool": {
                  "must": [
                    {
                      "match": {
                        "desc": "boy"
                      }
                    },
                    {
                      "range": {
                        "age": {
                          "gt": 1,
                          "lt": 50
                        }
                      }
                    }
                  ]
                }
              }
            }
         */
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.matchQuery(matchField, queryCondition))
                .setPostFilter(QueryBuilders.rangeQuery(rangeField).gt(rangeCondition[0]).lt(rangeCondition[1]))
                .setFrom(pageIndex).setSize(pageSize).setExplain(true);
        SearchResponse searchResponse = searchRequestBuilder.get();
        /*
          {
              "took": 22,
              "timed_out": false,
              "_shards": {
                "total": 5,
                "successful": 5,
                "skipped": 0,
                "failed": 0
              },
              "hits": {
                "total": 1,
                "max_score": 1.287682,
                "hits": [
                  {
                    "_index": "school",
                    "_type": "student",
                    "_id": "1",
                    "_score": 1.287682,
                    "_source": {
                      "name": "jack",
                      "age": 27,
                      "gender": 0,
                      "desc": "i am good boy,work it"
                    }
                  }
                ]
              }
            }
         */
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        Arrays.stream(searchHits).map(e->e.getSourceAsString()).collect(Collectors.toList()).forEach(System.out::println);
    }

    @Override
    public void multiQuery(String index, String type, String queryCondition, String[] fields, int pageIndex, int pageSize) {
        MultiSearchRequestBuilder multiSearchRequestBuilder = transportClient.prepareMultiSearch();
        Arrays.stream(fields).forEach(e -> {
            multiSearchRequestBuilder
                    .add(transportClient.prepareSearch(index)
                            .setTypes(type)
                            .setQuery(QueryBuilders.matchQuery(e, queryCondition))
                            .setFrom(pageIndex)
                            .setSize(pageSize));
        });
        MultiSearchResponse multiSearchResponse = multiSearchRequestBuilder.get();
        MultiSearchResponse.Item[] items = multiSearchResponse.getResponses();
        Arrays.stream(items).forEach(e -> {
            Arrays.stream(e.getResponse().getHits().getHits())
                    .map(x -> x.getSourceAsString())
                    .collect(Collectors.toList())
                    .forEach(System.out::println);
        });
    }

    @Override
    public void bulk(Map<String, Student> map, BulkOptions option) {
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
        switch (option.getOption()) {
            case "index":
                map.forEach((k, v) -> {
                    String index = k.split(":")[0];
                    String type = k.split(":")[1];
                    String id = k.split(":")[2];
                    bulkRequestBuilder.add(transportClient.prepareIndex(index, type, id).setSource(JSON.toJSONString(v), XContentType.JSON));
                });
                BulkResponse bulkResponse1 = bulkRequestBuilder.get();
                BulkItemResponse[] bulkResponse1Items = bulkResponse1.getItems();
                Arrays.stream(bulkResponse1Items).forEach(e -> {
                    System.out.println(e.getResponse().getResult().toString());
                });
                break;
            case "update":
                map.forEach((k, v) -> {
                    String index = k.split(":")[0];
                    String type = k.split(":")[1];
                    String id = k.split(":")[2];
                    bulkRequestBuilder.add(transportClient.prepareUpdate(index, type, id).setDoc(JSON.toJSONString(v), XContentType.JSON));
                });
                BulkResponse bulkResponse2 = bulkRequestBuilder.get();
                BulkItemResponse[] bulkResponse2Items = bulkResponse2.getItems();
                Arrays.stream(bulkResponse2Items).forEach(e -> {
                    System.out.println(e.getResponse().getResult().toString());
                });
                break;
            case "delete":
                map.forEach((k, v) -> {
                    String index = k.split(":")[0];
                    String type = k.split(":")[1];
                    String id = k.split(":")[2];
                    bulkRequestBuilder.add(transportClient.prepareDelete(index, type, id));
                });
                BulkResponse bulkResponse3 = bulkRequestBuilder.get();
                BulkItemResponse[] bulkResponse3Items = bulkResponse3.getItems();
                Arrays.stream(bulkResponse3Items).forEach(e -> {
                    System.out.println(e.getResponse().getResult());
                });
                break;
        }
    }
}
