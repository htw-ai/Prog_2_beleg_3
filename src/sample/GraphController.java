package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by dudzik on 09.01.15.
 */
public class GraphController {
    /**
     * Textfield which has the name a node
     */
    public TextField nodeName;

    /**
     * Textfield which has the name to a node
     */
    public TextField toNodeName;

    /**
     * Textfield which has the name from a node
     */
    public TextField fromNodeName;

    /**
     * Textfield which has a edge rating in it
     */
    public TextField edgeRating;

    /**
     * Textfield which has contains a file path
     */
    public TextField filePath;

    /**
     * holds the canvas
     */
    public Pane graphCanvas;

    /**
     * initializes graph from autosave
     */
    private Graph graph = new Graph();

    /**
     * retrieves graph from autosave file
     *
     * @todo into own method to share with cli
     */
    private Graph graphFromAutosave () {
        Graph gGraph = new Graph();
        try {
            File file = new File(".graph");
            if(!file.exists()) file.createNewFile();
            gGraph = gGraph.fromFile(".graph");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gGraph;
    };

    /**
     * a random instance
     */
    private Random rnd = new Random();

    /**
     * listens to addNode and adds node to graph
     */
    public void addNode(ActionEvent actionEvent) {
        graph.addNode(nodeName.getText());
        rerenderGraph();
    }

    /**
     * listens to addNode and adds node to graph
     */
    public void removeNode(ActionEvent actionEvent) {
        graph.deleteNode(nodeName.getText());
        rerenderGraph();
    }

    /**
     * listens to addNode and adds node to graph
     */
    public void addEdge(ActionEvent actionEvent) {
        graph.getNode(fromNodeName.getText()).addEdge(graph.getNode(toNodeName.getText()), Integer.valueOf(edgeRating.getText()));
        rerenderGraph();
    }

    /**
     * listens to removeEdge and removes an edge from the graph
     */
    public void removeEdge(ActionEvent actionEvent) {
        graph.getNode(fromNodeName.getText()).deleteEdge(graph.getNode(toNodeName.getText()));
        rerenderGraph();
    }

    /**
     * listens to reset and initializes a new graph
     */
    public void reset(ActionEvent actionEvent) {
        graph = new Graph();
        rerenderGraph();
    }

    /**
     * listens to random and initializes a random graph
     */
    public void random(ActionEvent actionEvent) {
        Random rand = new Random();
        graph.random(rand.nextInt(50), rand.nextInt(50));

        // render two times
        rerenderGraph();
        rerenderGraph();
    }

    /**
     * listens to writeFile and writes graph to a file
     */
    public void writeFile(ActionEvent actionEvent) throws FileNotFoundException, UnsupportedEncodingException {
        graph.toFile(filePath.getText());
    }

    /**
     * listens to readFile and initializes graph from a file
     */
    public void readFile(ActionEvent actionEvent) throws IOException {
        graph = Graph.fromFile(filePath.getText());
        rerenderGraph();
        rerenderGraph();
    }

    /**
     * listens to loadAutosave and initializes graph from a file
     */
    public void fromAutosave(ActionEvent actionEvent) throws IOException {
        graph = graphFromAutosave();
        rerenderGraph();
        rerenderGraph();
    }

    /**
     * renders the the graph on the pane
     */
    private void rerenderGraph(){
        graphCanvas.getChildren().clear();
        Map<String, String> paths = new HashMap<>();

        for (Map.Entry<String, Node> mapEntry: graph.getNodes().entrySet()){
            Node node = mapEntry.getValue();

            // generate position
            if (node.getPosX() < 20) {
                int xValue = rnd.nextInt(new Double(graphCanvas.getWidth() - 40).intValue());
                int yValue = rnd.nextInt(new Double(graphCanvas.getHeight() - 60).intValue());
                node.setPosX(xValue + 20);
                node.setPosY(yValue + 20);
            }

            // UI elements
            Circle c = new Circle(4);
            //c.setFill(new Color(119/256, 186/256,186/256, 1));
            c.setFill(Color.ALICEBLUE);
            Label label = new Label(mapEntry.getKey());
            c.setLayoutX(node.getPosX());
            c.setLayoutY(node.getPosY());
            label.setLayoutX(node.getPosX() - 10);
            label.setLayoutY(node.getPosY() + 5);

            // paint edges
            for (Map.Entry<Node, Edge> edgeEntry : node.edges.entrySet()){
                Edge edge = edgeEntry.getValue();
                Node destinationNode = edgeEntry.getKey();

                if (checkPath(node, destinationNode, paths)){
                    Path path = new Path();
                    MoveTo moveTo = new MoveTo(node.getPosX(), node.getPosY());
                    LineTo lineTo = new LineTo(destinationNode.getPosX(), destinationNode.getPosY());

                    paths.put(node.getName(), destinationNode.getName());

                    path.getElements().add(moveTo);
                    path.getElements().add(lineTo);

                    Label rating = new Label(String.valueOf(edge.getRating()));
                    labelRatingAtCenterOfPath(path, rating);
                    rating.setTextFill(Color.RED);

                    graphCanvas.getChildren().add(path);
                    graphCanvas.getChildren().add(rating);
                }
            }

            graphCanvas.getChildren().add(c);
            graphCanvas.getChildren().add(label);
        }

        storeData();
    }

    /**
     * implements the autosaving feature
     */
    private void storeData(){
        try {
            graph.toFile(".graph");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * search for node1, if it's found search for a connection to node2
     * also check it the other way around
     *
     * @param node1
     * @param node2
     * @param paths
     * @return
     */
    private boolean checkPath(Node node1, Node node2, Map<String, String> paths){
        if (paths.containsKey(node1.getName())
                && paths.get(node1.getName()).equals(node2.getName()))
            return false;
        else if (paths.containsKey(node2.getName())
                && paths.get(node2.getName()).equals(node1.getName()))
            return false;
        return true;
    }

    /**
     *
     *  sets the label to the center of a path
     *
     * @param path
     * @param label
     */
    private void labelRatingAtCenterOfPath(Path path, Label label){
        MoveTo moveTo = (MoveTo) path.getElements().get(0);
        LineTo lineTo = (LineTo) path.getElements().get(1);

        double aX = moveTo.getX();
        double aY = moveTo.getY();
        double bX = lineTo.getX();
        double bY = lineTo.getY();

        label.setLayoutX(aX + (bX - aX)/2);
        label.setLayoutY(aY + (bY - aY)/2);
    }
}