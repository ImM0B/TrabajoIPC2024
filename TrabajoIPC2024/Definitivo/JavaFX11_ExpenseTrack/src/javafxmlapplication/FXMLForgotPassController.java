/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLForgotPassController implements Initializable {

    private BooleanBinding fieldsValid;    
    @FXML
    private TextField bDir;
    @FXML
    private Label iniDir;
    @FXML
    private Label messageSent;
    @FXML
    private Label messageSent2;
    @FXML
    private Button bSend;
    @FXML
    private Button bReSend;
    @FXML
    private HBox bottomHBox;
    @FXML
    private Button bSend1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldsValid = bDir.textProperty().isNotEmpty();
        
        bSend.disableProperty().bind(fieldsValid.not());
        
        bSend.setOnAction((event) -> {
            if(!checkField()) { clearField(); }
            else {
                messageSent2.setVisible(false);
                messageSent.setVisible(true);
                bottomHBox.setVisible(true);
                bSend1.setVisible(true);
                bSend.setVisible(false);
            }
        });
        
        bReSend.setOnAction((event) -> {
            if(!checkField()) { clearField(); }
            else {
                messageSent.setVisible(false);
                messageSent2.setVisible(true);
            }
        });
    
        bDir.textProperty().addListener((observable, oldValue, newValue) -> {
            bSend.setVisible(true);
            bSend1.setVisible(false);
        });
    
    
    }
    
    private boolean checkField() {
        boolean emailValid = Utils.checkEmail(bDir.getText());
        if (!emailValid) {
            manageError(iniDir, bDir);
        } else {
            manageCorrect(iniDir, bDir);
        }
        return emailValid;
    }
    
    private void manageError(Label errorLabel, TextField textField) {
        errorLabel.setVisible(true);
    }
    
    private void manageCorrect(Label errorLabel, TextField textField) {
        errorLabel.setVisible(false);
    }
        
    private void clearField() {
        bDir.clear();
        messageSent.setVisible(false);
        messageSent2.setVisible(false);
    }
    
    
}
