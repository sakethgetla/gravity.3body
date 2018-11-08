package controller;

import au.edu.uts.ap.javafx.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

public class AddToCatalogueController extends Controller<Catalogue> {

    @FXML
    private TextField typeTf, nameTf, priceTf;

    @FXML
    private void add2Cat(ActionEvent event) throws Exception {
//        if (nameTf.getText() != null && typeTf.getText() != null && (Double.parseDouble(priceTf.getText()) > 0)) {
//
//            getModel().addPart(nameTf.getText(), typeTf.getText(), Double.parseDouble(priceTf.getText()));
//            stage.close();
//        } else {
//
//            ViewLoader.showStage(getModel(), "/view/error.fxml", "incorrect input", new Stage());
//        }

	try{
	    getModel().addPart(nameTf.getText(), typeTf.getText(), Double.parseDouble(priceTf.getText()));}catch(Exception e){

	    Stage s = new Stage();
	    s.setMinWidth(300);
		
            ViewLoader.showStage(getModel(), "/view/error.fxml", "incorrect input", s);	    
	    
	}
    }
    public final Catalogue getModel() {
        return model;
    }
        public final Stage getStage() {
           
        return stage;
    }

  

}
