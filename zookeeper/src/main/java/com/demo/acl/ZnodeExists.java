package com.demo.acl;

import com.demo.callback.ZnodeStatCallback;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/30 下午2:37
 */
public class ZnodeExists implements Watcher {

    private static ZooKeeper zooKeeper;
    private static CountDownLatch zkconnected = new CountDownLatch(1);

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZnodeExists());
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
            if (Event.EventType.NodeCreated == event.getType()) {
                System.out.println("znode(" + event.getPath() + ")" + "created ");
                //重新注册 watcher 监听 znode 节点变化
                zooKeeper.exists(event.getPath(), true, new ZnodeStatCallback(), "pass ctx h3");
            }
            if (Event.EventType.NodeDeleted == event.getType()) {
                System.out.println("znode(" + event.getPath() + ")" + "deleted ");
                //重新注册 watcher 监听 znode 节点变化
                zooKeeper.exists(event.getPath(), true, new ZnodeStatCallback(), "pass ctx h3");
            }
            if (Event.EventType.NodeDataChanged == event.getType()) {
                System.out.println("znode(" + event.getPath() + ")" + "data changed ");
                //重新注册 watcher 监听 znode 节点变化
                zooKeeper.exists(event.getPath(), true, new ZnodeStatCallback(), "pass ctx h3");
            }
        }
    }

    public static void isZnodeExists() throws Exception {
        String path = "/zk-test";
        Stat stat1 = zooKeeper.exists(path, true);
        System.out.println(stat1);
        String s1 = zooKeeper.create(path, "Rtest".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s1);
        Stat stat2 = zooKeeper.setData(path, "RRtest".getBytes(), -1);
        System.out.println(stat2);
        String s2 = zooKeeper.create(path + "/test1", "test1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s2);
        zooKeeper.delete(path + "/test1", -1);
        zooKeeper.delete(path, -1);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        zkconnected.await();
        isZnodeExists();
    }
}
