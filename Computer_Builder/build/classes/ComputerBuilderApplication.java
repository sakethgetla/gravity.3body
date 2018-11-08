//import au.edu.uts.ap.javafx.*;
import java.io.File;
import javafx.stage.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.ComputerBuilder;

public class ComputerBuilderApplication extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
	/*
/	Image image = new Image("view/parts.jpg");
/         // simple displays ImageView the image as is
/        ImageView iv1 = new ImageView();
/        iv1.setImage(image);
/	HBox box = new HBox();
	box.getChildren().add(iv1);
	*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/computerbuilder.fxml"));
        Parent root = loader.load();
	String title = "Guillermo's Computer Store";
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.show();



    }
}
