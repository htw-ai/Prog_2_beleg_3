package sample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by dudzik on 14.12.14.
 */

public class Graph {
    private Map<String, Node> nodes = new HashMap<> ();

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
     * @todo return the path
     * @return Node[]
     */
    public Boolean route_depthFirstSearch(Node start_node, Node goal_node){
        HashSet<String> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        Node node;
        stack.push(goal_node);
        while(!stack.isEmpty()){
            node = stack.pop();
            if(visited.contains(node.getName())){
                if(start_node.getName() == node.getName()) return true;
                visited.add(node.getName());
                node.edges.forEach((Node n, Edge e) -> stack.push(n));
            }
        }
        return false;
    }

    /**
     * @return Node[]
     * @todo find the shortest path and return it
     */
    public boolean route_breadthFirstSearch(Node start_node, Node goal_node){
        HashMap<String, Boolean> visited = new HashMap<String, Boolean> ();
        Queue<Node> queue = new LinkedList<>();
        for (HashMap.Entry<String, Node> entry : nodes.entrySet()) {
            visited.put(entry.getKey(), false);
        }
        queue.add(start_node);
        visited.put(start_node.getName(), true);
        Node node;
        while(! queue.isEmpty() ) {
            node = queue.poll();
            if(node == goal_node) {
                return true;// TODO: make "snapshot" of the route and continue
            }
            node.edges.forEach((Node n, Edge e) -> {
                if(visited.get(n.getName()) == false) {
                   queue.add(n);
                   visited.put(n.getName(), true);
                }
            });
        }
        // TODO: compare "snapshots" and return the shortest
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

    /**
     *
     * @param nodeCount amount of nodes. min 2 nodes
     * @param edgeCount
     */
    public void random(int nodeCount, int edgeCount){
        nodeCount = nodeCount < 2 ? 2 : nodeCount;
        //Graph  = new Graph();
        nodes = new HashMap<>();
        Random rand = new Random();
        for (int i = 0; i < nodeCount ; i++)
            addNode(Integer.toString(i));

        for (int i = 0; i < edgeCount ; i++) {

            int firstNodeName = rand.nextInt(nodeCount);
            int secondNodeName = rand.nextInt(nodeCount);
            while(secondNodeName == firstNodeName)
                secondNodeName = rand.nextInt(nodeCount);

            Node firstNode = getNode(String.valueOf(firstNodeName));
            Node secondNode = getNode(String.valueOf(secondNodeName));
            firstNode.addEdge(secondNode, rand.nextInt(5));
        }
        //return graph;
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
        }

        // minus length because last element is always \n
        for (int i = 0; i < graph_info.length - 1; i++) {
            node_info = graph_info[i].replace("\n","").split(";");
            firstNode = graph.getNode(node_info[0]);
            for (int j = 1; j < node_info.length; j = j+2) {
                secondNode = graph.getNode(node_info[j]);
                firstNode.addEdge(secondNode, Integer.parseInt(node_info[j+1]));
            }
        }
        System.out.print(graph.toString());
        return graph;
    }

    public void toFile(String path) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        writer.print(toString());
        writer.close();
    }

    public Map<String, Node> getNodes(){
        return nodes;
    }
}
