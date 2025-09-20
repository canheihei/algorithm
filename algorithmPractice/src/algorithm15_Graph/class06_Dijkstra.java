package algorithm15_Graph;

import algorithm00_common.GraphEdge;
import algorithm00_common.GraphNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class class06_Dijkstra {
    public static HashMap<GraphNode,Integer> dijkstra1(GraphNode from) {
        HashMap<GraphNode,Integer> distanceMap = new HashMap<>();
        // 初始化出发节点
        distanceMap.put(from,0);
        // 用来记录已经选过的节点
        HashSet<GraphNode> selectedNodes = new HashSet<>();
        // 选择当前节点最小权重的边对应的节点
        GraphNode minNode = getMinDistanceAndUnselectedNode(distanceMap,selectedNodes);
        while (minNode != null) {
            int distance = distanceMap.get(minNode);
            for (GraphEdge edge : minNode.edges) {
                GraphNode toNode = edge.to;
                if(!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode,distance + edge.weight);
                } else {
                    distanceMap.put(edge.to,Math.min(distanceMap.get(toNode),distance + edge.weight));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap,selectedNodes);
        }
        return distanceMap;
    }

    private static GraphNode getMinDistanceAndUnselectedNode(HashMap<GraphNode, Integer> distanceMap, HashSet<GraphNode> toucchedNodes) {
        GraphNode minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<GraphNode, Integer> entry : distanceMap.entrySet()) {
            GraphNode node = entry.getKey();
            int distance = entry.getValue();
            if(!toucchedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

    public static class NodeRecord {
        public GraphNode node;
        public int distance;

        public NodeRecord(GraphNode node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        private GraphNode[] nodes;
        private HashMap<GraphNode,Integer> heapIndexMap;
        private HashMap<GraphNode,Integer> distanceMap;
        private int size;

        public NodeHeap(int size) {
            nodes = new GraphNode[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void addOrUpdateOrIgnore(GraphNode node,int distance) {
            if(inHeap(node)) {
                distanceMap.put(node,Math.min(distanceMap.get(node),distance));
                insertHeapify(node, heapIndexMap.get(node));
            }
            if(!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node,size);
                distanceMap.put(node,distance);
                insertHeapify(node,size++);
            }
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0],distanceMap.get(nodes[0]));
            swap(0,size - 1);
            heapIndexMap.put(nodes[size - 1],-1);
            distanceMap.remove(nodes[size - 1]);

            nodes[size - 1] = null;
            heapify(0,--size);
            return nodeRecord;
        }

        private void insertHeapify(GraphNode node, int index) {
            while(distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest,index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        private boolean isEntered(GraphNode node) {
            return heapIndexMap.containsKey(node);
        }

        private boolean inHeap(GraphNode node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1],index2);
            heapIndexMap.put(nodes[index2],index1);
            GraphNode tmp  = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }

    public static HashMap<GraphNode,Integer> dijkstra2(GraphNode head,int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head,0);
        HashMap<GraphNode,Integer> result = new HashMap<>();
        while(!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            GraphNode cur = record.node;
            int distance = record.distance;
            for (GraphEdge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to,edge.weight + distance);
            }
            result.put(cur,distance);
        }

        return result;
    }
}
