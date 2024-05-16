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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLMainPageController implements Initializable {

    //GENERAL
    @FXML
    private Button bCancel;
    @FXML
    private StackPane rightStackPane;
    @FXML
    private HBox topHBox;
    @FXML
    private StackPane topStackPane;
    
    private boolean spOpen = false;
    private boolean vBoxECOpen = false;
    private boolean vBoxProfOpen = false;
    private boolean vBoxAdderOpen = false;
    
    //MENÚ
    @FXML
    private Button editCategories;
    @FXML
    private Button print;
    @FXML
    private Button editProfile;
    
    //GASTOS
    @FXML
    private CheckBox selectAll;
    @FXML
    private ListView<?> gastosListView;
    @FXML
    private Button editAdder;
    
    //EDITAR CATEGORÍA
    @FXML
    private VBox vBoxEC;
    @FXML
    private VBox vBoxProfile;
    @FXML
    private Text cCategory;
    @FXML
    private MenuButton sCategory;
    @FXML
    private Button cAdd;
    @FXML
    private Button cDelete;
    @FXML
    private VBox vBoxNewCategory;
    @FXML
    private TextField nombreCat;
    @FXML
    private TextArea descCat;
    @FXML
    private Button cAdd1; //Add interior
    @FXML
    private Button cCancel;
    
    //PERFIL
    @FXML
    private ImageView pImage;
    @FXML
    private Text pUser;
    @FXML
    private TextField pName;
    @FXML
    private TextField pSurname;
    @FXML
    private TextField pEmail;
    @FXML
    private Label pErrorEmail;
    @FXML
    private Label pChanged;
    @FXML
    private Label pVerification;
    @FXML
    private Button pEdit;
    @FXML
    private Button pLogOut;
    @FXML
    private Button pConfChanges;
    @FXML
    private Button pCancel;
    
    //AÑADIR GASTO
    @FXML
    private VBox vBoxAdder;
    @FXML
    private TextField aName;
    @FXML
    private Button aAdd;
    @FXML
    private Button aCancel;
    @FXML
    private Label aErrorPrice;
    @FXML
    private Label aExpAdded;
    @FXML
    private TextField aCost;
    @FXML
    private TextArea aDesc;
    @FXML
    private MenuButton aCategory;
    @FXML
    private DatePicker aDate;
    @FXML
    private Button aImage;
    
    

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
                //MenuCategorias();
                
            }
        });
        
        // Profile button
        editProfile.setOnAction((event) -> {
            if (!vBoxProfOpen) { openProfile(); }
        });
        
        // Expense adder button
        editAdder.setOnAction((event) -> {
            if (!vBoxAdderOpen) { openExpenseAdder(); }
        });
              
        
        //sCategory.setOnAction ((event) -> {  
            
        //    cCategory.textProperty().bind(categoryList.getSelectionModel().selectedItemProperty()); //ERROR
        //});
        
        
    }
    
    //CERRAR TODAS LAS PESTAÑAS DE LA DERECHA
    public void closeRightStackPane() {
        rightStackPane.setPrefWidth(0);
        vBoxEC.setVisible(false); vBoxEC.setManaged(false);
        vBoxProfile.setVisible(false); vBoxProfile.setManaged(false);
        vBoxAdder.setVisible(false); vBoxAdder.setManaged(false);
        spOpen = false;
        vBoxECOpen = false;
        vBoxProfOpen = false;
        vBoxAdderOpen = false;
    }
    
    //ABRIR PESTAÑAS DE LA DERECHA
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
    
    private void openExpenseAdder() {
        closeRightStackPane(); 
        rightStackPane.setPrefWidth(600);
        vBoxAdder.setVisible(true); vBoxAdder.setManaged(true);
        spOpen = true;
        vBoxAdderOpen = true;
    }
    
    @FXML
    public void cancelStackPaneButtonAction(ActionEvent event) {
        closeRightStackPane();
    }

    // Método para obtener una referencia al StackPane desde otras clases
    public StackPane getRightStackPane() {
        return rightStackPane;
    }
    
    //----------------------------EDITAR CATEGORÍAS-----------------------------
    
    @FXML
    public void handleMenuButtonAction(ActionEvent event) {

        MenuButton menuButton = (MenuButton) event.getSource();
        String selectedOption = menuButton.getText();
        cCategory.setText(selectedOption);
    }


   /* //LISTA DE CATEGORIAS DE EJEMPLO 
    public static List<String> obtenerListaDeCategorias() {
        // Aquí defines cómo obtendrás la lista de categorías, por ejemplo, desde una base de datos o un archivo
        List<String> arrayCategorias = new ArrayList<>();
        // Agrega categorías de ejemplo
        arrayCategorias.add("Category 1");
        arrayCategorias.add("Category 2");
        arrayCategorias.add("Category 3");
        return arrayCategorias;
    }*/
    
    
    
    // Lista que contiene las categorías disponibles
    //List<Category> categoryList = Acount.getInstance().getUserCategories();     //ERROR
    
    //public void MenuCategorias() {
        
        // Limpiar items existentes en MenuButton al inicio
    //    sCategory.getItems().clear();
        
        // Si no hay categorías en la lista, agregar un MenuItem que indique "No categories"
    //    if (categoryList.isEmpty()) {
    //        MenuItem noCategoriesItem = new MenuItem("No categories");
    //        sCategory.getItems().add(noCategoriesItem);
    //    } else {
            
            // Si hay categorías, creamos un MenuItem por cada una de ellas
    //        for (Category category : categoryList) {
    //            MenuItem categoryItem = new MenuItem(category.getName());
    //            sCategory.getItems().addAll(categoryItem);
    //        }
              
    //    }
    //}



    //----------------------------------PERFIL----------------------------------
    
    
    
    
    
    
    
    
    
    
    
    //-------------------------------AÑADIR GASTO-------------------------------
    
    
    
    
    
    
    
    
    
    
    
    //------------------------------GESTIÓN GASTOS------------------------------

    
       
    
}
    
    
    
    
    
    
    
    

