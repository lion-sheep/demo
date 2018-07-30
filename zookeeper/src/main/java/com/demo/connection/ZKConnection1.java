package com.demo.connection;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author maguowei
 * @desc
 * @date 2018/7/22 上午12:38
 */
public class ZKConnection1 implements Watcher {
    /**
     * CountDownLatch是一个同步工具类，用来协调多个线程之间的同步，或者说起到线程之间的通信（而不是用作互斥的作用）
     * CountDownLatch能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。使用一个计数器进行实现。计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。
     * 当计数器的值为0时，表示所有的线程都已经完成了任务，然后在CountDownLatch上等待的线程就可以恢复执行任务。
     * -------------------------------------------------------------------------------------------------------------------------------------------------------
     * CountDownLatch典型用法1：某一线程在开始运行前等待n个线程执行完毕。将CountDownLatch的计数器初始化为n new CountDownLatch(n) ，
     * 每当一个任务线程执行完毕，就将计数器减1 countdownlatch.countDown()，当计数器的值变为0时，在CountDownLatch上 await() 的线程就会被唤醒。
     * <p>
     * CountDownLatch典型用法2：实现多个线程开始执行任务的最大并行性。注意是并行性，不是并发，强调的是多个线程在某一时刻同时开始执行。类似于赛跑，将多个线程放到起点，
     * 等待发令枪响，然后同时开跑。做法是初始化一个共享的CountDownLatch(1)，将其计数器初始化为1，多个线程在开始执行任务前首先 coundownlatch.await()，
     * 当主线程调用 countDown() 时，计数器变为0，多个线程同时被唤醒。
     * -------------------------------------------------------------------------------------------------------------------------------------------------------
     * CountDownLatch的不足
     * CountDownLatch是一次性的，计数器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，当CountDownLatch使用完毕后，它不能再次被使用。
     */
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        System.out.println("receive watched event : " + event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            //当 zk处于连接状态的时候，也就是客户端已经创建了与zk的tcp链接的时候，计数器 -1 ，也就是说创建zk链接的异步线程已经执行完毕了，这时候唤醒主线程
            connectedSemaphore.countDown();
        }
    }

    private static void generateZKConnection() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZKConnection1());
        System.out.println(zooKeeper.getState());
        try {
            //让当前主线程阻塞，等待  new ZooKeeper 这个异步线程创建完 tcp 链接返回后，再进行后续操作。
            connectedSemaphore.await();
        } catch (Exception e) {
            System.out.println("zookeeper session established");
        }
    }

    public static void main(String[] args) throws Exception {
        generateZKConnection();
    }
}
