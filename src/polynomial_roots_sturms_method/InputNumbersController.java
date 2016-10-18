/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polynomial_roots_sturms_method;


import java.awt.Desktop;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;



/**
 * FXML Controller class
 *
 * @author Marek
 */
public class InputNumbersController {

    @FXML
    private VBox vbox;

    @FXML
    private Label text1;

    @FXML
    private Label text2;

    @FXML
    private Label textCoeff_0;

    @FXML
    private TextField inputText_0;

    @FXML
    private Label textCoeff_1;

    @FXML
    private TextField inputText_1;

    @FXML
    private Label textCoeff_2;

    @FXML
    private TextField inputText_2;

    @FXML
    private Label textCoeff_3;

    @FXML
    private TextField inputText_3;

    @FXML
    private Label textCoeff_4;

    @FXML
    private TextField inputText_4;

    @FXML
    private Label textCoeff_5;

    @FXML
    private TextField inputText_5;

    @FXML
    private Label textCoeff_6;

    @FXML
    private TextField inputText_6;

    @FXML
    private Label textCoeff_7;

    @FXML
    private TextField inputText_7;

    @FXML
    private Label textCoeff_8;

    @FXML
    private TextField inputText_8;

    @FXML
    private Label textCoeff_9;

    @FXML
    private TextField inputText_9;

    @FXML
    private Label textCoeff_10;

    @FXML
    private TextField inputText_10;

    private String[] enteredStrings;
    
    private MainScreenController mainController;

    private ShowResultsController showResultsController;

    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        
        String sentence = "Coefficient of the";
        XsupscriptsPow xPow = new XsupscriptsPow();

        textCoeff_0.setText("Constant term");
        textCoeff_1.setText(sentence + xPow.XsupPow(1));
        textCoeff_2.setText(sentence + xPow.XsupPow(2));
        textCoeff_3.setText(sentence + xPow.XsupPow(3));
        textCoeff_4.setText(sentence + xPow.XsupPow(4));
        textCoeff_5.setText(sentence + xPow.XsupPow(5));
        textCoeff_6.setText(sentence + xPow.XsupPow(6));
        textCoeff_7.setText(sentence + xPow.XsupPow(7));
        textCoeff_8.setText(sentence + xPow.XsupPow(8));
        textCoeff_9.setText(sentence + xPow.XsupPow(9));
        textCoeff_10.setText(sentence + xPow.XsupPow(10));
    }

    @FXML
    protected void sturmTheoremLink(MouseEvent event) throws URISyntaxException, IOException {

        URI uri = new URI("https://en.wikipedia.org/wiki/Sturm%27s_theorem");
        Desktop.getDesktop().browse(uri);
    }
    
    @FXML
    protected void startButton() {
 
        enteredStrings = new String[11];

        enteredStrings[0] = inputText_0.getText();
        enteredStrings[1] = inputText_1.getText();
        enteredStrings[2] = inputText_2.getText();
        enteredStrings[3] = inputText_3.getText();
        enteredStrings[4] = inputText_4.getText();
        enteredStrings[5] = inputText_5.getText();
        enteredStrings[6] = inputText_6.getText();
        enteredStrings[7] = inputText_7.getText();
        enteredStrings[8] = inputText_8.getText();
        enteredStrings[9] = inputText_9.getText();
        enteredStrings[10] = inputText_10.getText();
        
        showResultsController = loadShowResults();
        showResultsController.enteredStrings = this.enteredStrings;
        BigDecimal[] polynomial = StringsToBigDecimals(emptyToZero(enteredStrings));
        showResultsController.polynomial = polynomial;
        
        if(polynomial.length > 0){
            showResultsController.setShowTexts1("Entered polynomial coefficiens: \n" + polynomialForShow(polynomial));
        }
    }

    protected ShowResultsController loadShowResults() {
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ShowResults.fxml"));
        VBox box = null;

        try {
            box = loader.load();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        showResultsController = loader.getController();
        showResultsController.setMainController(mainController);
        mainController.setScreen(box);
        return showResultsController;
    }

    @FXML
    public void resetButton(ActionEvent event) {
        String[] emptyStrings = {"", "", "", "", "", "", "", "", "", "", ""};
        stringArrToInputTexts(emptyStrings);
    }
  
    
    protected void stringArrToInputTexts(String[] arr){
        
        inputText_0.setText(arr[0]);
        inputText_1.setText(arr[1]);
        inputText_2.setText(arr[2]);
        inputText_3.setText(arr[3]);
        inputText_4.setText(arr[4]);
        inputText_5.setText(arr[5]);
        inputText_6.setText(arr[6]);
        inputText_7.setText(arr[7]);
        inputText_8.setText(arr[8]);
        inputText_9.setText(arr[9]);
        inputText_10.setText(arr[10]);
    }

    
    private BigDecimal[] StringsToBigDecimals(String[] polynomialString){
        
        BigDecimal[] polynomialFromString = new BigDecimal[11];
        
        try {
            
            for(int i = 0; i< 11; i++) polynomialFromString[i] = new BigDecimal(polynomialString[i]);
            
        } catch(IllegalArgumentException exp){showResultsController.setShowTexts1("Wrong arguments format. Try again. \n"); return new BigDecimal[0];}
        
        int degree = PolynomialRootsSturmsMethod.degree(polynomialFromString);
        BigDecimal[] polynomial = new BigDecimal[degree+1];
        for(int i = 0; i <= degree; i++) { polynomial[i] = polynomialFromString[i]; }
        return polynomial;
    }
    
    
    public String[] emptyToZero(String[] string) {
        
        String[] stringWithZeros = new String[11];
        
        for (int i = 0; i < 11; i++) {
            
            stringWithZeros[i] = string[i];
            if (string[i].equals("")) { stringWithZeros[i] = "0"; }
        }
        
        return stringWithZeros;
    }
    
    
    public String polynomialForShow(BigDecimal[] polynomial){

        String polynomialString = "";
        int degree = PolynomialRootsSturmsMethod.degree(polynomial);
        
        for(int i = degree; i >= 0; i--) {
            
            int compare = polynomial[i].compareTo(BigDecimal.ZERO);
            if(compare != 0)  polynomialString += ((compare > 0) ? " + " : " - ") + polynomial[i].abs() + new XsupscriptsPow().XsupPow(i);
            if ( !(polynomialString.equals("")) && (polynomialString.substring(0, 2)).equals(" +") ) polynomialString = polynomialString.substring(3);
            if (polynomialString.equals("")) polynomialString = "0";
        }
        
        return polynomialString;
    }
    
    
    protected void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }
    
}
