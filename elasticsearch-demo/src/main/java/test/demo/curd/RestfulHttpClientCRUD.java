package test.demo.curd;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import test.demo.client.RestfulClient;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/18 下午2:24
 */
public class RestfulHttpClientCRUD implements RestfulCRUD {

    @Override
    public void addIndex(String index, String type, String id, String ip, String port,String option,Object o) {
        HttpClient httpClient = RestfulClient.buildHttpClient();
        String host = ip + ":" + port;
        String[] strs = null;
        if(StringUtils.isEmpty(option)){
            strs = new String[] {host, index, type, id};
        }else{
            strs = new String[] {host, index, type, id, option};
        }
        String url = StringUtils.join(strs, "/");
        System.out.println(url);
        //这就代表一个 put 方式的 request 请求
        HttpPut request = new HttpPut(url);
        request.setHeader("Content-Type", "application/json;charset=utf-8");
        String jsonStr = JSON.toJSONString(o);
        try {
            request.setEntity(new StringEntity(jsonStr));
            // 返回 这个 request post 请求的响应结果
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
            //            InputStream content = response.getEntity().getContent();
            //            byte[] but = new byte[content.available()];
            //            content.read(but);
            //            System.out.println("content = " + new String(but,0,but.length));
            //获取得到的响应数据
            HttpEntity httpEntity = response.getEntity();
            System.out.println(EntityUtils.toString(httpEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateIndex(String index, String type, String id, String ip, String port,String option,Object o) {
        HttpClient httpClient = RestfulClient.buildHttpClient();
        String host = ip + ":" + port;
        String[] strs = null;
        if(StringUtils.isEmpty(option)){
            strs = new String[] {host, index, type, id};
        }else{
            strs = new String[] {host, index, type, id, option};
        }
        String url = StringUtils.join(strs, "/");
        HttpPut request = new HttpPut(url);
        request.setHeader("Content-type","application/json;charset=utf-8");
        try {
            request.setEntity(new StringEntity(JSON.toJSONString(o)));
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String index, String type, String id,String ip,String port,String option) {
        HttpClient httpClient = RestfulClient.buildHttpClient();
        String host = ip + ":" + port;
        String[] strs = null;
        if(StringUtils.isEmpty(option)){
            strs = new String[] {host, index, type, id};
        }else{
            strs = new String[] {host, index, type, id, option};
        }
        String url = StringUtils.join(strs, "/");
        HttpDelete request = new HttpDelete(url);
        try{
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public Object getObjectByESSearch(String index, String type, String id,String ip,String port,String option,Class<?> clazz) {
        HttpClient httpClient = RestfulClient.buildHttpClient();
        String host = ip+":"+port;
        String[] strs = null;
        if(StringUtils.isEmpty(option)){
            strs = new String[] {host, index, type, id};
        }else{
            strs = new String[] {host, index, type, id, option};
        }
        String url = StringUtils.join(strs,"/");
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
