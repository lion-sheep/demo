package test.demo.query;

import test.demo.po.Student;

import java.util.Map;

/**
 * @author maguowei
 * @desc
 * @date 2018/6/11 下午6:47
 */
public interface QuerySearch {
      void matchQuery(String index,String type,String queryCondition,String field,int pageIndex,int pageSize);
      void matchRangeQuery(String index,String type,String queryCondition,String matchField,String rangeField,int[] rangeCondition,int pageIndex,int pageSize);
      void multiQuery(String index,String type,String queryCondition,String[] fields,int pageIndex,int pageSize);
      void bulk(Map<String,Student> map,BulkOptions option);
}
