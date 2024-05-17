/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

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
    private Button editAdder;
    @FXML
    private ListView<Charge> listViewCharges;

    private ObservableList<Charge> charges = FXCollections.observableArrayList();

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

    private ObservableList<String> categories = FXCollections.observableArrayList();
    
    private BooleanBinding cNameFieldValid;
    private BooleanBinding cCategoryFieldInvalid;

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
    @FXML
    private Button pImageButton;




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
        editCategories.setOnAction((event) -> { if (!vBoxECOpen) openEditCategory(); });

        // Profile button
        editProfile.setOnAction((event) -> { if (!vBoxProfOpen) openProfile(); });

        // Expense adder button
        editAdder.setOnAction((event) -> { if (!vBoxAdderOpen) openExpenseAdder(); });

        //---------------------------CATEGORÍA----------------------------
        vBoxNewCategory.setVisible(false);
        vBoxNewCategory.setManaged(false);

        // Configurar el selector de categorías
        configureCategorySelector();

        cAdd.setOnAction((event) -> { showNewCategoryDialog(); });
        
        cCancel.setOnAction((event) -> { handleCancelButtonAction(event); });
        
        cNameFieldValid = nombreCat.textProperty().isNotEmpty();
        cAdd1.disableProperty().bind(cNameFieldValid.not());
        cAdd1.setOnAction((event) -> { handleAddCategoryAction(event); });
        
        cCategoryFieldInvalid = cCategory.textProperty().isEqualTo("None");
        cDelete.disableProperty().bind(cCategoryFieldInvalid);
        cDelete.setOnAction((event) -> { handleDeleteButtonAction(event); });
        
        cCategory.styleProperty().bind(
            Bindings.when(cCategoryFieldInvalid)
                .then("-fx-fill: #9a9a9a;")
                .otherwise("-fx-fill: #e80000;")
        );
        

        
        
        
        
        //----------------------------PERFIL-----------------------------         
        try {            
            pUser.setText("@" + Acount.getInstance().getLoggedUser().getNickName()); //@UsuarioActual
        } catch (AcountDAOException | IOException ex) {}
        
        pImageButton.setOnAction((event) -> {
            handleImageClick(event);
        });
        






        //----------------------------GASTOS-----------------------------
        listViewCharges.setItems(charges);
        listViewCharges.setCellFactory(CheckBoxListCell.forListView(new Callback<Charge, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Charge charge) {
                return new SimpleBooleanProperty();
            }
        }));

        selectAll.setOnAction(event -> selectAllCharges());

        aAdd.setOnAction(event -> addCharge());
        aCancel.setOnAction(event -> clearAdderFields());

    } //--------------------------FIN INICIALIZADOR-----------------------------

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

    private void configureCategorySelector() {
        // Limpiar las opciones actuales del selector
        sCategory.getItems().clear();

        // Crear una opción para cada categoría existente
        for (String category : categories) {
            MenuItem menuItem = new MenuItem(category);
            menuItem.setOnAction(event -> {
                cCategory.setText(menuItem.getText());
            });
            sCategory.getItems().add(menuItem);
        }

        MenuItem newItem = new MenuItem("New Category");
        newItem.setOnAction(event -> showNewCategoryDialog());
        sCategory.getItems().add(newItem);
    }

    private void handleDeleteButtonAction(ActionEvent event) {
        String categoryName = cCategory.getText();
        if (!categoryName.equals("None")) {
            // Mostrar un diálogo de confirmación antes de eliminar la categoría
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Category");
            alert.setContentText("Are you sure you want to delete the selected category?");
            alert.initModality(Modality.APPLICATION_MODAL);

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            stage.toFront();

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Eliminar la categoría
                    categories.removeIf(category -> category.equals(categoryName));
                    configureCategorySelector(); // Actualizar el selector de categorías
                    cCategory.setText("None"); // Restablecer el texto de categoría actual
                }
            });
        }
    }

    private void handleCancelButtonAction(ActionEvent event) {
        vBoxNewCategory.setVisible(false);
        vBoxNewCategory.setManaged(false);
        nombreCat.clear();
    }

    private void showNewCategoryDialog() {
        vBoxNewCategory.setVisible(true);
        vBoxNewCategory.setManaged(true);

        // Configurar el campo de texto de nombre de categoría
        nombreCat.setTextFormatter(new TextFormatter<>((Change c) -> {
            if (c.isAdded()) {
                String text = c.getControlNewText();
                if (text.length() > 20) {
                    c.setText("");
                }
            }
            return c;
        }));

        // Enfocar el campo de texto de nombre de categoría
        nombreCat.requestFocus();
    }

    private void handleAddCategoryAction(ActionEvent event) {
        String categoryName = nombreCat.getText().trim();
        
        // Verificar si la categoría ya existe
        if (categories.stream().anyMatch(category -> category.equalsIgnoreCase(categoryName))) {
            // Mostrar un mensaje de error si la categoría ya existe
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Category Already Exists");
            alert.setContentText("A category with the same name already exists.");
            alert.initModality(Modality.APPLICATION_MODAL);

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            stage.toFront();

            alert.showAndWait();
        } else {
            // Agregar la nueva categoría
            categories.add(categoryName);
            cCategory.setText(categoryName); // Establecer la nueva categoría como la actual
            configureCategorySelector(); // Actualizar el selector de categorías
            handleCancelButtonAction(null); // Ocultar el VBox de nueva categoría y limpiar el campo de texto
        }
    }

    
    
    
    //----------------------------------PERFIL----------------------------------

    public void handleImageClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Muestra el cuadro de diálogo de selección de archivo
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            pImage.setImage(image);
        } else {
            System.out.println("File selection cancelled.");
        }
        //Utils.circularCutout(pImage);

    }









    //-------------------------------AÑADIR GASTO-------------------------------

    private void clearAdderFields() {
        aName.clear();
        aCost.clear();
        aDesc.clear();
        aCategory.setText("Select Category");
        aDate.setValue(null);
        aErrorPrice.setText("");
        aExpAdded.setText("");
    }

    private void addCharge() {
        String name = aName.getText();
        String costText = aCost.getText();
        String description = aDesc.getText();
        String categoryText = aCategory.getText(); // You'll need to change this to get the actual selected category
        LocalDate date = aDate.getValue();

        if (name.isEmpty() || costText.isEmpty() || description.isEmpty() || categoryText.equals("Select Category") || date == null) {
            aErrorPrice.setText("Please fill in all fields.");
            return;
        }

        double cost;
        try {
            cost = Double.parseDouble(costText);
        } catch (NumberFormatException e) {
            aErrorPrice.setText("Invalid price.");
            return;
        }

        // Create new Charge with category as null
        //Charge newCharge = new Charge(name, description, cost, 1, null, date, null);

        // Add to the charges list
        //addCharge(newCharge);

        // Clear fields and show success message
        clearAdderFields();
        aExpAdded.setText("Expense added successfully!");
    }










    //------------------------------GESTIÓN GASTOS------------------------------

    public void addCharge(Charge charge) {
        charges.add(charge);
    }

    public void removeCharge(Charge charge) {
        charges.remove(charge);
    }

    private void selectAllCharges() {
        for (Charge charge : charges) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(true);
        }
    }

    @FXML
    private void NewCategoryButtonAction(ActionEvent event) {
    }

}