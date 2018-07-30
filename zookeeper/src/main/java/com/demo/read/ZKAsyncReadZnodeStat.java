package com.demo.read;

import com.demo.callback.ReadChildrenCallback2;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/28 上午1:26
 */
public class ZKAsyncReadZnodeStat implements Watcher {

    private static CountDownLatch zkconneted = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZKAsyncReadZnodeStat());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                zkconneted.countDown();
            }
            if (Event.EventType.NodeChildrenChanged == event.getType()) {
                //由于watcher通知是一次性的，既一旦触发一次通知后，该watcher就失效了，因此客户端需要反复注册watcher
                zooKeeper.getChildren(event.getPath(), true, new ReadChildrenCallback2(), "pass object 2b");
            }
        }
    }

    public static void asyncReadZnodeChildrenStat() throws Exception {
        zooKeeper.create("/zk-test", "Rtest".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create("/zk-test/test1", "Ctest1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getChildren("/zk-test", new ZKAsyncReadZnodeStat(), new ReadChildrenCallback2(), "pass object 1b");
        zooKeeper.create("/zk-test/test2", "Ctest2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public static void main(String[] args) {
        try {
            zkconneted.await();
            asyncReadZnodeChildrenStat();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
