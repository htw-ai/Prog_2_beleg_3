package sample;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by dudzik on 14.12.14.
 */
public class Graph {
    private HashMap<String, Node> nodes = new HashMap<String, Node> ();

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
     */
    public void route_breadthFirstSearch(Node from_node, Node to_node){
/*
BFS(start_node, goal_node) {
 for(all nodes i) visited[i] = false; // anfangs sind keine Knoten besucht
 queue.push(start_node);              // mit Start-Knoten beginnen
 visited[start_node] = true;
 while(! queue.empty() ) {            // solange queue nicht leer ist
  node = queue.pop();                 // erstes Element von der queue nehmen
  if(node == goal_node) {
   return true;                       // testen, ob Ziel-Knoten gefunden
  }
  foreach(child in expand(node)) {    // alle Nachfolge-Knoten, ...
   if(visited[child] == false) {      // ... die noch nicht besucht wurden ...
    queue.push(child);                // ... zur queue hinzufügen...
    visited[child] = true;            // ... und als bereits gesehen markieren
   }
  }
 }
 return false;                        // Knoten kann nicht erreicht werden
}
 */
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for (HashMap.Entry<String, Node> entry : nodes.entrySet()) {
            str.append("node name:").append(entry.getKey()).append("\n");
            str.append(entry.getValue().toString());
            str.append("\n-----------------------\n");
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
            firstNode.addEdge(secondNode, new Edge(rand.nextInt(5)));
        }
        return graph;
    }
}
