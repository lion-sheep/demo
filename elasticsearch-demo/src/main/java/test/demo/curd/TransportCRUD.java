package test.demo.curd;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/18 下午2:25
 */
public interface TransportCRUD {
    void addIndex(String index, String type, String id, Object o);
    void updateIndex(String index, String type, String id, Object o);
    void delete(String index, String type, String id);
    Object getObjectByESSearch(String index, String type, String id, Class<?> clazz);
}
