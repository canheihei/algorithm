package algorithm15_Graph;

import algorithm00_common.GraphNode;

import java.util.*;

/*
拓扑排序：有先后顺序的任务中，找到一个合理的执行顺序
 */
public class sotedTopology {
    public static List<GraphNode> sotedTopology(Graph graph) {
        HashMap<GraphNode,Integer> inMap = new HashMap<>();

        Queue<GraphNode> zeroInQueue = new LinkedList<>();
        for (GraphNode node : graph.nodes.values()) {
            inMap.put(node,node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        List<GraphNode> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            GraphNode cur = zeroInQueue.poll();
            result.add(cur);
            for (GraphNode next : cur.nexts) {
                inMap.put(next,inMap.get(next) - 1);
                if(inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }
}
