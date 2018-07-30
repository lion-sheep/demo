package com.demo.callback;

import org.apache.zookeeper.AsyncCallback;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/25 下午5:24
 */
public class DeleteZnodeCallback implements AsyncCallback.VoidCallback {

    @Override
    /**
     * @author xiaoma
     * @date 2018/7/25 下午5:27
     * @param rc ： Result code 服务端响应码。客户端可以从这个响应码中识别出 api 调用的结果。
     *           0 <ok> -- 接口调用成功  -4 <ConnectionLoss> -- 客户端与服务器连接已经断开  -110 <NodeExists> -- 指定节点已经存在  -112 <SessionExpired> -- 会话已经过期
     * @param path : znode 节点的路径
     * @param ctx ：context object 上下文对象，用户在创建znode节点的时候传入一个object对象，在回调参数中可以接受到并进行进一步处理
     * @return void
     */

    public void processResult(int rc, String path, Object ctx) {
        System.out.println("Result Code : " + rc + " path : " + path + " context object ：[ " + ctx + " ] ");
    }
}
