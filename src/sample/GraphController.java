package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.*;
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

    private Graph graph = new Graph();

    public void addNode(ActionEvent actionEvent) {
        graph.addNode(nodeName.toString());
        rerenderGraph();
    }

    public void removeNode(ActionEvent actionEvent) {
        graph.deleteNode(nodeName.toString());
        rerenderGraph();
    }

    public void addEdge(ActionEvent actionEvent) {
        graph.getNode(fromNodeName.toString()).addEdge(graph.getNode(toNodeName.toString()), Integer.parseInt(edgeRating.toString()));
        rerenderGraph();
    }

    public void removeEdge(ActionEvent actionEvent) {
        graph.getNode(fromNodeName.toString()).deleteEdge(graph.getNode(toNodeName.toString()));
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
        graph.toFile(filePath.toString());
    }

    public void readFile(ActionEvent actionEvent) throws IOException {
        graph = Graph.fromFile(filePath.toString());
        rerenderGraph();
    }

    private void rerenderGraph(){

    }
}