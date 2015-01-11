package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
        graph.getNode(fromNodeName.getText()).addEdge(graph.getNode(toNodeName.getText()), Integer.parseInt(edgeRating.getText()));
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

        for (Map.Entry<String, Node> mapEntry: graph.getNodes().entrySet()){
            Node node = mapEntry.getValue();

            // generate position
            if (mapEntry.getValue().getPosX() < 20) {
                int xValue = rnd.nextInt(new Double(graphCanvas.getWidth() - 40).intValue());
                int yValue = rnd.nextInt(new Double(graphCanvas.getHeight() - 60).intValue());
                node.setPosX(xValue);
                node.setPosY(yValue);
            }

            // UI elements
            Circle c = new Circle(4);
            c.setFill(new Color(119/256, 186/256,186/256, 1));
            Label label = new Label(mapEntry.getKey());
            c.setLayoutX(node.getPosX() + 20);
            c.setLayoutY(node.getPosY() + 20);
            label.setLayoutX(node.getPosX() + 12);
            label.setLayoutY(node.getPosY() + 25);

            graphCanvas.getChildren().add(c);
            graphCanvas.getChildren().add(label);
        }


    }
}