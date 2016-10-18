
package polynomial_roots_sturms_method;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Marek
 */
public class MainScreenController {

    @FXML
    private Pane mainPane;
    
    InputNumbersController inputNumbersController;

    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        loadInputNumbersScreen();
    }

    protected void loadInputNumbersScreen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/InputNumbers.fxml"));
        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        inputNumbersController = loader.getController();
        inputNumbersController.setMainController(this);
        setScreen(pane);
    }

    protected void setScreen(Pane pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }

}
