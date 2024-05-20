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
import java.util.HashMap;
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
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
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
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
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
    @FXML
    private Button editCategoriesImg;
    @FXML
    private Button printImg;

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
    private Map<String, String> categoryDescriptions = new HashMap<>(); // Mapa para almacenar descripciones
    
    private BooleanBinding cNameFieldValid;
    private BooleanBinding cCategoryFieldInvalid;
    
    @FXML
    private VBox vBoxEditCategory;
    @FXML
    private VBox vBoxEditCat;
    @FXML
    private TextField nombreCat1;
    @FXML
    private TextArea descCat1;
    @FXML
    private VBox vBoxCat;
    @FXML
    private Text nameCatForEdit;
    @FXML
    private Text descCatForEdit;
    @FXML
    private HBox cCatEdit;
    @FXML
    private Button cEdit;
    @FXML
    private HBox cCatConfirmChanges;
    @FXML
    private Button cEdit1;

    //PERFIL
    @FXML
    private ImageView pImage;
    private ImageView pImageSecond;
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
    
    //PROFILE
    private String lastName;
    private String lastSurname;
    private String lastEmail;
    private BooleanBinding fieldsValid;
    private BooleanProperty textFieldsChanged = new SimpleBooleanProperty(false);
    


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
        editCategoriesImg.setOnAction((event) -> { if (!vBoxECOpen) openEditCategory(); });

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
        
        // Apartado de edición y visualización de las categorías
        vBoxEditCategory.setVisible(false); vBoxEditCategory.setManaged(false);
        vBoxEditCat.setVisible(false); vBoxEditCat.setManaged(false);
        vBoxCat.setVisible(false); vBoxCat.setManaged(false);
        cCatEdit.setVisible(false); cCatEdit.setManaged(false);
        cCatConfirmChanges.setVisible(false); cCatConfirmChanges.setManaged(false);
        
        
        //----------------------------PERFIL-----------------------------         
        
        try {            
            pUser.setText("@" + Acount.getInstance().getLoggedUser().getNickName()); //@UsuarioActual
            pName.setText(Acount.getInstance().getLoggedUser().getName()); 
            pSurname.setText(Acount.getInstance().getLoggedUser().getSurname()); 
            pEmail.setText(Acount.getInstance().getLoggedUser().getEmail()); //@UsuarioActual
            editProfile.setText(Acount.getInstance().getLoggedUser().getNickName());
            //Salvamos campos originales
            lastName = pName.getText();
            lastSurname = pSurname.getText();
            lastEmail = pEmail.getText();
            //
        } catch (AcountDAOException | IOException ex) {}
        
        pImageButton.setOnAction((event) -> {
            handleImageClick(event);
        });
        
        pEdit.setOnAction((event) -> {
            editProfile(event);
        });
        
        pConfChanges.setOnAction((event) -> {
            confirmChanges(event);
        });
        
        pCancel.setOnAction((event) -> {
            cancelProfile(event);
        });
        
        pLogOut.setOnAction((event) -> {
            try {
                logout(event);
            } catch (IOException ex) {}
        });
        
        fieldsValid = pName.textProperty().isNotEmpty()
                .and(pSurname.textProperty().isNotEmpty())
                .and(pEmail.textProperty().isNotEmpty());

        pConfChanges.disableProperty().bind(fieldsValid.not());

        //Declara el objeto de tipo listener que escucha si hay cambios en el string que se le pasa como parámetro
        ChangeListener<String> textFieldChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textFieldsChanged.set(true);
            }
        };
        
        //Se le pasa como parámetro los 3 textfields
        pName.textProperty().addListener(textFieldChangeListener);
        pSurname.textProperty().addListener(textFieldChangeListener);
        pEmail.textProperty().addListener(textFieldChangeListener);
        
        //Mientras no haya cambios pConfChanges está deshabilitado
        pConfChanges.disableProperty().bind(textFieldsChanged.not());
       
        



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
        cancelProfile(null);
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
        
        if (!selectedOption.equals("New Category")) {
            nameCatForEdit.setText(selectedOption);
            descCatForEdit.setText(categoryDescriptions.get(selectedOption));
            
            vBoxCatAppear();
        }
    }

    private void configureCategorySelector() {
        // Limpiar las opciones actuales del selector
        sCategory.getItems().clear();

        // Crear una opción para cada categoría existente
        for (String category : categories) {
            MenuItem item = new MenuItem(category);
            item.setOnAction(event -> {
                cCategory.setText(category);
                nameCatForEdit.setText(category);
                descCatForEdit.setText(categoryDescriptions.get(category));
                
            });
            sCategory.getItems().add(item);
        }

        MenuItem newItem = new MenuItem("New Category");
        newItem.setOnAction(event -> showNewCategoryDialog());
        sCategory.getItems().add(newItem);
    }

    private void handleDeleteButtonAction(ActionEvent event) {
        String selectedCategory = cCategory.getText();
        if (!selectedCategory.equals("None")) {
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
                    categories.removeIf(category -> category.equals(selectedCategory));
                    categoryDescriptions.remove(selectedCategory);
                    vBoxEditCategory.setVisible(false); vBoxEditCategory.setManaged(false);
                    configureCategorySelector(); // Actualizar el selector de categorías
                    cCategory.setText("None"); // Restablecer el texto de categoría actual
                    vBoxCatDisappear();
                }
            });
        }
    }

    private void handleCancelButtonAction(ActionEvent event) {
        hideNewCategoryDialog();
        nombreCat.clear();
    }
    
    // Método para agregar una categoría
    private void addCategory(String name, String description) {
        categories.add(name);
        categoryDescriptions.put(name, description);
    }

    private void showNewCategoryDialog() {
        vBoxNewCategory.setVisible(true); vBoxNewCategory.setManaged(true);

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
        
        descCat.setText("");

        // Enfocar el campo de texto de nombre de categoría
        nombreCat.requestFocus();
    }
    private void hideNewCategoryDialog() {
        vBoxNewCategory.setVisible(false);
        vBoxNewCategory.setManaged(false);
    }

    private void handleAddCategoryAction(ActionEvent event) {
        String name = nombreCat.getText().trim();
        String description = descCat.getText().trim();
        
        // Verificar si la categoría ya existe
        if (categories.stream().anyMatch(category -> category.equalsIgnoreCase(name))) {
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
            addCategory(name, description);
            cCategory.setText(name); // Establecer la nueva categoría como la actual
            vBoxEditCatDisappear();
            vBoxCatAppear();
            configureCategorySelector(); // Actualizar el selector de categorías
            handleCancelButtonAction(null); // Ocultar el VBox de nueva categoría y limpiar el campo de texto
        }
    }
    
    // Métodos Edit
    public void vBoxCatAppear() {
        String categoryName = nombreCat.getText().trim();
        nameCatForEdit.setText(categoryName);
        
        String categoryDesc = categoryDescriptions.get(categoryName);
        descCatForEdit.setText(categoryDesc);
        
        cCatEdit.setVisible(true); cCatEdit.setManaged(true);
        cCatConfirmChanges.setVisible(false); cCatConfirmChanges.setManaged(false);
        
        vBoxCat.setVisible(true); vBoxCat.setManaged(true);
        vBoxEditCategory.setVisible(true); vBoxEditCategory.setManaged(true);
    }
    
    public void vBoxCatDisappear() {
        vBoxCat.setVisible(false); vBoxCat.setManaged(false);
        cCatEdit.setVisible(false); cCatEdit.setManaged(false);
    }
    
    public void vBoxEditCatAppear() {
        vBoxCatDisappear();
        
        String categoryName = nombreCat.getText().trim();
        nombreCat1.setText(categoryName);
        
        String categoryDesc = descCat.getText().trim();
        descCat1.setText(categoryDesc);
        
        cCatConfirmChanges.setVisible(true); cCatConfirmChanges.setManaged(true);
        cCatEdit.setVisible(false); cCatEdit.setManaged(false);
        
        vBoxEditCategory.setVisible(true); vBoxEditCategory.setManaged(true);
        vBoxEditCat.setVisible(true); vBoxEditCat.setManaged(true);
    }
    
    public void vBoxEditCatDisappear() {
        vBoxEditCat.setVisible(false); vBoxEditCat.setManaged(false);
        cCatConfirmChanges.setVisible(false); cCatConfirmChanges.setManaged(false);
    }
    
    public void vBoxCatChange() {
        String categoryName = cCategory.getText().trim();
        nameCatForEdit.setText(categoryName);
        
        String categoryDesc = descCat.getText().trim();
        descCatForEdit.setText(categoryDesc);
        
        cCatEdit.setVisible(true); cCatEdit.setManaged(true);
        cCatConfirmChanges.setVisible(false); cCatConfirmChanges.setManaged(false);
        
        vBoxCat.setVisible(true); vBoxCat.setManaged(true);
        vBoxEditCategory.setVisible(true); vBoxEditCategory.setManaged(true);
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
            pImageSecond.setImage(image);
            Utils.circularCutout(pImage);
            Utils.circularCutout(pImageSecond);
        } else {
            System.out.println("File selection cancelled.");
        }
    }
    
    public void editProfile(ActionEvent event) {
        pChanged.setVisible(false);
        pEdit.setVisible(false); 
        pCancel.setVisible(true);
        pConfChanges.setVisible(true);
        pName.setDisable(false);
        pSurname.setDisable(false);
        pEmail.setDisable(false);
        pConfChanges.setDisable(false);
    }
    
    public void cancelProfile(ActionEvent event) {
        //Restablecemos antiguos campos
        pName.setText(lastName);
        pSurname.setText(lastSurname);
        pEmail.setText(lastEmail);
        //
        pCancel.setVisible(false);
        pChanged.setVisible(false);
        pConfChanges.setVisible(false);
        pEdit.setVisible(true);
        pErrorEmail.setVisible(false);
        pName.setDisable(true);
        pSurname.setDisable(true);
        pEmail.setDisable(true);
    }
    
    
    public void confirmChanges(ActionEvent event) {
        if(!Utils.checkEmail(pEmail.getText())){
            pErrorEmail.setVisible(true);
            pConfChanges.setDisable(true);
        }
        else{
            //Salvamos nuevos campos
            lastName = pName.getText();
            lastSurname = pSurname.getText();
            lastEmail = pEmail.getText();
            //
            pErrorEmail.setVisible(false);
            pConfChanges.setVisible(false);
            pCancel.setVisible(false);
            pChanged.setVisible(true);
            pEdit.setVisible(true);
            pName.setDisable(true);
            pSurname.setDisable(true);
            pEmail.setDisable(true);
        }
    }

    public void logout(ActionEvent event) throws IOException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, buttonTypeCancel);
        alert.setTitle("Logout Confimation");
        alert.setHeaderText("You are going to logout");
        alert.setContentText("Are you sure that you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //logout

            } else {}
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


}