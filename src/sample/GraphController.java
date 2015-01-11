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

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by dudzik on 09.01.15.
 */
public class GraphController {
    public TextField nodeName;
    public TextField toNodeName;
    public TextField fromNodeName;
    public TextField edgeRating;
    public TextField filePath;
    public Pane graphCanvas;

    private Graph graph = new Graph();

    //Map<Node, Double[]> nodes = new HashMap<>();
    Random rnd = new Random();

    public void addNode(ActionEvent actionEvent) {
        graph.addNode(nodeName.getText());
        rerenderGraph();
    }

    public void removeNode(ActionEvent actionEvent) {
        graph.deleteNode(nodeName.getText());
        rerenderGraph();
    }

    public void addEdge(ActionEvent actionEvent) {
        graph.getNode(fromNodeName.getText()).addEdge(graph.getNode(toNodeName.getText()), Integer.valueOf(edgeRating.getText()));
        rerenderGraph();
    }

    public void removeEdge(ActionEvent actionEvent) {
        graph.getNode(fromNodeName.getText()).deleteEdge(graph.getNode(toNodeName.getText()));
        rerenderGraph();
    }

    public void reset(ActionEvent actionEvent) {
        graph = new Graph();
        rerenderGraph();
    }

    public void random(ActionEvent actionEvent) {
        Random rand = new Random();
        graph = graph.random(rand.nextInt(50), rand.nextInt(50));
        rerenderGraph();
    }

    public void writeFile(ActionEvent actionEvent) throws FileNotFoundException, UnsupportedEncodingException {
        graph.toFile(filePath.getText());
    }

    public void readFile(ActionEvent actionEvent) throws IOException {
        graph = Graph.fromFile(filePath.getText());
        rerenderGraph();
    }

    private void rerenderGraph(){
        graphCanvas.getChildren().clear();
        Map<String, String> paths = new HashMap<>();

        for (Map.Entry<String, Node> mapEntry: graph.getNodes().entrySet()){
            Node node = mapEntry.getValue();

            // generate position
            if (mapEntry.getValue().getPosX() < 20) {
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