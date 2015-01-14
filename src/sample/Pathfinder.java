package sample;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by root on 14.01.15.
 */
public final class Pathfinder {

    public static Graph Graph;

    public static LinkedList<Node> Search(Node start, Node destination){
        LinkedList<Node> path = new LinkedList<>();


        HashSet<String> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        Node node;
        stack.push(destination);
        while(!stack.isEmpty()){
            node = stack.pop();
            if(visited.contains(node.getName())){
                if(start.getName() == node.getName())
                    return null;
                visited.add(node.getName());
                node.edges.forEach((Node n, Edge e) -> stack.push(n));
            }
        }



        return path;
    }

    public static LinkedList<Node> QuickSearch(Node start, Node destination){
        LinkedList<Node> path = new LinkedList<>();
        return path;
    }
}
