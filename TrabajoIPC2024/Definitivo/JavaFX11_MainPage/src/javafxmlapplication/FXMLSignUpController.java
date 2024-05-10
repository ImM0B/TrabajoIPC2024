/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLSignUpController implements Initializable {

    @FXML
    private Button bCancel;
    @FXML
    private VBox rightVBox;
    @FXML
    private Button editCategory;
    @FXML
    private Button print;
    @FXML
    private Button imgProfile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Botón Cancelar cierra la ventana
        bCancel.setOnAction((event) -> {
            bCancel.getScene().getWindow().hide();
        });
        
        editCategory.setOnAction((event) -> {
           if(rightVBox.getWidth() == 0) {
               rightVBox.setPrefWidth(600);
           } else rightVBox.setPrefWidth(0);
        });
        
        
    }    
    
}
