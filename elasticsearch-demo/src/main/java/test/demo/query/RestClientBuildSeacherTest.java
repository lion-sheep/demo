package test.demo.query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author maguowei
 * @desc
 * @date 2018/6/29 下午3:48
 */
public class RestClientBuildSeacherTest {
    private static Logger log = LogManager.getLogger(TransportClientSeacherTest.class);

    public static void main(String[] args) {
        log.info("来是时候展现你真正的技术了");
        new RestClientBuildSeacher().matchQuery("school", "student", "boy", "desc", 0, 10);
    }
}
