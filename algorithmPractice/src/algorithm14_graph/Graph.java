package algorithm14_graph;

import simpleDataNode.GraphEdge;
import simpleDataNode.GraphNode;

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
