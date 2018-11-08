package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Build;

public class CheckBuildController extends Controller<Build>{

        @FXML
    private Text text;
    
    @FXML private void initialize() {

	String s = "adw";
        System.out.print(s);
	if(getModel().hasPartOfType("CPU")) {

	    s= "The buld is missing a CPU.\n" ;
	}
	 
	if(getModel().hasPartOfType("MOTHERBOARD")){
	    s+= "The buld is missing a motherboard.\n";
	}
	
	if(getModel().hasPartOfType("MEMORY")){
	    s+= "The buld is missing RAM.\n";
	}
	if(getModel().hasPartOfType("CASE")){
	    s+= "The buld is missing a case.\n";
	}
	if(getModel().hasPartOfType("STORAGE")){
	    s+= "The buld is missing storage.";
	}
if(getModel().hasPartOfType("CPU")) {
        }else{
	    s= "The buld is missing a CPU.\n" ;
	}
	 
	if(getModel().hasPartOfType("MOTHERBOARD")){
	}else{
            s+= "The buld is missing a motherboard.\n";
	}
	
	if(getModel().hasPartOfType("MEMORY")){
	    }else{
            s+= "The buld is missing RAM.\n";
	}
	if(getModel().hasPartOfType("CASE")){
	    }else{
            s+= "The buld is missing a case.\n";
	}
	if(getModel().hasPartOfType("STORAGE")){
	    }else{
            s+= "The buld is missing storage.";
	}
	text.setText(s);
	System.out.print(s);

	
    }
        public final Build getModel() {
        return model;
    }
    @FXML private void close(ActionEvent event) throws Exception{
	getStage().close();
	
    }
    public final Stage getStage() {
        return stage;
    }
}
