package sample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by dudzik on 14.12.14.
 */

public class Graph {
    private HashMap<String, Node> nodes = new HashMap<String, Node> ();

    public int nodeCount(){
        return nodes.size();
    }

    public void addNode(String name){
        nodes.put(name, new Node(name));
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }

    public void deleteNode(String name){
        nodes.remove(name).deleteAllEdges();
    }

    /**
     * @todo irgendeinen weg finden
     * @return Node[]
     */
    public void route_depthFirstSearch(Node from_node, Node to_node){
/*
1  procedure DFS-iterative(G,v):
2      let S be a stack
3      S.push(v)
4      while S is not empty
5            v ← S.pop()
6            if v is not labeled as discovered:
7                label v as discovered
8                for all edges from v to w in G.adjacentEdges(v) do
9                    S.push(w)

 */
    }

    /**
     * @return Node[]
     * @todo den kurzesten weg finden
     */
    public boolean route_breadthFirstSearch(Node start_node, Node goal_node){
        HashMap<String, Boolean> visited = new HashMap<String, Boolean> ();
        Queue queue = new LinkedList();
        for (HashMap.Entry<String, Node> entry : nodes.entrySet()) {
            visited.put(entry.getKey(), false);
        }
        queue.add(start_node);
        visited.put(start_node.getName(), true);
        while(! queue.isEmpty() ) {
            Object node = queue.poll();
            if(node == goal_node) {
                return true;
            }
            // foreach(child in expand(node)) {    // alle Nachfolge-Knoten, ...
            //    if(visited[child] == false) {      // ... die noch nicht besucht wurden ...
            //        queue.push(child);                // ... zur queue hinzufügen...
            //        visited[child] = true;            // ... und als bereits gesehen markieren
            //    }
            // }
        }
        return false;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for (HashMap.Entry<String, Node> entry : nodes.entrySet()) {
            str.append(entry.getKey()).append(";\n");
            str.append(entry.getValue().toString());
            str.append(";;\n");
        }
        return str.toString();
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
            firstNode.addEdge(secondNode, rand.nextInt(5));
        }
        return graph;
    }

    public static Graph fromFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String str = new String(encoded);
        Graph graph = new Graph();
        String[] graph_info = str.split(";;");
        String[] node_info;
        Node firstNode;
        Node secondNode;
        // minus length because last element is always \n
        for (int i = 0; i < graph_info.length - 1; i++) {
            node_info = graph_info[i].replace("\n","").split(";");
            graph.addNode(node_info[0]);
            firstNode = graph.getNode(node_info[0]);
            for (int j = 1; j < node_info.length; j = j+2) {
                graph.addNode(node_info[j]);
                secondNode = graph.getNode(node_info[j]);
                firstNode.addEdge(secondNode, Integer.parseInt(node_info[j+1]));
            }
        }
        return graph;
    }

    public void toFile(String path) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        writer.print(toString());
        writer.close();
    }

}
