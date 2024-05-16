/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;


/**
 * FXML Controller class
 *
 * @author RMEDLLO
 */
public class NewCategoriesController implements Initializable {

    
    private Button bCancel;
    @FXML
    private HBox bottomHBox;
    @FXML
    private Button bReSend;
    @FXML
    private TextField nombreCat;
    @FXML
    private TextArea descCat;
    @FXML
    private Button bAñadir;
    @FXML
    private Button bCancelar;
    private Label errorNuevaDescr;
    @FXML
    private Label exitoNuevaCat;
    @FXML
    private Label errorNuevaCat;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        bAñadir.setOnAction( (event) ->{
            
            if(!category()){
                try {
                    // Cargar la ventana del segundo proyecto
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainPage.fxml"));
                    Parent root = loader.load();
                    FXMLMainPageController controller = loader.getController();

                    // Configurar la escena
                    Scene scene = new Scene(root);

                    // Configurar el escenario
                    Stage stage = new Stage();
                    stage.setMaximized(true); // El tamaño de la ventana se ajusta automáticamente a la pantalla del usuario
                    stage.setResizable(false); // No se puede cambiar el tamaño de la pestaña
                    stage.initStyle(StageStyle.TRANSPARENT); // Pantalla completa (hay q añadir el botón de cerrar a mano)

                    // Acceder al VBox desde el controlador y ocultarlo
                    StackPane rightStackPane = controller.getRightStackPane();
                    rightStackPane.setPrefWidth(0);
                    
                    // Iterar sobre los hijos del VBox y establecer las propiedades "managed" y "visible" en "false"
                    for (Node node : rightStackPane.getChildren()) {
                        node.setVisible(false); // Hacer que el nodo hijo sea invisible
                        node.setManaged(false); // Establecer que el nodo hijo no sea gestionado por el VBox
                    }
                    
                    // Establecer el ícono de la aplicación en la barra de título
                    Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
                    stage.getIcons().add(icon);

                    stage.setScene(scene);
                    stage.show();

                    // Ocultar la ventana actual después de medio segundo
                    javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(Duration.seconds(0.1), evt -> {
                        // Cerrar la ventana actual
                        ((Node) event.getSource()).getScene().getWindow().hide();
                    }));
                
                    timeline.play(); // Iniciar la animación de retraso
                } catch (IOException e) {}               
            }   
        });
        
    }    
    
    
    public boolean category (){
        boolean categorianueva = false;
        
        try{
            categorianueva = Acount.getInstance().registerCategory(nombreCat.getText(), descCat.getText());
            exitoNuevaCat.setVisible(true);
            
        } catch (AcountDAOException | IOException ex) { manageError(errorNuevaCat, nombreCat); }
        finally{
            if(!categorianueva){
                manageError(errorNuevaCat, nombreCat);
                borrarCampos();
            }
        }
        return categorianueva;
    }

    
    
    private void manageError(Label errorNuevaCat, TextField nombreCat) {
        
        errorNuevaDescr.setVisible(true);
    }
    
    
    
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) bCancel.getScene().getWindow();
        stage.close();
    }
    
    
    private void borrarCampos() {
        nombreCat.clear();
        descCat.clear();
    }

    

    
    
}
