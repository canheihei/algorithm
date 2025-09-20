package algorithm15_Graph;

import algorithm00_common.GraphEdge;
import algorithm00_common.GraphNode;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    // 点集
    public HashMap<Integer, GraphNode> nodes;
    // 边集
    public HashSet<GraphEdge> edges;

    public Graph(){
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
