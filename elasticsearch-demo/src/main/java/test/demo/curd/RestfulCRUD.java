package test.demo.curd;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/18 下午2:25
 */
public interface RestfulCRUD {
    void addIndex(String index,String type,String id,String ip,String port,String option,Object o);
    void updateIndex(String index,String type,String id,String ip,String port,String option,Object o);
    void delete(String index,String type,String id,String ip,String port,String option);
    Object getObjectByESSearch(String index,String type,String id,String ip,String port,String option,Class<?> clazz);
}
