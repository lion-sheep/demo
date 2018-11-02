package com.demo.watcher;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoma
 * @desc
 * @date 2018/10/22 下午1:27
 */
public class WatcherListener implements Watcher {

    private static CountDownLatch connnectFlag = new CountDownLatch(1);
    private static ZooKeeper zk;
    private static Logger log = Logger.getLogger(WatcherListener.class);

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            System.out.println("zk client connected ......");
            connnectFlag.countDown();
        }
        System.out.println(event.getType());
        if (Event.EventType.NodeDataChanged == event.getType()) {
            System.out.println("node data changed ... ");
            try {
                zk.getData(event.getPath(), true, new Stat());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 当服务器端session过期，操作临时节点删除的时候，手动重新建立节点。
        if (Event.KeeperState.Expired == event.getState()) {
            try {
                zk.create("/test1", "test1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        zk = new ZooKeeper("localhost:2181", 12000, new WatcherListener());
        connnectFlag.await();
        zk.create("/test1", "test1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(10000);
        Stat stat = new Stat();
        byte[] data = zk.getData("/test1", true, stat);
        System.out.println(new String(data));
        Thread.sleep(30000);
        System.out.println(new String(data));
    }
}