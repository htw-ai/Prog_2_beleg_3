package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        primaryStage.setTitle("Belegarbeit 3");

        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        if(args.length > 0 && args[0].equals("gui")){
            // start gui
            launch(args);
        } else {
            // start cli
            GraphCli graph = new GraphCli();
            while (true) {
                graph.call();
                if (graph.shouldBreak())
                    return;
            }
        }
    }
}
