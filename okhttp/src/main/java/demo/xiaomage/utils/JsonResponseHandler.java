package demo.xiaomage.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/24 下午2:53
 */
public class JsonResponseHandler implements Callback {

    private Class<?> clazz;
    private Object result;

    public JsonResponseHandler(Class<?> clazz){
        this.clazz = clazz;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String body = response.body().string();
        if(StringUtils.isEmpty(body)){
            Class<?> t = JSON.parseObject(body, (Type) clazz);
            this.result = t;
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
