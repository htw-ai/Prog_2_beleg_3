package sample;

import java.util.HashMap;

/**
 * Created by dudzik on 14.12.14.
 */
public class Node {
    /**
     * the list of all the edges a node has
     */
    public HashMap<Node, Edge> edges = new HashMap<>();

    /**
     * the name of the node
     */
    private String name;

    /**
     * x position of the node on the pane
     *
     * @todo move to NodeGUI class
     */
    private double posX;

    /**
     * y position of the node on the pane
     *
     * @todo move to NodeGUI class
     */
    private double posY;

    /**
     * @todo move to NodeGUI class
     *
     * @return x get the position of the node on the pane
     */
    public double getPosX() {
        return posX;
    }

    /**
     * set x position of the node on the pane
     *
     * @todo move to NodeGUI class
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * @todo move to NodeGUI class
     * @return  get the y position of the node on the pane
     */
    public double getPosY() {
        return posY;
    }


    /**
     * set the y position of the node on the pane
     *
     * @todo move to NodeGUI class
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * sets node name
     *
     * @param name node name
     */
    public Node (String name){
        this.name = name;
    }

    /**
     * gets the node name
     *
     * @return name
     */
    public String getName () {
        return name;
    }

    /**
     * adds edge to node
     * @param node node to which the edge is
     * @param rating rating of the edge
     */
    public void addEdge(Node node, int rating) {
        Edge edge = new Edge(rating);
        node.edges.put(this, edge);
        this.edges.put(node, edge);
    }

    /**
     * removes an edge to a node
     *
     * @param node
     */
    public void deleteEdge(Node node){
        node.edges.remove(this);
        this.edges.remove(node);
    }

    /**
     * converts node with its edges to a string
     *
     * @return String representation of node
     */
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

    /**
     * removes all edges from node
     */
    public void deleteAllEdges(){
        for (HashMap.Entry<Node, Edge> entry : edges.entrySet()) {
            //deleteEdge(entry.getKey());
            entry.getKey().edges.remove(this);
        }
        edges.clear();
    }
}
