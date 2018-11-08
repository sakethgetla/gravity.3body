package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.Catalogue;

public class ErrorController extends Controller<Catalogue> {

    @FXML private void close(ActionEvent event) throws Exception {
        getStage().close();
    }

    public final Stage getStage() {
        return stage;
    }

    
}
