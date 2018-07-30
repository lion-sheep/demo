package com.demo.read;

import org.apache.zookeeper.KeeperException;
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
public class ZKSyncReadZnodeData implements Watcher {

    private static CountDownLatch zkconnected = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZKSyncReadZnodeData());
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
            System.out.println(event.getType());
            if (Event.EventType.NodeDataChanged == event.getType()) {
                try {
                    Stat stat = new Stat();
                    //由于watcher通知是一次性的，既一旦触发一次通知后，该watcher就失效了，因此客户端需要反复注册watcher
                    byte[] data = zooKeeper.getData(event.getPath(), true, stat);
                    System.out.println(new String(data) + "  " + stat);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void syncGetZnodeData() {
        Stat stat = new Stat();
        try {
            byte[] data = zooKeeper.getData("/zk-test", new ZKSyncReadZnodeData(), stat);
            System.out.println(new String(data) + "  " + stat);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            zkconnected.await();
            syncGetZnodeData();
            //version=-1 就是告诉zk服务器，客户端需要基于数据的最新版本进行更新操作
            zooKeeper.setData("/zk-test", "test".getBytes(), -1);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
