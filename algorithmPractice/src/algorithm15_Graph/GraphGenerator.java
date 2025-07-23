package algorithm15_Graph;

import algorithm00_common.GraphEdge;
import algorithm00_common.GraphNode;

public class GraphGenerator {
    public static Graph createGraph(Integer[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];
            if(!graph.nodes.containsKey(from)) {
                graph.nodes.put(from,new GraphNode(from));
            }
            if(!graph.nodes.containsKey(to)) {
                graph.nodes.put(to,new GraphNode(to));
            }
            GraphNode fromNode = graph.nodes.get(from);
            GraphNode toNode = graph.nodes.get(to);
            GraphEdge newEdge = new GraphEdge(weight,fromNode,toNode);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            fromNode.edges.add(newEdge);
            graph.edges.add(newEdge);
        }
        return graph;
    }
}
