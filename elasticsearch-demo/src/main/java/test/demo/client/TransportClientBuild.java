package test.demo.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;


/**
 * @author maguowei
 * @desc TransportClient 传输客户端
 *  TransportClient 传输客户端作为一个集群和应用程序之间的通信层。它知道 API 并能自动帮你在节点之间轮询，帮你嗅探集群等等。但它是集群 外部的 ，和 REST 客户端类似。
 * @date 2018/4/18 上午12:55
 */
public class TransportClientBuild {
    public static TransportClient build() {
        String clusterName = "elasticsearch";
        Settings settings = Settings.builder().put("cluster.name",clusterName).build();
        // 传输客户端 使用 tcp 协议 通过 9300 端口访问 es
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost",9300);
        TransportAddress transportAddress = new TransportAddress(inetSocketAddress);
        //本方法允许只连接到集群而不加入集群。你会发现，客户端可以连接到多个节点，并通过轮询调度的方式使用它们。
        /*
         * Settings 类的静态方法builder()来建立合适的配置对象，如在本例中只需设置好集群名称。
         * 接着我们使用这个配置对象来建立一个TransportClient的客户端对象，然后把连接点的网络地址 127.0.0.1 传递给他。
         * 这可以通过TransportClient类的addTransportAddress()方法并传递InetSocketTransportAddress 对象来实现。
         * 为创建InetSocketTransportAddress对象，我们需要提供ElasticSearch多节点在机器的IP地址和节点传输层的监听端口号。
         * 注意，这里的端口号不是9200，而是9300。9200端口是用来让HTTP REST API来访问ElasticSearch，而9300端口是传输层监听的默认端口。
         */
        TransportClient transportClient = new PreBuiltTransportClient(settings).addTransportAddress(transportAddress);
        return transportClient;
    }
}
