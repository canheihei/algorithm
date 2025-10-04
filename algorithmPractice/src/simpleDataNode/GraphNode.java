package simpleDataNode;

import java.util.ArrayList;

public class GraphNode {
    public int value;
    public int in;
    public int out;
    // 只记录直接邻居
    public ArrayList<GraphNode> nexts;
    // 只记录直接边
    public ArrayList<GraphEdge> edges;

    public GraphNode(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
