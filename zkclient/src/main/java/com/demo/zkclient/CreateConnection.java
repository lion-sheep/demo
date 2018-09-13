package com.demo.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/30 下午4:42
 */
public class CreateConnection {

    public static void craeateConnectionOne() {
        ZkClient zkClient = new ZkClient("localhost:2181");
        System.out.println(zkClient);
    }

    public static void craeateConnectionTow() {
        ZkClient zkClient = new ZkClient("localhost:2181", 5000);
        System.out.println(zkClient);
    }

    public static void craeateConnectionThree() {
        ZkClient zkClient = new ZkClient("localhost:2181", 5000);
        System.out.println(zkClient);
    }
}
