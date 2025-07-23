package algorithm15_Graph;

import algorithm00_common.GraphEdge;
import algorithm00_common.GraphNode;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    public HashMap<Integer, GraphNode> nodes;
    public HashSet<GraphEdge> edges;

    public Graph(){
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
