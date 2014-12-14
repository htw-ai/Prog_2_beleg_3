package sample;

/**
 * Created by dudzik on 14.12.14.
 */
public class Edge {
    public int rating;
    public Node node;

    public Edge(Node to_node, int rating){
        this.rating = rating;
        this.node = to_node;
    }
}
