package com.demo;

import com.demo.watcher.CuratorWatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WatcherTests {

    @Autowired
    private CuratorWatcher curatorWatcher;

    @Test
    public void nodeCacheTest() throws Exception {
        curatorWatcher.makeNodeCache("/nodeCache");
    }

    @Test
    public void PathChildrenCacheTest() throws Exception {
        curatorWatcher.makePathChildrenCache("/pathChildrenCache");
    }

    @Test
    public void treeCacheTest() throws Exception {
        curatorWatcher.makeTreeCache("/treeCache");
    }

    @Test
    public void ttest1() throws Exception {
        byte[] encode = Base64.getEncoder().encode("11111".getBytes("utf-8"));
        System.out.println(encode);
        byte[] decode = Base64.getDecoder().decode(encode);
        System.out.println(new String(decode));
    }
}
