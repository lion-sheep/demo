package test.demo.tree;

import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author maguowei
 * @desc 深度优先遍历树
 * @date 2018/4/15 下午12:23
 */
public class DepthFirstSearch<T extends ITree> {

    private Map<String, T> nodeIdMap;
    private Map<String, List<T>> parentIdMap;

    //初始化一个树结构
    public static <T extends ITree> DepthFirstSearch<T> create(List<T> trees) {
        if (CollectionUtils.isEmpty(trees)) {
            throw new IllegalArgumentException("树不能为空");
        }
        return new DepthFirstSearch<>(trees);
    }

    public DepthFirstSearch(List<T> trees) {
        this.nodeIdMap = trees.stream().collect(Collectors.toMap(ITree::getTreeNodeId, Function.identity()));
        this.parentIdMap = trees.stream().collect(Collectors.groupingBy(ITree::getTreeNodeParentId));
    }

    public static void main(String[] args) {

    }

    private TreeRelation depthFirstTraverse(Map<String, T> nodeIdMap, Map<String, List<T>> parentIdMap) {
        int level = 0;
         //获取根节点
         T root = parentIdMap.get("").get(0);
         //基于树的深度优先，遍历树的各个节点
        List<T> secondTreeNodeList = parentIdMap.get(root.getTreeNodeId());
        if(CollectionUtils.isEmpty(secondTreeNodeList)){
            //包装后的root根节点
            TreeRelation rootTreeRelation = new TreeRelation(root,level++);
            //包装后的root根节点的直接子节点List
            List<TreeRelation> rootChildTreeRelation = secondTreeNodeList.stream().map(e -> new TreeRelation(e)).collect(Collectors.toList());
            rootTreeRelation.setChildren(rootChildTreeRelation);

            //当前要遍历的这一层的所有节点，放入  currentQueue
            Queue<TreeRelation> currentLevel = new LinkedList<>();
            currentLevel.addAll(rootChildTreeRelation);
            Queue<TreeRelation> nextLevel = new LinkedList<>();
            while (currentLevel.size() != 0){
                //遍历当前层的所有节点，并设置树深
                TreeRelation currentTraverseNode = currentLevel.poll();
                currentTraverseNode.setLevel(level);
                //获取当前节点的所有直接子节点List
                List<T> currentTraverseNodeChildrens = parentIdMap.get(currentTraverseNode.getT().getTreeNodeId());
                if(CollectionUtils.isEmpty(currentTraverseNodeChildrens)){
                    //获取当前遍历节点的直接子节点包装list
                    List<TreeRelation> currentTreeRelationChildrens = currentTraverseNodeChildrens.stream()
                            .map(e -> new TreeRelation(e)).collect(Collectors.toList());
                    currentTraverseNode.setChildren(currentTreeRelationChildrens);
                    //会将当前遍历所在的层上的所有节点的直接子节点都放入 nextLevel，也就是是当前层节点的所有直接子节点的队列。
                    //比如当前层有元素 B、C 。B有直接子节点 D、E ；有直接子节点 F、G 。那么 nextLevel队列中就是 D-E-F-G
                    nextLevel.addAll(currentTreeRelationChildrens);
                }
                if(currentLevel.size() == 0){//如果当前层遍历完毕
                    //会将当前层上所有节点的子节点list，
                    currentLevel.addAll(nextLevel);
                    //清空马上要遍历的当前层节点的直接子节点队列
                    nextLevel.clear();
                    //马上遍历下一层
                    level++;
                }
            }
            //当到达最后一个叶子节点的时候，获取这可树的总深度
            rootTreeRelation.setTreeDepth(level);
            return rootTreeRelation;
        }else{
            return new TreeRelation(root,null,level);
        }
    }

}
