/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.MenuItem;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
            if (!vBoxECOpen) { 
                openEditCategory();
                MenuCategorias();
                
            }
        });
        
        // Profile button
        editProfile.setOnAction((event) -> {
            if (!vBoxProfOpen) { openProfile(); }
        });
              
        
        //NO FUNCIONA. PENDIENTE DE REVISAR
        sCategory.setOnAction((event) -> {
            
            MenuButton seleccion = (MenuButton) event.getSource();
            String selectedOption = seleccion.getText();
            cCategory.setText(selectedOption);            
        }); 
                
        
        //SELECTION MODEL TAMPOCO FUNCIONA PORQUE sCategory NO ES UNA LISTVIEW. PENDIENTE DE REVISAR
        /*sCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Verificar si la selección es un MenuItem
            if (newValue instanceof MenuButton) {
                MenuButton selectedItem = (MenuButton) newValue;
                // Obtener el texto del MenuItem y actualizar cCategory
                cCategory.setText(selectedItem.getText());
            }
        });*/

        //cCategory.textProperty().bind(sCategory.getItems());
        //PARECE QUE SI SE PUEDE HACER CON MENUITEM YA QUE LAS OPCIONES SON ITEMS. PERO CON MENUBUTTON NO PORQUE LAS OPCIONES SON BOTONES/ACCIONES, NO ELEMENTOS DE TEXTO NI ITEMS.
        
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
    
    //----------------------------------------------------------------------------------------------------
    
    @FXML
    public void handleMenuButtonAction(ActionEvent event) {

        MenuButton menuButton = (MenuButton) event.getSource();
        String selectedOption = menuButton.getText();
        cCategory.setText(selectedOption);
    }


    //LISTA DE CATEGORIAS DE EJEMPLO (FALTA BASE DE DATOS)
    public static List<String> obtenerListaDeCategorias() {
        // Aquí defines cómo obtendrás la lista de categorías, por ejemplo, desde una base de datos o un archivo
        List<String> categoryList = new ArrayList<>();
        // Agrega categorías de ejemplo
        categoryList.add("Category 1");
        categoryList.add("Category 2");
        categoryList.add("Category 3");
        return categoryList;
    }

    // Lista que contiene las categorías disponibles
    List<String> categoryList = obtenerListaDeCategorias(); // Definir todavía cómo obtener esta listasegún se añadan en la pantalla de añadir categoria. Habrá que hacer en obtenerListaDeCategorias que reciba atributos del tipo string y los introduzca en un string. Pasar entonces el metodo por añadir categoria
    public void MenuCategorias() {
        
        // Limpiar items existentes en MenuButton al inicio
        sCategory.getItems().clear();
        
        // Si no hay categorías en la lista, agregar un MenuItem que indique "No categories"
        if (categoryList.isEmpty()) {
            MenuItem noCategoriesItem = new MenuItem("No categories");
            sCategory.getItems().add(noCategoriesItem);
        } else {
            
            // Si hay categorías, agregamos un MenuItem para cada una de ellas
            for (String category : categoryList) {
                MenuItem categoryItem = new MenuItem(category);
                sCategory.getItems().add(categoryItem);
            }
              
        }
    }    


    @FXML
    public void NewCategoryButtonAction(ActionEvent event) {
        try {
            // Cargar la ventana del segundo proyecto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCategories.fxml"));
            Parent root = loader.load();

            // Configurar la escena
            Scene scene = new Scene(root);

            // Configurar el escenario
            Stage stage = new Stage();

            // Establecer el ícono de la aplicación en la barra de título
            Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
            stage.getIcons().add(icon);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {}
    }
           
}
