package com.demo.callback;

import org.apache.zookeeper.AsyncCallback;

/**
 * @author maguowei
 * @desc
 * @date 2018/7/23 下午2:27
 */
public class CreateZnodeCallback implements AsyncCallback.StringCallback {

    @Override
    /**
     * @author xiaoma
     * @date 2018/7/25 下午3:21
     * @param rc ： Result code 服务端响应码。客户端可以从这个响应码中识别出 api 调用的结果。
     *             0 <ok> -- 接口调用成功  -4 <ConnectionLoss> -- 客户端与服务器连接已经断开  -110 <NodeExists> -- 指定节点已经存在  -112 <SessionExpired> -- 会话已经过期
     * @param ctx ：context object 上下文对象，用户在创建znode节点的时候传入一个object对象，在回调参数中可以接受到并进行进一步处理
     * @param name ：返回 znode节点的完整节点路径
     * @return void
     */
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("Result Code : " + rc + " path : " + path + " context object ：[ " + ctx + " ] " + " full path ：" + name);
    }
}
