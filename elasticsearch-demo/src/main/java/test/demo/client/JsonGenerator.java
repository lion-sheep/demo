package test.demo.client;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/28 下午5:51
 */

public final class JsonGenerator {

    final String key;
    final Object value;

    JsonGenerator(Builder builder) {
        this.key = builder.k;
        this.value = builder.v;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public String createJsonQueryString(){
        Map<String,Object> map = Maps.newHashMap();
        map.put(key,value);
        String s = StringUtils.replace(JSON.toJSONString(map),"\\","");
        s = StringUtils.replace(s,"\"{","{");
        String prefix =s.substring(0,s.lastIndexOf(":"));
        String suffix =s.substring(s.lastIndexOf(":"));
        suffix = StringUtils.replace(suffix,"}\"","}");
        return prefix + suffix;
    }

    public static class Builder{
        String k;
        Object v;
        public static Map<String,Object> map = Maps.newHashMap();
        
        public Builder addKeyValue(String key, Object value) {
            map.clear();
            k = key;
            v = value;
            map.put(k,v);
            return this;
        }

        public JsonGenerator build(){
            return new JsonGenerator(this);
        }
    }
}
