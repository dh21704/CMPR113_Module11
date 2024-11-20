import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class HW11 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the image file
        Image image = new Image("file:C:/Users/Danny/Downloads/joshua-hoehne-tofMn2AWGpI-unsplash.jpg");  // Path to your image file
        
        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image);
        
        primaryStage.setTitle("Heads or Tails");
               
        Button submitButton = new Button("Submit");
        
        submitButton.setOnAction(e ->
        {
            Random rand = new Random();
            
            int num = rand.nextInt(2);
            
            Stage secondStage = new Stage();
            GridPane pane = new GridPane();
            ImageView secondImageView = null;
            
            if(num == 1)
            {
                System.out.println("1");
                                
                Image image1 = new Image("file:C:/Users/Danny/Downloads/tails.jpg");
                
                secondImageView = new ImageView(image1);
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Result");
                alert.setHeaderText("Number is 1, Tails");
                alert.showAndWait();
                
            }
            if(num == 0)
            {
                System.out.println("0");
                
                Image image2 = new Image("file:C:/Users/Danny/Downloads/heads.jpg");
                
                secondImageView = new ImageView(image2);
                
                Alert alert = new Alert(AlertType.INFORMATION); 
                alert.setTitle("Result");
                alert.setHeaderText("Number is 0, HEads");
                alert.showAndWait();
            }
            
            pane.add(secondImageView, 0, 0);
            Scene secondScene = new Scene(pane, 500, 500);
            secondStage.setScene(secondScene);
            
            //hide the primary stage and show the second stage
            primaryStage.hide();
            secondStage.show();
        }
        );
        
        imageView.setFitWidth(100);  
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true); 
        
        // Create a GridPane layout
        GridPane grid = new GridPane();
        
        grid.add(imageView, 0, 0);
        
        grid.add(submitButton, 0, 1);
        
        // Create the scene and set it on the stage
        Scene scene = new Scene(grid, 200, 200); // Set size for the scene
        primaryStage.setScene(scene);
        
        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
