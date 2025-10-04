package algorithm14_graph;

import simpleDataNode.GraphEdge;
import simpleDataNode.GraphNode;


// 通过二维矩阵构造图
public class class00_GraphGenerator {
    public static Graph createGraph(Integer[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];

            // 存放两端节点
            if(!graph.nodes.containsKey(from)) {
                graph.nodes.put(from,new GraphNode(from));
            }
            if(!graph.nodes.containsKey(to)) {
                graph.nodes.put(to,new GraphNode(to));
            }

            // 构造边
            GraphNode fromNode = graph.nodes.get(from);
            GraphNode toNode = graph.nodes.get(to);
            GraphEdge newEdge = new GraphEdge(weight,fromNode,toNode);
            fromNode.nexts.add(toNode);
            // 出入度更新
            fromNode.out++;
            toNode.in++;
            // 更新节点的直接边
            fromNode.edges.add(newEdge);
            graph.edges.add(newEdge);
        }
        return graph;
    }
}
