package test.demo.client;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/24 下午7:30
 */
public class OkHttpUtil {

    public static OkHttpClient getClient() {
        return new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public static String sychronizedGet(String url, Map<String, Object> params, Map<String, String> headers) {
        OkHttpClient client = getClient();
        Request.Builder builder = new Request.Builder();
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((k, v) -> builder.header(k, v));
        }
        StringBuffer sb = new StringBuffer("?");
        if (MapUtils.isNotEmpty(params)) {
            params.forEach((k, v) -> sb.append(k).append("=").append(v).append("&"));
            String param = sb.toString();
            url += param.substring(0, param.lastIndexOf("&"));
        }
        Request request = builder.url(url).get().build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return response.message();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String asychronizedGet(String url, Map<String, Object> params, Map<String, String> headers, CallbackHandler handler) {
        OkHttpClient client = getClient();
        StringBuffer sb = new StringBuffer("?");
        if (MapUtils.isNotEmpty(params)) {
            params.forEach((k, v) -> sb.append(k).append("=").append("&"));
            String param = sb.toString();
            url += param.substring(0, param.lastIndexOf("&"));
        }
        Request.Builder builder = new Request.Builder();
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((k, v) -> builder.header(k, v));
        }
        Request request = builder.url(url).get().build();
        client.newCall(request).enqueue(handler);
        return handler.getResult();
    }

    public static String sychronizedPostOrPutOrDelete(String url, Object o, Map<String, String> headers, HttpMethod method) {
        OkHttpClient client = getClient();
        Request.Builder builder = new Request.Builder();
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((k, v) -> builder.header(k, v));
        }
        if (ObjectUtils.allNotNull(o)) {
            RequestBody body = null;
            if(o instanceof String){
                body = RequestBody.create(MediaType.parse("application/json;charset=utf-8;"),(String) o);
            }else{
                body = RequestBody.create(MediaType.parse("application/json;charset=utf-8;"), JSON.toJSONString(o));
            }
            switch (method) {
                case PUT:
                    builder.put(body);
                    break;
                case DELETE:
                    builder.delete(body);
                    break;
                case POST:
                    builder.post(body);
                    break;
                default:
                    builder.post(body);
                    break;
            }
        } else {
            switch (method) {
                case DELETE:
                    builder.delete();
                    break;
                default:
                    builder.delete();
                    break;
            }
        }
        Request request = builder.url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String body = response.body().string();
                return body;
            } else {
                return response.message();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String asychronizedPostOrPutOrDelete(
            String url,
            Object o,
            Map<String, String> headers,
            HttpMethod method,
            CallbackHandler handler) {
        OkHttpClient client = getClient();
        Request.Builder builder = new Request.Builder();
        if (ObjectUtils.allNotNull(o)) {
            RequestBody body = null;
            if(o instanceof String){
                body = RequestBody.create(MediaType.parse("application/json;charset=utf-8;"),(String) o);
            }else{
                body = RequestBody.create(MediaType.parse("application/json;charset=utf-8;"), JSON.toJSONString(o));
            }
            switch (method) {
                case POST:
                    builder.post(body);
                    break;
                case PUT:
                    builder.put(body);
                    break;
                case DELETE:
                    builder.delete(body);
                    break;
                default:
                    builder.post(body);
                    break;
            }
        } else {
            switch (method) {
                case DELETE:
                    builder.delete();
                    break;
                default:
                    builder.delete();
                    break;
            }
        }
        Request request = builder.url(url).build();
        client.newCall(request).enqueue(handler);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return handler.getResult();
    }
}
