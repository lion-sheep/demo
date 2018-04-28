package test.demo.client;

import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/19 下午4:30
 */
public class RestfulClient {

    public static HttpClient buildHttpClient(){
        return HttpClients.createDefault();
    }

    public static OkHttpClient buildOkHttpClient(){
        return null;
    }
}
