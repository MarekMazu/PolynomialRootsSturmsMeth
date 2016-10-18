/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polynomial_roots_sturms_method;


import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;



/**
 * FXML Controller class
 *
 * @author Marek
 * 
 * 
 */

public class ShowResultsController {
   
    @FXML
    protected Label showTexts1;

    @FXML
    protected Label showTexts;

    @FXML
    private ChoiceBox<String> precisionChoice;
    
    BigDecimal[] roots;
    
    BigDecimal[] polynomial;
    
    ObservableList<String> precisionList = FXCollections.observableArrayList("0.1", "0.01", "0.001", "0.0001", "0.00001", "0.000001");

    MainScreenController mainController;

    InputNumbersController inputNumbersController;

    String[] enteredStrings;
    
    PolynomialRootsSturmsMethod polynRootsSM;
    
    
    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
          precisionChoice.setItems(precisionList);
          precisionChoice.setValue("0.1");
    }   
    
    protected void showRoots() {
        
        String rootsString = rootsToString(roots);
        if(!rootsString.equals("")) setText("Counted roots: \n\n" + rootsString);
        else setText("There is no roots.");
    }
    
    
    public String rootsToString(BigDecimal[] roots){
        
        String rootsString = "";
        for(BigDecimal root : roots) rootsString += root + "\n";
        return rootsString;
    
    }
    
    @FXML
    void backToStart(ActionEvent event) {
        mainController.loadInputNumbersScreen();
        mainController.inputNumbersController.stringArrToInputTexts(enteredStrings);
        
    }
    
    @FXML
    void start(ActionEvent event) {
        
        polynRootsSM = new PolynomialRootsSturmsMethod();
        polynRootsSM.setShowResultController(this);
        polynRootsSM.setPrecision(new BigDecimal(precisionChoice.getValue()));
        polynRootsSM.main(polynomial);
    }

    
    
    void setShowTexts1(String text) {
        this.showTexts1.setText(text);
    }
   
    
    void setText(String text) {
        this.showTexts.setText(this.showTexts.getText() +  text + "\n");
    }
    
    void setRoots(BigDecimal[] roots) {
        this.roots = roots;
    }
    
    void setMainController( MainScreenController mainController) {
        this.mainController = mainController;
     
    }
     
    void setInputNumbersController(InputNumbersController inputNumbersController) {
        this.inputNumbersController = inputNumbersController;
    }
     
     
}
