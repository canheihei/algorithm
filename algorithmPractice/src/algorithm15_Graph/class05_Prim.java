package algorithm15_Graph;

import algorithm00_common.GraphEdge;
import algorithm00_common.GraphNode;

import java.util.*;

public class class05_Prim {
    public static class EdgeComparator implements Comparator<GraphEdge> {
        @Override
        public int compare(GraphEdge o1, GraphEdge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<GraphEdge> primMST(Graph class00Graph) {
        PriorityQueue<GraphEdge> priorityQueue = new PriorityQueue<>(new EdgeComparator());

        HashSet<GraphNode> nodeSet = new HashSet<>();

        Set<GraphEdge> result = new HashSet<>();

        for (GraphNode node : class00Graph.nodes.values()) {
            if(!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (GraphEdge edge : node.edges) {
                    priorityQueue.add(edge);
                }
                while (!priorityQueue.isEmpty()) {
                    GraphEdge edge = priorityQueue.poll();
                    GraphNode toNode = edge.to;
                    if (!nodeSet.contains(toNode)) {
                        nodeSet.add(toNode);
                        result.add(edge);
                        for (GraphEdge nextEdge : toNode.edges) {
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
            break;
        }
        return result;
    }
}
