package com.demo.read;

import com.demo.callback.ReadChildrenCallback;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/27 下午11:49
 */
public class ZKAsyncReadZnode implements Watcher {

    private static CountDownLatch zkConnected = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZKAsyncReadZnode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                zkConnected.countDown();
            }
            if (Event.EventType.NodeChildrenChanged == event.getType()) {
                try {
                    //由于watcher通知是一次性的，既一旦触发一次通知后，该watcher就失效了，因此客户端需要反复注册watcher
                    List<String> children = zooKeeper.getChildren(event.getPath(), true);
                    System.out.println(children);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println();
            }
        }
    }

    public static void asyncReadZnode() throws Exception {
        zooKeeper.create("/zk-test", "Rtest".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create("/zk-test/test1", "Ctest1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getChildren("/zk-test", new ZKAsyncReadZnode(), new ReadChildrenCallback(), "pass ctx h2");
        zooKeeper.create("/zk-test/test2", "Ctest2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public static void main(String[] args) {
        try {
            zkConnected.await();
            asyncReadZnode();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
