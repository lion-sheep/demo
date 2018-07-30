package com.demo.read;

import com.demo.callback.ZnodeDataCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/28 上午2:15
 */
public class ZKAsyncReadZnodeData implements Watcher {

    private static ZooKeeper zooKeeper;
    private static CountDownLatch zkconnected = new CountDownLatch(1);

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZKAsyncReadZnodeData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                zkconnected.countDown();
            }
            if (Event.EventType.NodeDataChanged == event.getType()) {
                System.out.println("znode's path : " + event.getPath());
                //由于watcher通知是一次性的，既一旦触发一次通知后，该watcher就失效了，因此客户端需要反复注册watcher
                zooKeeper.getData(event.getPath(), true, new ZnodeDataCallback(), "pass ctx hhh");
            }
        }
    }

    public static void AsyncGetZnodeData() throws Exception {
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData("/zk-test", new ZKAsyncReadZnodeData(), stat);
        System.out.println("znode's data : " + new String(data) + " " + stat);
        //version=-1 就是告诉zk服务器，客户端需要基于数据的最新版本进行更新操作
        zooKeeper.setData("/zk-test", "resRtest".getBytes(), -1);
    }

    public static void main(String[] args) throws Exception {
        zkconnected.await();
        AsyncGetZnodeData();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
