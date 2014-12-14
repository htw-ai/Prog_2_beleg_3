package sample;

import java.util.HashMap;

/**
 * Created by dudzik on 14.12.14.
 */
public class Node {
    public HashMap<Node, Edge> edges = new HashMap<Node, Edge>();

    public void addEdge(Node node, Edge edge) {
        node.edges.put(this, edge);
        this.edges.put(node, edge);
    }

    public void deleteEdge(Node node){
        node.edges.remove(this);
        this.edges.remove(node);
    }

    public void deleteAllEdges(){
        for (HashMap.Entry<Node, Edge> entry : edges.entrySet()) {
            deleteEdge(entry.getKey());
        }
    }
}
