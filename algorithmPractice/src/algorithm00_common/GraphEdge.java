package algorithm00_common;

public class GraphEdge {
    public int weight;
    public GraphNode from;
    public GraphNode to;

    public GraphEdge(int weight,GraphNode from,GraphNode to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
