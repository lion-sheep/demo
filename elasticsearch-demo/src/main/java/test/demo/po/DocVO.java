package test.demo.po;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/25 下午4:24
 */
public class DocVO {

    private String key;
    private Object value;

    public DocVO(String key,Object value){
        this.key = key;
        this.value = value;
    }

    public Map<String,Object> createMap(){
        Map<String,Object> map = Maps.newHashMap();
        map.put(key,value);
        return map;
    }
}
