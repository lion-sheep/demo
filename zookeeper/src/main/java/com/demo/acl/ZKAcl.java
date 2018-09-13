package com.demo.acl;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/30 下午3:37
 */
public class ZKAcl {

    public static void main(String[] args) throws Exception {
        ZooKeeper zk1 = new ZooKeeper("localhost:2181", 5000, null);
        zk1.addAuthInfo("digest", "ma:lion".getBytes());
        String s = zk1.create("/zk-test", "Rtest".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        System.out.println(s);
        ZooKeeper zk2 = new ZooKeeper("localhost:2181", 5000, null);
        byte[] data = zk2.getData("/zk-test", false, null);
        System.out.println(data);
    }
}
