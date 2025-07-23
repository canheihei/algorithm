package algorithm15_Graph;

import algorithm00_common.GraphNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
图的宽度优先遍历
 */
public class BFS {
    public static void bfs(GraphNode start) {
        if(start == null) {
            return;
        }

        Queue<GraphNode> queue = new LinkedList<>();
        HashSet<GraphNode> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while(!queue.isEmpty()) {
            GraphNode cur = queue.poll();
            System.out.println(cur.value);
            for (GraphNode next : cur.nexts) {
                if(!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }
}
