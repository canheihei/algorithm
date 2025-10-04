package simpleDataNode;

import java.util.ArrayList;
import java.util.List;

public class Category {
    int id;
    int parentId;
    String name;
    List<Category> children;

    public Category(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}
