package demo.xiaomage.test;

import okhttp3.*;

import java.io.IOException;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/23 下午4:21
 */
public class OkHttpGetTest {

    public static void main(String[] args) {
        try {
            asynchronization();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //同步get请求
    public static void synchronization() throws Exception{
        OkHttpClient client = new OkHttpClient();
        String url = "http://www.baidu.com";
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String header = response.headers().toString();
        String body = response.body().string();
        System.out.println(header);
        System.out.println(body);
    }

    public static void asynchronization(){
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        String url = "http://www.baidu.com";
        Request request = builder.url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                RequestBody requestBody = call.request().body();
                System.out.println(requestBody);
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                RequestBody requestBody = call.request().body();
                System.out.println(requestBody);
                String requestHeaders = call.request().headers().toString();
                System.out.println(requestHeaders);
                System.out.println("-------------------------------");
                System.out.println();
                String headers = response.headers().toString();
                String body = response.body().string();
                System.out.println(headers);
                System.out.println(body);
            }
        });
    }

    public static void post(){
       OkHttpClient client = new OkHttpClient();
       FormBody.Builder formBuilder = new FormBody.Builder();
        FormBody formBody = formBuilder
                            .add("name", "lili")
                            .add("age", "18")
                            .add("password", "1q2w3e4r")
                            .build();
        Request.Builder requestBuilder = new Request.Builder();
        String url = "";
        Request request = requestBuilder.url(url).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
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
