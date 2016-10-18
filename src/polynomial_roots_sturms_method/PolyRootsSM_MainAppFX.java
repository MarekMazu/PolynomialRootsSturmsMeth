/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polynomial_roots_sturms_method;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Marek
 */
public class PolyRootsSM_MainAppFX extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/MainScreen.fxml"));
        Pane root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Roots of polynomial - Sturm's method");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
