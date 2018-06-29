package test.demo.query;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.demo.po.Student;

import java.util.Map;

/**
 * @author maguowei
 * @desc
 * @date 2018/6/11 下午9:11
 */
public class TransportClientSeacherTest {

    private static Logger log = LogManager.getLogger(TransportClientSeacherTest.class);

    public static void main001(String[] args) {
        log.info("来是时候展现你真正的技术了");
        new TransportClientSeacher().matchQuery("school", "student", "i am good", "desc", 0, 10);
    }

    public static void main002(String[] args) {
        log.info("来是时候叫王小宁来展示她真正的技术了");
        new TransportClientSeacher().multiQuery("school", "student", "i am j", new String[] {"desc", "name"}, 0, 10);
    }

    public static void main003(String[] args) {
        log.info("小王宁你给我出来");
        Map<String, Student> map = Maps.newHashMap();
        map.put("school:student:4", new Student("Aaron", 25, 0));
        map.put("school:student:5", new Student("Berg", 27, 0));
        map.put("school:student:6", new Student("Agnes", 16, 1));
        map.put("school:student:7", new Student("Eranthe", 30, 1));
        new TransportClientSeacher().bulk(map, BulkOptions.INDEX);
    }

    public static void main004(String[] args) {
        log.info("小王宁你这个小熊妮");
        Map<String, Student> map = Maps.newHashMap();
        map.put("school:student:4", new Student("i am good boy,work duck"));
        map.put("school:student:5", new Student("i am good boy,work gigolo"));
        map.put("school:student:6", new Student("i am good girl,work prostitute"));
        map.put("school:student:7", new Student("i am good girl,work whore"));
        new TransportClientSeacher().bulk(map, BulkOptions.UPDATE);
    }

    public static void main005(String[] args) {
        log.info("小王宁你这个小熊妮");
        Map<String, Student> map = Maps.newHashMap();
        map.put("school:student:6", new Student());
        map.put("school:student:7", new Student());
        new TransportClientSeacher().bulk(map, BulkOptions.DELETE);
    }

    public static void main006(String[] args) {
        log.info("快小马哥请开始你的表演");
        new TransportClientSeacher().matchRangeQuery("school", "student", "girl", "desc", "age", new int[] {1, 50}, 0, 10);
    }

}
