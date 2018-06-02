package test.demo.client;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/27 上午10:26
 */
public class HttpClientUtil {

    public static String doGet(String url, Map<String, Object> params, Map<String, String> headerParams) {
        HttpClient client = RestfulClient.buildHttpClient();
        try {
            final URIBuilder uriBuilder = new URIBuilder(url);
            if (MapUtils.isEmpty(params)) {
                params.forEach((k, v) -> uriBuilder.addParameter(k, v.toString()));
            }
            final HttpGet get = new HttpGet(uriBuilder.build());
            if (MapUtils.isEmpty(headerParams)) {
                headerParams.forEach((k, v) -> {
                    get.setHeader(k, v);
                });
            }
            HttpResponse response = client.execute(get);
            if (200 == response.getStatusLine().getStatusCode()) {
                HttpEntity httpEntity = response.getEntity();
                return EntityUtils.toString(httpEntity, "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost(String url, Object o, Map<String, String> headerParams) {
        HttpClient client = RestfulClient.buildHttpClient();
        HttpPost post = new HttpPost(url);
        if (ObjectUtils.allNotNull(o)) {
            if (o instanceof String) {
                // 构建消息实体
                StringEntity entity = new StringEntity((String) o, Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                // 发送Json格式的数据请求
                entity.setContentType("application/json");
                post.setEntity(entity);
            } else {
                // 构建消息实体
                StringEntity entity = new StringEntity(JSON.toJSONString(o), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                // 发送Json格式的数据请求
                entity.setContentType("application/json");
                post.setEntity(entity);
            }
        }
        if (MapUtils.isNotEmpty(headerParams)) {
            headerParams.forEach((k, v) -> {
                post.setHeader(k, v);
            });
        }
        try {
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPut(String url, Object o, Map<String, String> headers) {
        HttpClient client = RestfulClient.buildHttpClient();
        HttpPut put = new HttpPut(url);
        if (ObjectUtils.allNotNull(o)) {
            if (o instanceof String) {
                // 构建消息实体
                StringEntity entity = new StringEntity((String) o, Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                // 发送Json格式的数据请求
                entity.setContentType("application/json");
                put.setEntity(entity);
            } else {
                // 构建消息实体
                StringEntity entity = new StringEntity(JSON.toJSONString(o), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                // 发送Json格式的数据请求
                entity.setContentType("application/json");
                put.setEntity(entity);
            }
        }
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((k, v) -> put.setHeader(k, v));
        }
        try {
            HttpResponse response = client.execute(put);
            HttpEntity httpEntity = response.getEntity();
            EntityUtils.toString(httpEntity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doDelete(String url, Map<String, Object> params, Map<String, String> headers) {
        HttpClient client = RestfulClient.buildHttpClient();
        HttpDelete delete = new HttpDelete();
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (MapUtils.isNotEmpty(params)) {
                params.forEach((k, v) -> uriBuilder.addParameter(k, v.toString()));
            }
            delete.setURI(uriBuilder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((k, v) -> delete.setHeader(k, v));
        }
        try {
            HttpResponse response = client.execute(delete);
            HttpEntity httpEntity = response.getEntity();
            EntityUtils.toString(httpEntity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
