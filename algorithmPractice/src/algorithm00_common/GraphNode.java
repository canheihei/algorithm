package algorithm00_common;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphNode {
    public int value;
    public int in;
    public int out;
    public ArrayList<GraphNode> nexts;
    public ArrayList<GraphEdge> edges;

    public GraphNode(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
