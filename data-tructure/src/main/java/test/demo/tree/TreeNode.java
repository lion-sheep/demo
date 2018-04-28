package test.demo.tree;

/**
 * @author maguowei
 * @date 2018/4/15 下午12:08
 */
public class TreeNode implements ITree{
     private String id;//本节点id
     private String name;//节点名称
     private String parentId;//父节点id

    public TreeNode() {
    }

    public TreeNode(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }

    @Override
    public String getTreeNodeId() {
        return this.id;
    }

    @Override
    public String getTreeNodeParentId() {
        return this.parentId;
    }

    @Override
    public String getTreeNodeName() {
        return this.name;
    }
}
