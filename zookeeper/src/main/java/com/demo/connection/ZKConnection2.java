package com.demo.connection;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author maguowei
 * @desc
 * @date 2018/7/23 上午11:13
 */
public class ZKConnection2 implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        System.out.println("receive zookeeper event : " + event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 4000, new ZKConnection2());
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                System.out.println("zookeeper session establishing");
            }
            long sessionId = zooKeeper.getSessionId();
            byte[] sessionPasswd = zooKeeper.getSessionPasswd();
            Thread.sleep(5000);
            zooKeeper = new ZooKeeper("localhost:2181", 4000, new ZKConnection2(), sessionId, sessionPasswd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
