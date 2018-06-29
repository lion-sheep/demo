package test.demo.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

/**
 * @author maguowei
 * @desc
 * @date 2018/6/29 下午2:44
 */
public class RestClientBuild {

    public static RestClient build(){
        RestClient restClient = RestClient.builder( new HttpHost("localhost",9200,"http")).build();
        return restClient;
    }
}
