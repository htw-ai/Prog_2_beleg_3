package sample;

import java.util.HashMap;

/**
 * Created by dudzik on 14.12.14.
 */
public class Node {
    public HashMap<Node, Edge> edges = new HashMap<Node, Edge>();
    private String name;
    private double posX;
    private double posY;

    public Node (String name){
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void addEdge(Node node, int rating) {
        Edge edge = new Edge(rating);
        node.edges.put(this, edge);
        this.edges.put(node, edge);
    }

    public void deleteEdge(Node node){
        node.edges.remove(this);
        this.edges.remove(node);
    }

    public String toString (){
        StringBuilder str = new StringBuilder();
        for (HashMap.Entry<Node, Edge> entry : edges.entrySet()) {
            str.append(entry.getKey().getName());
            str.append(";");
            str.append(Integer.toString(entry.getValue().getRating()));
            str.append(";");
            str.append("\n");
        }
        return str.toString();
    }

    public void deleteAllEdges(){
        for (HashMap.Entry<Node, Edge> entry : edges.entrySet()) {
            deleteEdge(entry.getKey());
        }
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
