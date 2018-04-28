package test.demo.client;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/27 上午10:42
 */
public class HttpClientPool {
    private static Logger log = LogManager.getLogger(HttpClientUtil.class);
    private final static int CONNECT_TIMEOUT = 4000;// 连接超时毫秒
    private final static int SOCKET_TIMEOUT = 10000;// 传输超时毫秒
    private final static int REQUESTCONNECT_TIMEOUT = 3000;// 获取请求超时毫秒
    private final static int CONNECT_TOTAL = 200;// 最大连接数
    private final static int CONNECT_ROUTE = 20;// 每个路由基础的连接数
    private static PoolingHttpClientConnectionManager manager;
    private static CloseableHttpClient client;

    static {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext context = SSLContext.getInstance(SSLConnectionSocketFactory.SSL);
            sslsf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            log.error("SSL上下文创建失败，由于" + e.getLocalizedMessage());
            e.printStackTrace();
        }
        LayeredConnectionSocketFactory lsf = sslsf;
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf).register("https", lsf).build();
        manager = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加到200
        manager.setMaxTotal(CONNECT_TOTAL);
        // 将每个路由基础的连接增加到20
        manager.setDefaultMaxPerRoute(CONNECT_ROUTE);
        // 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
        manager.setValidateAfterInactivity(30000);
        // 设置socket超时时间
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
        manager.setDefaultSocketConfig(socketConfig);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(REQUESTCONNECT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        client = HttpClients.custom().setConnectionManager(manager).setDefaultRequestConfig(requestConfig).setRetryHandler(
                //实现了HttpRequestRetryHandler接口的
                //public boolean retryRequest(IOException exception, int executionCount, HttpContext context)方法
                (exception, executionCount, context) -> {
                    if(executionCount >= 3)// 如果已经重试了3次，就放弃
                        return false;
                    if(exception instanceof NoHttpResponseException)//如果服务器断掉了连接那么重试
                        return true;
                    if(exception instanceof SSLHandshakeException)//不重试握手异常
                        return false;
                    if(exception instanceof InterruptedIOException)//IO传输中断重试
                        return true;
                    if(exception instanceof UnknownHostException)//未知服务器
                        return false;
                    if(exception instanceof ConnectTimeoutException)//超时就重试
                        return true;
                    if(exception instanceof SSLException)//ssl握手异常
                    return false;
                    HttpClientContext cliContext = HttpClientContext.adapt(context);
                    HttpRequest request = cliContext.getRequest();
                    if(!(request instanceof HttpEntityEnclosingRequest))
                        return true;
                    return false;
                }
        ).build();
        if (manager != null && manager.getTotalStats() != null) {
            log.info("now client pool " + manager.getTotalStats().toString());
        }
    }

    public static PoolingHttpClientConnectionManager getManager() {
        return manager;
    }

    public static void setManager(PoolingHttpClientConnectionManager manager) {
        HttpClientPool.manager = manager;
    }

    public static CloseableHttpClient getClient() {
        return client;
    }

    public static void setClient(CloseableHttpClient client) {
        HttpClientPool.client = client;
    }
}