package com.demo.curd;

import com.demo.callback.CreateZnodeCallback;
import com.demo.callback.DeleteZnodeCallback;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author maguowei
 * @desc
 * @date 2018/7/23 上午11:49
 */
public class ZKcurd implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 50000, new ZKcurd());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            countDownLatch.countDown();
        }
    }

    public static void syncCreateZNode() throws Exception {
        countDownLatch.await();
        String path1 = zooKeeper.create("/zk-test1", "test1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode" + path1);
    }

    public static void asyncCreateZNode() throws Exception {
        countDownLatch.await();
        zooKeeper.create("/zk-test2",
                "test2".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                new CreateZnodeCallback(),
                "transfer context object hello");
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void syncDeleteZNode() throws Exception {
        countDownLatch.await();
        zooKeeper.delete("/zk-test1", 0);
    }

    public static void asyncDeleteZNode() throws Exception {
        countDownLatch.await();
        zooKeeper.delete("/zk-test2", 0, new DeleteZnodeCallback(), "transfer context object h2");
        Thread.sleep(3000);
    }

    public static void main(String[] args) {
        try {
            asyncDeleteZNode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
