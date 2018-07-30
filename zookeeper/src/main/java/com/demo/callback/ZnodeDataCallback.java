package com.demo.callback;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

/**
 * @author xiaoma
 * @desc
 * @date 2018/7/28 上午2:22
 */
public class ZnodeDataCallback implements AsyncCallback.DataCallback {
    @Override
    /**
     * @author xiaoma
     * @date 2018/7/28 上午2:23
     * @param rc ：Result code 服务端响应码。客户端可以从这个响应码中识别出 api 调用的结果。
     *      0 <ok> -- 接口调用成功  -4 <ConnectionLoss> -- 客户端与服务器连接已经断开  -110 <NodeExists> -- 指定节点已经存在  -112 <SessionExpired> -- 会话已经过期
     * @param path ：当前节点所在路径
     * @param ctx ：context object 上下文对象，用户在创建znode节点的时候传入一个object对象，在回调参数中可以接受到并进行进一步处理
     * @param data ：当前节点的数据信息
     * @param stat ：当前节点的状态信息
     * @return void
     */
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        System.out.println(" Result code : " + rc + " path : " + path + " context object : " + ctx + " data :" + new String(data) + "  " + stat);
    }
}
