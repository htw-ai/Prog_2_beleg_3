package sample;

import java.util.HashMap;
import java.util.Random;

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

    public static Graph random(int nodeCount, int edgeCount){
        Graph graph = new Graph();
        for (int i = 0; i < nodeCount ; i++) {
            graph.addNode(Integer.toString(i));
        }
        for (int i = 0; i < edgeCount ; i++) {
            Random rand = new Random();
            int firstNodeName = rand.nextInt(nodeCount);
            int secondNodeName = rand.nextInt(nodeCount);
            while(secondNodeName == firstNodeName){
                secondNodeName = rand.nextInt(nodeCount);
            }
            Node firstNode = graph.getNode(Integer.toString(firstNodeName));
            Node secondNode = graph.getNode(Integer.toString(secondNodeName));
            firstNode.addEdge(secondNode, new Edge(rand.nextInt(5)));
        }
        return graph;
    }
}
