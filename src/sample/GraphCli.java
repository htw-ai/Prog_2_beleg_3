package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dudzik on 16.12.14.
 */
public class GraphCli {
    /**
     * a list of all the Graph methods which can be accessed by the user from the cli
     */
    private static String[] methods = { "reset" , "addNode", "removeNode", "addEdge", "removeEdge", "showGraph", "readFromFile", "writeToFile", "random", "quit" };


    /**
     * just the graph. a new graph. just for you.
     */
    public Graph graph = new Graph();

    /**
     * the value which let the while loop know that the user wants the programm closed
     */
    private boolean quit = false;

    /**
     * the user gets the feedback that a graph was generated when the Graph Cli is initialized
     */
    public GraphCli (){
        System.out.println("An graph was generated for you");
    }

    /**
     * starts the cmd prompt
     */
    public void call() throws IOException {
        System.out.println("Enter the identifier of one of the following methods to run it");
        System.out.println(methodsToString());
        String name = readLine("confirm with enter");
        try {
            runMethod(name.charAt(0));
        } catch (StringIndexOutOfBoundsException e){
            System.out.println("wrong identifier. only 0-9. Try again.");
        }
    }

    /**
     * promts the user to enter a cmd identifier which in turn runs identified cmd
     *   autosaving is done here
     *
     * @param identifier char which is the identifier -> there are only 9
     */
    public void runMethod(char identifier) throws IOException {
        switch (identifier) {
            case '0':
                reset();
                break;
            case '1':
                addNode();
                break;
            case '2':
                removeNode();
                break;
            case '3':
                addEdge();
                break;
            case '4':
                removeEdge();
                break;
            case '5':
                showGraph();
                break;
            case '6':
                readFromFile();
                break;
            case '7':
                writeToFile();
                break;
            case '8':
                random();
                break;
            case '9':
                quit();
                break;
            default:
                System.out.println("wrong identifier. only 0-9. Try again.");
        }
        // autosaving
        graph.toFile(".graph");
    }

    /**
     * sets quit to true
     */
    private void quit() {
        setQuit(true);
    }

    /**
     * @return if the user enterd the quit cmd
     */
    public Boolean shouldBreak(){
        return quit;
    }

    /**
     * set an empty graph
     */
    public void reset(){
        graph = new Graph();
    }

    /**
     * promts the user to enter the edge which is going to be removed
     */
    public void addNode() throws IOException {
        String nodeName = readLine("Enter a node name");
        graph.addNode(nodeName);
    }

    /**
     * promts the user to enter the node which is going to be removed
     */
    public void removeNode() throws IOException {
        String nodeName = readLine("Enter the node name");
        graph.deleteNode(nodeName);
    }

    /**
     * promts the user to enter the edge which is going to be added
     */
    public void addEdge() throws IOException {
        if(graph.nodeCount() <= 1) {
            System.out.println("you have to have at least two nodes for this operation");
            return;
        }
        String nodeNameFrom = readLine("Enter the from node name");
        String nodeNameTo   = readLine("Enter the to node name");
        String rating   = readLine("Enter an edge rating");

        int iRating = Integer.parseInt(rating);
        graph.getNode(nodeNameFrom).addEdge(graph.getNode(nodeNameTo), iRating);
    }

    /**
     * promts the user to enter the edge which is going to be removed
     */
    public void removeEdge() throws IOException {
        String nodeNameFrom = readLine("Enter the from node name");
        String nodeNameTo   = readLine("Enter the to node name");
        graph.getNode(nodeNameFrom).deleteEdge(graph.getNode(nodeNameTo));
    }

    public void showGraph(){
        System.out.println(graph.toString());
    }

    /**
     * promts the user to enter path to the file which is going to be imported into the console
     *
     */
    public void readFromFile() throws IOException {
        String path = readLine("Enter file path");
        graph = Graph.fromFile(path);
        System.out.println("import successfull");
    }

    /**
     * promts the user to enter path to file which is going to be written to
     *
     */
    public void writeToFile() throws IOException {
        String path = readLine("Enter file path");
        graph.toFile(path);
    }

    /**
     * promts the user to enter edges and node count which the random graph should have
     *
     */
    public void random() throws IOException{
        int edgeCount = Integer.parseInt(readLine("Enter the max edges"));
        int nodeCount = Integer.parseInt(readLine("Enter the max node count"));

        //graph =
        graph.random(nodeCount, edgeCount);
    }

    /**
     * convert instance method methods to a String
     *
     * @return a representation of the instance variable methods as a string
     */
    public static String methodsToString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < methods.length; i++) {
            str.append(i).append(") ");
            str.append(methods[i]);
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * promts the user to enter somthing into the console and returns its value
     *
     * @param to_print String which is printed before the line is read
     * @return string from console
     */
    public static String readLine(String to_print) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(to_print);
        return br.readLine();
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }
}
