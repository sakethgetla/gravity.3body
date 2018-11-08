package controller;

import au.edu.uts.ap.javafx.*;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import model.*;
import java.text.DecimalFormat;

public class BuildController extends Controller<Build> {

    @FXML
    private TableView<Part> partsTv;
    @FXML
    private Button removeSelectedBtn;
    @FXML
    private TableColumn<Part, String> priceClm;
    @FXML
    private Text price;



	
	private static DecimalFormat df2 = new DecimalFormat(".##");
    @FXML
    private void initialize() {
	partsTv.setMinHeight(380);
price.setText("Total: $0.00");
	priceClm.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asString("$%.2f"));

        partsTv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //        price.setText(getModel().totalPrice());

        partsTv.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSubject, newSubject) -> removeSelectedBtn.setDisable(newSubject == null)
        );

	//	ObservableList<Integer> ob = FXCollections.observableArrayList(intList);
	getModel().getParts().addListener(new ListChangeListener<Part>() {
        @Override
        public void onChanged(javafx.collections.ListChangeListener.Change<? extends Part> c) {
	    //            System.out.println("Changed on " + );
	    //  if(c.next()){
	      System.out.println("adwawd");
	    //            }
	              price.setText("Total: $" + df2.format(getModel().totalPrice()) +"0");

        }

    });

    }

    private ObservableList<Part> getSelectedParts() {

        return partsTv.getSelectionModel().getSelectedItems();
    }

    public final Build getModel() {
        return model;
    }

    public final Stage getStage() {
        return stage;
    }

    @FXML
    private void checkBuild(ActionEvent event) throws Exception {
      	    Stage sa = new Stage();
	    sa.setMinWidth(300);
            System.out.println(getModel().isValid());
         String s = "adw";
        System.out.print(s);
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
        	System.out.print(s);

        ViewLoader.showStage(getModel(), "/view/buildcheck.fxml", "Build Validity Status", sa);
    }

    @FXML
    private void removeSelected(ActionEvent event) {
        getModel().remove(getSelectedParts());
    }

    @FXML
    private void close(ActionEvent event) throws Exception {
        getStage().close();

    }

}
