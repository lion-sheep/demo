package test.demo.curd;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import test.demo.client.CallbackHandler;
import test.demo.client.HttpMethod;
import test.demo.client.OkHttpUtil;
import test.demo.po.DocVO;

import java.util.Map;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/18 下午2:24
 */
public class RestfulOKHttpClientCRUD implements RestfulCRUD {

    @Override
    public void addIndex(String index, String type, String id, String ip, String port,String option,Object o) {
        String host = ip + ":" + port;
        String[] strs = null;
        if(StringUtils.isEmpty(option)){
            strs = new String[] {host, index, type, id};
        }else{
            strs = new String[] {host, index, type, id, option};
        }
        String url = StringUtils.join(strs, "/");
        Map<String,String> headers = Maps.newHashMap();
        headers.put("Content-type","application/json;charset=utf-8");
        String result = OkHttpUtil.asychronizedPostOrPutOrDelete(url,o,headers,HttpMethod.PUT,new CallbackHandler());
        System.out.println(result);
    }

    @Override
    public void updateIndex(String index, String type, String id, String ip, String port,String option,Object o) {
        String host = ip + ":" + port;
        String[] strs = null;
        if(StringUtils.isEmpty(option)){
            strs = new String[] {host, index, type, id};
        }else{
            strs = new String[] {host, index, type, id, option};
        }
        String url = StringUtils.join(strs, "/");
        Map<String,String> headers = Maps.newHashMap();
        headers.put("Content-type","application/json;charset=utf-8");
        DocVO vo = new DocVO("doc",o);
        String result = OkHttpUtil.sychronizedPostOrPutOrDelete(url,vo.createMap(),headers,HttpMethod.POST);

        System.out.println(result);
    }

    @Override
    public void delete(String index, String type, String id,String ip,String port,String option) {
        String host = ip + ":" + port;
        String[] strs = null;
        if(StringUtils.isEmpty(option)){
            strs = new String[] {host, index, type, id};
        }else{
            strs = new String[] {host, index, type, id, option};
        }
        String url = StringUtils.join(strs, "/");
        Map<String,String> headers = Maps.newHashMap();
        headers.put("Content-type","application/json;charset=utf-8");
        String result = OkHttpUtil.asychronizedGet(url,null,headers,new CallbackHandler());
        System.out.println(result);
    }


    @Override
    public Object getObjectByESSearch(String index, String type, String id, String ip, String port, String option, Class<?> clazz) {
        String host = ip+":"+port;
        String[] strs = null;
        if(StringUtils.isEmpty(option)){
            strs = new String[] {host, index, type, id};
        }else{
            strs = new String[] {host, index, type, id, option};
        }
        String url = StringUtils.join(strs,"/");
      Map<String,String> headers = Maps.newHashMap();
      headers.put("Content-type","application/json;charset=utf-8");
        String result = OkHttpUtil.sychronizedGet(url,null,headers);
        result = result.substring(result.lastIndexOf("_source")+9,result.length()-1);
        System.out.println(result);
        Object o = JSON.parseObject(result, clazz);
        return o;
    }
}
