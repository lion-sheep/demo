package test.demo.curd;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentType;
import test.demo.client.TransportClientBuild;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/18 下午2:24
 */
public class TransportClientCRUD implements TransportCRUD {

    @Override
    public void addIndex(String index, String type, String id, Object o) {
        /*
         * prepareIndex(index, type, id) =  GET /index/type/id
         * setSource(JSON.toJSONString(o), XContentType.JSON) =  {json}
         * prepareIndex(index, type, id).setSource(JSON.toJSONString(o), XContentType.JSON)
         * GET /index/type/id
         * {json}
         */
        IndexRequestBuilder indexRequestBuilder =
                TransportClientBuild.build().prepareIndex(index, type, id).setSource(JSON.toJSONString(o), XContentType.JSON);
        IndexResponse indexResponse = indexRequestBuilder.get();
        System.out.println(indexResponse.getResult());
    }

    @Override
    public void updateIndex(String index, String type, String id, Object o) {
        UpdateRequestBuilder updateRequestBuilder = TransportClientBuild.build().prepareUpdate(index, type, id).setDoc(o);
        UpdateResponse updateResponse = updateRequestBuilder.get();
        System.out.println(updateResponse.getResult());
    }

    @Override
    public void delete(String index, String type, String id) {
        DeleteRequestBuilder deleteRequestBuilder = TransportClientBuild.build().prepareDelete(index, type, id);
        DeleteResponse deleteResponse = deleteRequestBuilder.get();
        System.out.println(deleteResponse.getResult());
    }

    @Override
    public Object getObjectByESSearch(String index, String type, String id, Class<?> clazz) {
        GetRequestBuilder getRequestBuilder = TransportClientBuild.build().prepareGet(index, type, id);
        GetResponse getResponse = getRequestBuilder.get();
        return JSON.parseObject(getResponse.getSourceAsString(), clazz);
    }
}
