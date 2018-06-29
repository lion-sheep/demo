package test.demo.query;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import test.demo.client.RestClientBuild;
import test.demo.po.Student;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

/**
 * @author maguowei
 * @desc
 * @date 2018/6/29 下午2:54
 */
public class RestClientBuildSeacher implements QuerySearch {

    private RestClient restClient = RestClientBuild.build();
    @Override
    public void matchQuery(String index, String type, String queryCondition, String field, int pageIndex, int pageSize){
        String[] strs = new String[]{"",index,type,"_search"};
        String endpoint = StringUtils.join(strs,"/");
        // HttpEntity 中的内容就是 http body 请求体重的数据信息
        HttpEntity entity = null;
        try {
             entity = new NStringEntity("{\n"
                    + "              \"query\":{\n"
                    + "                \"match\":{\n"
                    + "                   \""+field+"\":\""+queryCondition+"\"\n"
                    + "                }\n"
                    + "              }\n"
                    + "            }");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            Header header = new BasicHeader("Content-Type","application/json");
            Response response = restClient.performRequest("POST", endpoint, Collections.<String, String>emptyMap(), entity,header);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void matchRangeQuery(
            String index,
            String type,
            String queryCondition,
            String matchField,
            String rangeField,
            int[] rangeCondition,
            int pageIndex,
            int pageSize) {

    }

    @Override
    public void multiQuery(String index, String type, String queryCondition, String[] fields, int pageIndex, int pageSize) {

    }

    @Override
    public void bulk(Map<String, Student> map, BulkOptions option) {

    }

    @Override
    public void boolQuery() {

    }

    @Override
    public void filterQuery() {

    }

    @Override
    public void aggQuery() {

    }

    @Override
    public void metricsAgg() {

    }


}
