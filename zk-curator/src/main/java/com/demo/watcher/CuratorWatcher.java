package com.demo.watcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * @author xiaoma
 * @desc
 * @date 2018/10/18 下午7:50
 */
@Component
public class CuratorWatcher {

    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private ExecutorService pool;

    public void makeNodeCache(String path) throws Exception {
        // Node Cache：监视一个结点的创建、更新、删除，并将结点的数据缓存在本地。
        // dataIsCompressed if true, data in the path is compressed
        boolean dataIsCompressed = false;
        NodeCache nodeCache = new NodeCache(curatorFramework, path, dataIsCompressed);
        // Start the cache. The cache is not started automatically. You must call this method.
        nodeCache.start(true); // 这个监视不是默认开启的需要手动开启。
        nodeCache.getListenable().addListener(() -> {
            // 触发事件为创建节点和更新节点，在删除节点的时候并不触发此操作
            System.out.println("---------------------------------------");
            System.out.println(" NodeCache 触发事件为创建节点和更新节点，在删除节点的时候并不触发此操作 ");
            System.out.println("监听节点路径=====" + nodeCache.getCurrentData().getPath());
            // nodeCache.getCurrentData().getData() zk 节点中存储的 gzip 压缩后的数据,将其放入一个 byte input 流
            //            ByteArrayInputStream in = new ByteArrayInputStream(nodeCache.getCurrentData().getData());
            //            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //            GZIPInputStream gzipIn = new GZIPInputStream(in);
            //            byte[] bytes = new byte[512];
            //            int n;
            //            if((n = gzipIn.read(bytes)) >= 0){
            //                out.write(bytes,0,n);
            //            }
            //            gzipIn.close();
            System.out.println("数据=====" + new String(nodeCache.getCurrentData().getData()));
            System.out.println("状态=====" + nodeCache.getCurrentData().getStat());
            System.out.println("---------------------------------------");
        }, pool);
        Thread.sleep(1000);
        //        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //        GZIPOutputStream gzipOut = new GZIPOutputStream(out);
        //        gzipOut.write("1111".getBytes());
        //        gzipOut.close();
        curatorFramework.create().forPath(path, "11111".getBytes());

        Thread.sleep(1000);
        curatorFramework.setData().forPath(path, "22222".getBytes());

        Thread.sleep(1000);
        curatorFramework.delete().forPath(path);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public void makePathChildrenCache(String path) throws Exception {
        // PathChildrenCache ：监视一个路径下1）孩子结点的创建、2）删除，3）以及结点数据的更新。产生的事件会传递给注册的PathChildrenCacheListener。
        // cacheData if true, node contents are cached in addition to the stat 是否接受节点数据内容
        boolean cacheData = true;
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, cacheData);
        pathChildrenCache.getListenable().addListener((client, event) -> {
            ChildData childData = event.getData();//获取当前节点对象本身
            if (childData == null) {
                System.out.println("No data in event[" + event + "]");
            } else {
                System.out.println("Receive event: "
                        + "type=[" + event.getType() + "]"
                        + ", path=[" + childData.getPath() + "]"
                        + ", data=[" + new String(childData.getData()) + "]"
                        + ", stat=[" + childData.getStat() + "]");
            }
        }, pool);
        Thread.sleep(1000);
        curatorFramework.create().forPath(path + "/xx", "111".getBytes());
        Thread.sleep(1000);
        curatorFramework.delete().forPath(path + "/xx");
        Thread.sleep(1000);
        curatorFramework.setData().forPath(path, "121".getBytes());
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void makeTreeCache(String path) throws Exception {
        TreeCache treeCache = new TreeCache(curatorFramework, path);
        treeCache.getListenable().addListener((client, event) -> {
            ChildData childData = event.getData();//获取当前节点对象本身
            if (childData == null) {
                System.out.println("No data in event[" + event + "]");
            } else {
                System.out.println("Receive event: "
                        + "type=[" + event.getType() + "]"
                        + ", path=[" + childData.getPath() + "]"
                        + ", data=[" + new String(childData.getData()) + "]"
                        + ", stat=[" + childData.getStat() + "]");
            }
        }, pool);

        Thread.sleep(1000);
        curatorFramework.create().forPath(path, "123".getBytes());
        Thread.sleep(1000);
        curatorFramework.create().forPath(path + "/xx", "111".getBytes());
        Thread.sleep(1000);
        curatorFramework.delete().forPath(path + "/xx");
        Thread.sleep(1000);
        curatorFramework.setData().forPath(path, "456".getBytes());
        Thread.sleep(1000);
        curatorFramework.delete().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
