package com.demo.read;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/27 下午3:56
 */
public class ZKSyncReadZnodeStat implements Watcher {

    private static ZooKeeper zooKeeper;
    private static CountDownLatch zkConnectioned = new CountDownLatch(1);

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZKSyncReadZnodeStat());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                zkConnectioned.countDown();
            }
            if (Event.EventType.NodeChildrenChanged == event.getType()) {
                try {
                    Stat stat = new Stat();
                    //由于watcher通知是一次性的，既一旦触发一次通知后，该watcher就失效了，因此客户端需要反复注册watcher
                    List<String> children = zooKeeper.getChildren(event.getPath(), true, stat);
                    System.out.println(children);
                    //stat 状态 是当前节点的状态，不是子节点的状态
                    System.out.println(
                            "znode's path = " + event.getPath() + " czxid = " + stat.getCzxid() + " ctime = " + stat.getCtime() + " mzxid = "
                                    + stat.getMzxid() + " mtime = " + stat.getMtime() + " pzxid = " + stat.getPzxid() + " cversion = "
                                    + stat.getCversion());
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void syncReadZnodeChildren() throws Exception {
        zooKeeper.create("/zk-test", "Rtest".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create("/zk-test/test1", "Ctest1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        Stat stat = new Stat();
        List<String> children = zooKeeper.getChildren("/zk-test", new ZKSyncReadZnodeStat(), stat);
        System.out.println(children);
        System.out.println(
                "czxid = " + stat.getCzxid() + " ctime = " + stat.getCtime() + " mzxid = " + stat.getMzxid() + " mtime = " + stat.getMtime()
                        + " pzxid = " + stat.getPzxid() + " cversion = " + stat.getCversion());
        zooKeeper.create("/zk-test/test2", "Ctest2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public static void main(String[] args) {
        try {
            zkConnectioned.await();
            syncReadZnodeChildren();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
