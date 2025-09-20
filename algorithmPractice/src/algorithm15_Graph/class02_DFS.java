package algorithm15_Graph;


import algorithm00_common.GraphNode;

import java.util.HashSet;
import java.util.Stack;

/*
图的深度优先遍历
 */
public class class02_DFS {
    public static void dfs(GraphNode node) {
        if(node == null) {
            return;
        }

        // 该栈一直存放当前路径
        Stack<GraphNode> stack = new Stack<>();
        HashSet<GraphNode> set = new HashSet<>();

        stack.add(node);
        set.add(node);

        System.out.println(node.value);
        while (!stack.isEmpty()) {
            GraphNode cur = stack.pop();
            for (GraphNode next : node.nexts) {
                if(!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }
}
