package sample;

import java.util.HashMap;

/**
 * Created by dudzik on 14.12.14.
 */
public class Graph {
    private HashMap<String, Node> nodes = new HashMap<String, Node> ();

    public void addNode(String name){
        nodes.put(name, new Node());
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }

    public void deleteNode(String name){
        nodes.remove(name).deleteAllEdges();
    }

    // @todo
    public String toString(){
        return "";
    }

    // @todo generate random Graph
    public static Graph random(){
        return new Graph();
    }
}
