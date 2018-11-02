package com.example.elsticjoblitedynamic.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.example.elsticjoblitedynamic.job.pojo.DataRepository;
import com.example.elsticjoblitedynamic.job.pojo.Foo;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 流式作业，每次调度触发的时候都会先调fetchData获取数据，如果获取到了数据再调度processData方法处理数据。DataflowJob在运行时有两种方式，流式的和非流式的，通过属性streamingProcess控制，如果是基于Spring XML的配置方式则是streaming-process属性，boolean类型。当作业配置为流式的时候，每次触发作业后会调度一次fetchData获取数据，如果获取到了数据会调度processData方法处理数据，处理完后又继续调fetchData获取数据，再调processData处理，如此循环，就像流水一样。直到fetchData没有获取到数据或者发生了重新分片才会停止。代码实现部分可参考数据流执行器 com.dangdang.ddframe.job.executor.type.DataflowJobExecutor。
 */

public class MyDataFlowJob implements DataflowJob<Foo> {

    private DataRepository dataRepository = new DataRepository();

    /**
     * 获取待处理数据.
     *
     * @param shardingContext 分片上下文
     * @return 待处理的数据集合
     */
    @Override
    public List<Foo> fetchData(ShardingContext shardingContext) {
        System.out.println("-------------------------------------fetchData: " + shardingContext.getShardingParameter()
                + "---------------------------------------------");
        System.out.println(shardingContext.getShardingParameter());
        List<Foo> result = dataRepository.findTodoData(shardingContext.getShardingParameter());
        System.out.println("++++++++++++++++++++++");
        System.out.println(result);
        System.out.println("++++++++++++++++++++++");
        System.out.println(String.format(
                "Item: %s | Time: %s | Thread: %s | %s | count: %d",
                shardingContext.getShardingItem(),
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                Thread.currentThread().getId(),
                "DATAFLOW FETCH",
                CollectionUtils.isEmpty(result) ? 0 : result.size()));
        return result;
    }

    /**
     * 处理数据.
     *
     * @param shardingContext 分片上下文
     * @param data 待处理数据集合
     */
    @Override
    public void processData(ShardingContext shardingContext, List<Foo> data) {
        System.out.println("----------------------------processData: " + shardingContext.getShardingParameter() + "----------------------------");
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s | count: %d",
                shardingContext.getShardingItem(),
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                Thread.currentThread().getId(), "DATAFLOW PROCESS",
                CollectionUtils.isEmpty(data) ? 0 : data.size()));
    }
}
