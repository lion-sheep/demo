package demo.xiaomage.test;

import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/23 下午9:48
 */
public class okHttpPacking {

    public static OkHttpClient getOkHttpClient(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();
        return client;
    }

    public static Headers setHeaders(Map<String,String> headersParams){
        Headers.Builder builder = new Headers.Builder();
        if(MapUtils.isEmpty(headersParams)){
            headersParams.forEach((k,v)->builder.add(k,v));
        }
        return builder.build();
    }

    public static void synchronizedGet(String url,Headers headers){
        OkHttpClient client = getOkHttpClient();
        Request request = null;
        if(ObjectUtils.allNotNull(headers)){
           request = new Request.Builder().url(url).get().headers(headers).build();
        }else {
           request= new Request.Builder().url(url).get().build();
        }
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.headers().toString());
        try {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void asynchronizedGet(String url,Headers headers){
        OkHttpClient client = getOkHttpClient();
        Request request = null;
        if(ObjectUtils.allNotNull(headers)){
            request = new Request.Builder().url(url).headers(headers).build();
        }else{
            request = new Request.Builder().url(url).build();
        }
        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String headers = response.headers().toString();
                String body = response.body().string();
                System.out.println(headers);
                System.out.println(body);
            }
        });
    }
 }
