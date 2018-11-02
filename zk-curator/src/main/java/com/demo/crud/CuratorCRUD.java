package com.demo.crud;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * @author xiaoma
 * @desc
 * @date 2018/10/18 下午6:14
 */
@Component
public class CuratorCRUD {

    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private ExecutorService pool;

    public void syncCreateNode(String path, String content) throws Exception {
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(path, content.getBytes());
    }

    public void syncCreateChildrenNode(String path, String content) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded().forPath(path, content.getBytes());
    }

    public void asyncCreateNode(String path, String content) throws Exception {
        curatorFramework.create().withMode(CreateMode.PERSISTENT).inBackground((cf, ce) -> {
            System.out.println("asynchronous create node ");
            System.out.println("code:" + ce.getResultCode());
            System.out.println("type:" + ce.getType());
            System.out.println("线程为:" + Thread.currentThread().getName());
        }, pool).forPath(path, content.getBytes());
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void syncGetNode(String path) throws Exception {
        byte[] bytes = curatorFramework.getData().forPath(path);
        System.out.println(new String(bytes));
    }

    public void asyncGetNode(String path) throws Exception {
        curatorFramework.getData().inBackground((CuratorFramework cf, CuratorEvent ce) -> {
            System.out.println("asynchronous get node ");
            System.out.println("code:" + ce.getResultCode());
            System.out.println("type:" + ce.getType());
            System.out.println("node's value:" + new String(ce.getData()));
            System.out.println("线程为:" + Thread.currentThread().getName());
        }, pool).forPath(path);
        Thread.sleep(10000);
    }

    public void syncUpdateNode(String path, String content) throws Exception {
        curatorFramework.setData().forPath(path, content.getBytes());
    }

    public void asyncUpdateNode(String path, String content) throws Exception {
        curatorFramework.setData().inBackground((CuratorFramework cf, CuratorEvent ce) -> {
            System.out.println("asynchronous update node ");
            System.out.println("code:" + ce.getResultCode());
            System.out.println("type:" + ce.getType());
            System.out.println("线程为:" + Thread.currentThread().getName());
        }, pool).forPath(path, content.getBytes());
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void syncDeleteOneNode(String path) throws Exception {
        curatorFramework.delete().forPath(path);
    }

    public void asyncDeleteOneNode(String path) throws Exception {
        curatorFramework.delete().inBackground((CuratorFramework cf, CuratorEvent ce) -> {
            System.out.println("asynchronous delete node ");
            System.out.println("code:" + ce.getResultCode());
            System.out.println("type:" + ce.getType());
            System.out.println("线程为:" + Thread.currentThread().getName());
        }, pool).forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void syncDeleteNodeAndChildren(String path) throws Exception {
        curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);
    }

    public void asyncDeleteNodeAndChildren(String path) throws Exception {
        curatorFramework.delete().deletingChildrenIfNeeded().inBackground((CuratorFramework cf, CuratorEvent ce) -> {
            System.out.println("asynchronous delete node and node's chrildrens ");
            System.out.println("code:" + ce.getResultCode());
            System.out.println("type:" + ce.getType());
            System.out.println("线程为:" + Thread.currentThread().getName());
        }, pool).forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
