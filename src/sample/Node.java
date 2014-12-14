package sample;

import java.util.HashMap;

/**
 * Created by dudzik on 14.12.14.
 */
public class Node {
    public HashMap<Node, Edge> edges = new HashMap<Node, Edge>();

    public void addEdges(Node node, Edge edge) {
        this.edges.put(node, edge);
        node.addEdges(this, edge);
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
