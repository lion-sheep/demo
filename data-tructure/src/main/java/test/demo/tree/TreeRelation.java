package test.demo.tree;

import java.util.List;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/15 下午5:25
 */
public class TreeRelation<T extends ITree> {
    private T t;//tree节点对象
    private List<TreeRelation> children;//当前节点的所有直接子节点
    private int level;//节点在树的深度
    private int treeDepth;//获取整棵树的深度

    public TreeRelation() {
    }

    public TreeRelation(T t) {
        this.t = t;
    }

    public TreeRelation(T t, int level) {
        this.t = t;
        this.level = level;
    }

    public TreeRelation(T t, List<TreeRelation> children, int level) {
        this.t = t;
        this.children = children;
        this.level = level;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public List<TreeRelation> getChildren() {
        return children;
    }

    public void setChildren(List<TreeRelation> children) {
        this.children = children;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }
}