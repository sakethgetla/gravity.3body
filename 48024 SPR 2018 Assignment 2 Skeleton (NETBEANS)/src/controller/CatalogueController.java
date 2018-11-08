package controller;

import javafx.stage.Stage;
import au.edu.uts.ap.javafx.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;

public class CatalogueController extends Controller<Catalogue> {

    @FXML
    private TableView<Part> partsTv;
    @FXML
    private TableColumn<Part, String> priceClm;
    @FXML
    private Button add2BuildBtn, removeFromCatBtn;

    @FXML
    private TextField rangeToTf, rangeFromTf, typeTf;

    private String newValType = "", newValrangeTo = "", newValrangeFrom = "";

    @FXML
    private void initialize() {
        
        partsTv.setMinHeight(380);
        partsTv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        priceClm.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asString("$%.2f"));
        
        
        partsTv.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSubject, newSubject) -> add2BuildBtn.setDisable(newSubject == null)
        );
        partsTv.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSubject, newSubject) -> removeFromCatBtn.setDisable(newSubject == null)
        );



	typeTf.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);

	    this.newValType = newValue;
	    System.out.println(newValType +" <> " + newValrangeFrom+" <> " +newValrangeTo);
            getModel().filterList(newValType, newValrangeFrom, newValrangeTo);
        });
        
//        rangeToTf.textProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("textfield changed from " + oldValue + " to " + newValue);
//
//	    //this.newValType = newValue;
//	    //System.out.println(newValType +" <> " +newValrangeTo+" <> " + newValrangeFrom);
//            //getModel().filterList(newValType, newValrangeTo, newValrangeFrom);
//        });
	
        rangeToTf.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
	    this.newValrangeTo = newValue;
            getModel().filterList(newValType, newValrangeFrom, newValrangeTo);
        });
	
        rangeFromTf.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
	    this.newValrangeFrom = newValue;
            getModel().filterList(newValType, newValrangeFrom, newValrangeTo);
        });

    }

    private ObservableList<Part> getSelectedParts() {
        return partsTv.getSelectionModel().getSelectedItems();
    }

    public final Catalogue getModel() {
        return model;
    }

    public final Stage getStage() {
        return stage;
    }

    @FXML
    private void add2Build(ActionEvent event) throws Exception {
        getModel().getBuild().addParts(getSelectedParts());

        for (Part p : getSelectedParts()) {
            System.out.println(p.getName());
        }

    }

    @FXML
    private void add2Cat(ActionEvent event) throws Exception {

		    Stage s = new Stage();
	    s.setMinWidth(300);
        ViewLoader.showStage(getModel(), "/view/addtocatalogue.fxml", "Add New Part to Catalogue", s);

    }

    @FXML
    private void removeFromCat(ActionEvent event) throws Exception {

        getModel().remove(getSelectedParts());

    }

    @FXML
    private void close(ActionEvent event) throws Exception {
        getStage().close();
    }

}
