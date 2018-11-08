package controller;

import au.edu.uts.ap.javafx.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.*;

public class ComputerBuilderController extends Controller<ComputerBuilder> {

    Stage buildStage, catStage;

    @FXML
    private void initialize() {
        buildStage = new Stage();
        catStage = new Stage();

	
	
    }

    public final ComputerBuilder getModel() {
        return model;
    }

    public final Stage getStage() {
        return stage;
    }

    @FXML
    private void viewCatalogue(ActionEvent event) throws Exception {
        System.out.println("view Cat");
        //catStage.setWidth(getStage().getWidth());
        catStage.setMinWidth(getStage().getWidth());
        ViewLoader.showStage(getModel().getCatalogue(), "/view/catalogue.fxml", "Catalogue", catStage);
        //getModel.getCatalogue
        
    }

    @FXML
    private void viewBuild(ActionEvent event) throws Exception {
        System.out.println(getStage().getWidth());
        System.out.println("view build");
        //ViewLoader.showStage(new ComputerBuilder(), "/view/computerbuilder.fxml", "Guillermo's Computer Store", stage);
        //getModel().getCatalogue().getBuild()
        buildStage.setMinWidth(getStage().getWidth());
        ViewLoader.showStage(getModel().getCatalogue().getBuild(), "/view/build.fxml", "Current Build", buildStage);

    }

    @FXML
    private void quit(ActionEvent event) throws Exception {

        getStage().close();
        buildStage.close();
        catStage.close();

    }

}
