package algorithm14_graph;

import simpleDataNode.GraphEdge;
import simpleDataNode.GraphNode;

import java.util.*;

public class class04_Kruskal {
    public static class UnionFind {
        private HashMap<GraphNode,GraphNode> fatherMap;
        private HashMap<GraphNode,Integer> sizeMap;

        public UnionFind() {
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        public void makeSets(Collection<GraphNode> nodes) {
            fatherMap.clear();;
            sizeMap.clear();
            for (GraphNode node : nodes) {
                fatherMap.put(node,node);
                sizeMap.put(node,1);
            }
        }

        private GraphNode findFather(GraphNode n) {
            Stack<GraphNode> path = new Stack<>();
            while (n != fatherMap.get(n)) {
                path.add(n);
                n = fatherMap.get(n);
            }

            while (!path.isEmpty()) {
                fatherMap.put(path.pop(),n);
            }
            return n;
        }

        public boolean isSameSet(GraphNode a, GraphNode b) {
            return findFather(a) == findFather(b);
        }

        public void union(GraphNode a, GraphNode b) {
            if (a == null || b == null) {
                return;
            }
            GraphNode aDai = findFather(a);
            GraphNode bDai = findFather(b);
            if(aDai != bDai) {
                int aSetSize = sizeMap.get(aDai);
                int bSetSize = sizeMap.get(bDai);
                if(aSetSize <= bSetSize) {
                    fatherMap.put(aDai,bDai);
                    sizeMap.put(bDai,aSetSize + bSetSize);
                    sizeMap.remove(aDai);
                }else {
                    fatherMap.put(bDai,aDai);
                    sizeMap.put(aDai,aSetSize + bSetSize);
                    sizeMap.remove(bDai);
                }
            }
        }
    }

    public static class EdgeComparator implements Comparator<GraphEdge> {
        @Override
        public int compare(GraphEdge o1, GraphEdge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<GraphEdge> kruskalMST(Graph class00Graph) {
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(class00Graph.nodes.values());
        PriorityQueue<GraphEdge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for (GraphEdge edge : class00Graph.edges) {
            priorityQueue.add(edge);
        }
        Set<GraphEdge> result = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            GraphEdge edge = priorityQueue.poll();
            if (!unionFind.isSameSet(edge.from,edge.to)) {
                result.add(edge);
                unionFind.union(edge.from,edge.to);
            }
        }
        return result;
    }
}
