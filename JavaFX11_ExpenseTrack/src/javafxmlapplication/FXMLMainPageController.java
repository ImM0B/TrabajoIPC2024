/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLMainPageController implements Initializable {

    @FXML
    private Button bCancel;
    @FXML
    private StackPane rightStackPane;
    private Button editCategory;
    @FXML
    private Button print;
    @FXML
    private VBox vBoxEC;
    @FXML
    private HBox topHBox;
    @FXML
    private StackPane topStackPane;
    @FXML
    private VBox vBoxProfile;
    @FXML
    private Button editProfile;
    
    private boolean spOpen = false;
    private boolean vBoxECOpen = false;
    private boolean vBoxProfOpen = false;
    @FXML
    private Button editCategories;
    @FXML
    private Text cCategory;
    @FXML
    private MenuButton sCategory;
    @FXML
    private Button cAdd;
    @FXML
    private Button cDelete;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Botón Cancelar cierra la ventana
        bCancel.setOnAction((event) -> {
            bCancel.getScene().getWindow().hide();
        });
        // Cargar la imagen
        Image image = new Image(getClass().getResourceAsStream("../icons/bg_mainPage.png"));
        
        // Establecer el fondo en el HBox
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        topHBox.setBackground(new Background(backgroundImage));
        
        // EditCategory button
        editCategories.setOnAction((event) -> {
            if (!vBoxECOpen) { openEditCategory(); }
        });
        
        // Profile button
        editProfile.setOnAction((event) -> {
            if (!vBoxProfOpen) { openProfile(); }
        });  
    }
    
    public void closeRightStackPane() {
        rightStackPane.setPrefWidth(0);
        vBoxEC.setVisible(false); vBoxEC.setManaged(false);
        vBoxProfile.setVisible(false); vBoxProfile.setManaged(false);
        spOpen = false;
        vBoxECOpen = false;
        vBoxProfOpen = false;
    }
    
    private void openEditCategory() {
        closeRightStackPane(); 
        rightStackPane.setPrefWidth(600);
        vBoxEC.setVisible(true); vBoxEC.setManaged(true);
        spOpen = true;
        vBoxECOpen = true;
    }
    
    private void openProfile() {
        closeRightStackPane(); 
        rightStackPane.setPrefWidth(600);
        vBoxProfile.setVisible(true); vBoxProfile.setManaged(true);
        spOpen = true;
        vBoxProfOpen = true;
    }
    
    @FXML
    public void cancelStackPAneButtonAction(ActionEvent event) {
        closeRightStackPane();
    }

    // Método para obtener una referencia al StackPane desde otras clases
    public StackPane getRightStackPane() {
        return rightStackPane;
    }
}
