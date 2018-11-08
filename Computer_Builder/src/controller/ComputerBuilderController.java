package controller;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.beans.binding.*;
import javafx.beans.property.*;
import java.io.*;
import java.text.*;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ComputerBuilderController {

    @FXML private Button viewBldBtn;
    @FXML private Button viewCalBtn;
    @FXML private Button quitBtn;

    @FXML private void viewCatalogue(ActionEvent event) throws Exception{
	System.out.println("view Cat");
	//	CatalogueController catalogue = new CatalogueController();
	/** FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/catalogue.fxml"));
        Parent root = loader.load();
	String title = "Catalogue";
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.show();
        */
      //  public void pressButton(ActionEvent event) throws Exception {               
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/catalogue.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          
}
    }

    @FXML private void viewBuild(ActionEvent event){
	System.out.println("view build");
	
    }    
    
}
