package com.demo.read;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/27 下午3:12
 */
public class ZKSyncReadZnode implements Watcher {

    private static CountDownLatch zkConnectionLatach = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZKSyncReadZnode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        //客户端已经连接上了zk服务器
        if (Event.KeeperState.SyncConnected == event.getState()) {
            //Event.EventType.None == 如果 watcher 监视器没有监听到任何操作
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                zkConnectionLatach.countDown();
            }
            //当zk节点的子节点发生变化的时候
            if (Event.EventType.NodeChildrenChanged == event.getType()) {
                try {
                    /**
                     * @param String path ： znode节点的路径
                     * @param boolean watch ：
                     *  如果 true 表示使用默认的 watcher，也就是 new ZooKeeper(String connectString, int sessionTimeout, Watcher watcher) 时候的那个 watcher 对象。
                     */
                    //由于watcher通知是一次性的，既一旦触发一次通知后，该watcher就失效了，因此客户端需要反复注册watcher
                    List<String> children = zooKeeper.getChildren(event.getPath(), true);
                    System.out.println(children);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void syncReadZnodeChildren() throws Exception {
        //临时节点不能有child 子节点
        zooKeeper.create("/zk-test", "Rtest".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create("/zk-test/test1", "Ctest1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        List<String> children = zooKeeper.getChildren("/zk-test", new ZKSyncReadZnode());
        System.out.println(children);
        zooKeeper.create("/zk-test/test2", "Ctest2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public static void main(String[] args) throws Exception {
        zkConnectionLatach.await();
        syncReadZnodeChildren();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
