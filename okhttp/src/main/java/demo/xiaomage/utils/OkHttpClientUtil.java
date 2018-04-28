package demo.xiaomage.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/24 下午3:29
 */
public class OkHttpClientUtil {

    public static OkHttpClient getClient(){
       return   new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    public static Headers setHeaders(Map<String,String> headersMaps){
        Headers.Builder builder = new Headers.Builder();
        if(MapUtils.isEmpty(headersMaps)){
            headersMaps.forEach((k,v)->builder.add(k,v));
        }
        return builder.build();
    }

    public static Class<?> synchronizedGet(String url,Headers headers,Class<?> clazz){
        OkHttpClient client = getClient();
        Request request = null;
        if(ObjectUtils.allNotNull(headers)){
            request = new Request.Builder().url(url).get().headers(headers).build();
        }else{
            request = new Request.Builder().url(url).get().build();
        }
        try {
            Response  response = client.newCall(request).execute();
            return JSON.parseObject(response.body().string(),(Type)clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> asynchornizedGet(String url,Headers headers,JsonResponseHandler handler){
        OkHttpClient client = getClient();
        Request request = null;
        if(ObjectUtils.allNotNull(headers)){
            request = new Request.Builder().url(url).get().headers(headers).build();
        }else{
            request = new Request.Builder().url(url).get().build();
        }
        client.newCall(request).enqueue(handler);
        return (Class<?>) handler.getResult();
    }

    public static Class<?> synchronizedPost(String url,Headers headers,Map<String,String> requestMap,Class<?> clazz){
        OkHttpClient client = getClient();
        FormBody.Builder builder = new FormBody.Builder();
        Request request = null;
        if(ObjectUtils.allNotNull(headers,requestMap)){
            requestMap.forEach((k,v)->builder.add(k,v));
            FormBody formBody = builder.build();
            request = new Request.Builder().url(url).headers(headers).post(formBody).build();
        }else{
            throw new IllegalArgumentException("post参数不能为空");
        }
        try {
            Response response = client.newCall(request).execute();
            return JSON.parseObject(response.body().string(),(Type) clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> asynchronizedPost(String url,Headers headers,Map<String,String> requestMap,JsonResponseHandler handler){
        OkHttpClient client = getClient();
        FormBody.Builder builder = new FormBody.Builder();
        Request request = null;
        if(ObjectUtils.allNotNull(headers,requestMap)){
            requestMap.forEach((k,v)->builder.add(k,v));
            FormBody formBody = builder.build();
            request = new Request.Builder().url(url).headers(headers).post(formBody).build();
        }else{
            throw new IllegalArgumentException("post参数不能为空");
        }
        client.newCall(request).enqueue(handler);
        return (Class<?>) handler.getResult();
    }
}
