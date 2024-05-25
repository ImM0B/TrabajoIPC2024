/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import org.apache.pdfbox.pdmodel.font.PDFont;

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
    private boolean vBoxEditorOpen = false;
    private boolean vBoxVisualizerOpen = false;
    
    private BooleanProperty spOpenProperty = new SimpleBooleanProperty(false);
    

    //MENÚ
    @FXML
    private Button editCategories;
    @FXML
    private Button editProfile;
    @FXML
    private Button editCategoriesImg;

    @FXML
    private Button editAdder;
    @FXML
    private Button delete;
    @FXML
    private Button delete1;
    @FXML
    private TableView<Charge> tableView;
    @FXML
    private HBox noChargesHBox;
    @FXML
    private TextField mNameField;
    @FXML
    private TextField mCategoryField;
    @FXML
    private HBox mHBox;
    @FXML
    private DatePicker mFirstDate;
    @FXML
    private DatePicker mLastDate;
    @FXML
    private MenuItem mName;
    @FXML
    private MenuItem mDate;
    @FXML
    private MenuItem mCategory;
    @FXML
    private MenuItem mNoFilter;
    @FXML
    private MenuButton filter;
    
    private ObservableList<Charge> charges = FXCollections.observableArrayList();
    
    //Listas observables para los filtros
    private ObservableList<Charge> nameFilterList = FXCollections.observableArrayList();
    private ObservableList<Charge> categoryFilterList = FXCollections.observableArrayList();
    private ObservableList<Charge> dateFilterList = FXCollections.observableArrayList();

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
    @FXML
    private Button cCancel1;
    
    //PERFIL
    @FXML
    private ImageView pImage;
    @FXML
    private ImageView pPerfilImage2;
    @FXML
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
    
    //PROFILE
    private String lastName;
    private String lastSurname;
    private String lastEmail;
    private BooleanBinding pFieldsValid;
    private BooleanProperty textFieldsChanged = new SimpleBooleanProperty(false);

    //GASTOS ADDER
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
    private TextField aUnits;
    @FXML
    private TextArea aDesc;
    @FXML
    private MenuButton aCategory;
    @FXML
    private DatePicker aDate;
    @FXML
    private ImageView aImageView;
    @FXML
    private Button aImage;
    @FXML
    private Text aImageText;
    @FXML
    private Button pImageButton;
    private BooleanBinding gNameFieldValid;
    private BooleanBinding gFieldsValid;
    private BooleanProperty gTextFieldsChanged = new SimpleBooleanProperty(false);
    @FXML
    private TableColumn<?, ?> fillingCol;
    
    private boolean isUpdating = false;
    @FXML
    private VBox vBoxNewCategory1;
    @FXML
    private TextField nombreCat2;
    @FXML
    private TextArea descCat2;
    @FXML
    private Button cAdd2;
    @FXML
    private Button cCancel2;
    
    //GASTOS EDITOR Y VISUALIZADOR
    @FXML
    private VBox vBoxEditor;
    @FXML
    private Text gName;
    @FXML
    private Text gCost;
    @FXML
    private Text gUnits;
    @FXML
    private Text gDesc;
    @FXML
    private MenuButton gCategory;
    @FXML
    private DatePicker gDate;
    @FXML
    private Button gEdit;
    @FXML
    private TextField eName;
    @FXML
    private TextField eCost;
    @FXML
    private TextField eUnits;
    @FXML
    private TextArea eDesc;
    @FXML
    private MenuButton eCategory;
    @FXML
    private DatePicker eDate;
    @FXML
    private Button eImage;
    @FXML
    private ImageView eImageView;
    @FXML
    private Text eImageText;
    @FXML
    private Label eErrorPrice;
    @FXML
    private Label eExpEdited;
    @FXML
    private VBox vBoxNewCategory11;
    @FXML
    private TextField nombreCat3;
    @FXML
    private TextArea descCat3;
    @FXML
    private Button eAdd;
    @FXML
    private Button eCancel;
    @FXML
    private Button eConfirmChanges;
    @FXML
    private VBox vBoxVisualizer;
    @FXML
    private ImageView gImage;
    @FXML
    private Text gImageText;
    @FXML
    private Button deleteImage;
    
    //ELEMENTOS GASTOS E IMPRESIÓN
    @FXML
    private Button bImagPrint;
    @FXML
    private Button bPrint;
    private TableView<Charge> tablaGastos;
    
    private ObservableList<Charge> gastos;
    @FXML
    private TableColumn<?, ?> checkCol;
    @FXML
    private TableColumn<Charge, String> nameCol;
    @FXML
    private TableColumn<Charge, String> categoryCol;
    @FXML
    private TableColumn<Charge, LocalDate> dateCol;
    @FXML
    private TableColumn<Charge, Void> optionsCol;
    @FXML
    private VBox vBoxBorder;;
    

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
        
        // Expense deleter button
        delete.setOnAction(event -> { try { deleteCharge(); adjustHeightToFitContent(tableView); } catch (AcountDAOException | IOException ex) {} });
        
        delete1.setOnAction(event -> { try { deleteAllCharges(); adjustHeightToFitContent(tableView); } catch (AcountDAOException | IOException ex) {} });
        
        //FILTROS
        mNoFilter.setVisible(false);
        
        mName.setOnAction(event -> nameFilterAppear());
        
        mNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            try { nameFilter(); } catch (AcountDAOException | IOException e) {}
        });
        
        mCategory.setOnAction(event -> catFilterAppear());
        
        mCategoryField.textProperty().addListener((observable, oldValue, newValue) -> {
            try { catFilter(); } catch (AcountDAOException | IOException e) {}
        });
        
        mDate.setOnAction(event -> dateFilterAppear());
        
        mFirstDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            try { dateFilter(); } catch (AcountDAOException | IOException e) {}
        });

        mLastDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            try { dateFilter(); } catch (AcountDAOException | IOException e) {}
        });
        
        mNoFilter.setOnAction(event -> noFilter());

        //---------------------------CATEGORÍA----------------------------  
        
        vBoxNewCategory.setVisible(false);
        vBoxNewCategory.setManaged(false);

        // Configurar el selector de categorías
        configureCategorySelector();

        cAdd.setOnAction((event) -> { showNewCategoryDialog(); });
        
        cCancel.setOnAction((event) -> { handleCancelButtonAction(event); });
        
        cNameFieldValid = nombreCat.textProperty().isNotEmpty();
        cAdd1.disableProperty().bind(cNameFieldValid.not());
        cAdd1.setOnAction((event) -> { try { handleAddCategoryAction(event); } catch (AcountDAOException | IOException ex) {} });
        
        cCategoryFieldInvalid = cCategory.textProperty().isEqualTo("None");
        cDelete.disableProperty().bind(cCategoryFieldInvalid);
        cDelete.setOnAction((event) -> { handleDeleteButtonAction(event); });
        
        cCategory.styleProperty().bind(
            Bindings.when(cCategoryFieldInvalid)
                .then("-fx-fill: #9a9a9a;")
                .otherwise("-fx-fill: #e80000;")
        );
        
        cEdit.setOnAction(event -> { vBoxEditCatAppear(); cEdit1.setDisable(true); });
        
        cCancel1.setOnAction(event -> vBoxCatAppear());
        
        StringProperty nombreCat1Anterior = new SimpleStringProperty("");
        StringProperty descCat1Anterior = new SimpleStringProperty("");
        nombreCat1.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.equals(nombreCat1Anterior.get()) || !descCat1.getText().equals(descCat1Anterior.get())) {
                cEdit1.setDisable(false); // Habilita el botón si hay un cambio
            } else {
                cEdit1.setDisable(true); // Deshabilita el botón si no hay cambios
            }
            nombreCat1Anterior.set(newValue); // Actualiza el valor anterior
        });
        descCat1.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.equals(descCat1Anterior.get()) || !nombreCat1.getText().equals(nombreCat1Anterior.get())) {
                cEdit1.setDisable(false); // Habilita el botón si hay un cambio
            } else {
                cEdit1.setDisable(true); // Deshabilita el botón si no hay cambios
            }
            descCat1Anterior.set(newValue); // Actualiza el valor anterior
        });
        
        cEdit1.setOnAction(event -> handleModifyCategory() );
        
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
        } catch (AcountDAOException | IOException ex) {}
        
        pImageButton.setOnAction((event) -> { handleImageClick(event); });
        pEdit.setOnAction((event) -> { editProfile(event); });
        pConfChanges.setOnAction((event) -> { confirmChangesProfile(event); });
        pCancel.setOnAction((event) -> { cancelProfile(event); });
        pLogOut.setOnAction((event) -> { try { logout(event); } catch (IOException ex) {} catch (AcountDAOException ex) {} });
        
        pFieldsValid = pName.textProperty().isNotEmpty()
                .and(pSurname.textProperty().isNotEmpty())
                .and(pEmail.textProperty().isNotEmpty());
        
        pConfChanges.disableProperty().bind(pFieldsValid.not());

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

        //----------------------------GASTOS-----------------------------
        tableView.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-alignment: CENTER-LEFT");
        
        try {
            Acount currentAccount = Acount.getInstance();
            List<Charge> userCharges = currentAccount.getUserCharges();
            charges.addAll(userCharges);
        } catch (AcountDAOException | IOException ex) {
            System.out.println("Error loading user charges");
        }
        
        setupChargeTable(tableView, nameCol, categoryCol, dateCol, charges);
        tableView.setFixedCellSize(35);
        adjustHeightToFitContent(tableView);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        BooleanBinding fieldsEmpty = Bindings.createBooleanBinding(() ->
                        aName.getText().trim().isEmpty() ||
                        aCost.getText().trim().isEmpty() ||
                        aCategory.getText().trim().equals("Category") ||
                        aDate.getValue() == null,
                aName.textProperty(),
                aCost.textProperty(),
                aCategory.textProperty(),
                aDate.valueProperty()
        );

        aAdd.disableProperty().bind(fieldsEmpty);
        gNameFieldValid = nombreCat2.textProperty().isNotEmpty();
        cAdd2.disableProperty().bind(gNameFieldValid.not());
        cAdd2.setOnAction((event) -> { try { handleAddCategoryAction1(); } catch (AcountDAOException | IOException ex) {} });
        cCancel2.setOnAction((event) -> { hideNewCategoryDialog1(); });

        aAdd.setOnAction(event -> { addCharge(); adjustHeightToFitContent(tableView); });
        aCancel.setOnAction(event -> { clearAdderFields(); closeRightStackPane(); });
        
        aImage.setOnAction((event) -> handleImageClick1(event));
        
        // Actualizar spOpenProperty cuando cambia el valor de la pila derecha
        rightStackPane.widthProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() > 0) {
                decreaseFillingCol();
            } else {
                increaseFillingCol();
            }
        });
                
        //VISUALIZAR GASTOS
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Charge>() {
            @Override
            public void changed(ObservableValue<? extends Charge> observable, Charge oldValue, Charge newValue) {
                if (newValue != null) {
                    Charge charge = tableView.getSelectionModel().selectedItemProperty().get();
                    gName.setText(charge.getName());
                    gCost.setText(String.valueOf(charge.getCost()));
                    gUnits.setText(String.valueOf(charge.getUnits()));
                    gDesc.setText(charge.getDescription());
                    if(gDesc.getText().equals("")) gDesc.setText("No description");
                    gCategory.setDisable(true);
                    gCategory.setText(charge.getCategory().getName());
                    gDate.setDisable(true);
                    gDate.setValue(charge.getDate());
                    if(charge.getImageScan() != null) { gImage.setImage(charge.getImageScan()); }
                    openExpenseVisualizer();
                }
            }
        });
        
        gEdit.setOnAction(event -> {
            Charge charge = tableView.getSelectionModel().selectedItemProperty().get();
            eName.setText(gName.getText());
            eCost.setText(gCost.getText());
            eUnits.setText(gUnits.getText());
            if(!gDesc.getText().equals("No description")) eDesc.setText(gDesc.getText());
            eCategory.setText(gCategory.getText());
            eDate.setValue(gDate.getValue());
            if(charge.getImageScan() != null){ eImageView.setImage(charge.getImageScan()); }
            openExpenseEditor();
        });
        
        //EDITAR GASTOS
        eCancel.setOnAction(event -> hideNewCategoryDialog11());
        eAdd.setOnAction(event -> { try { handleAddCategoryAction11(); } catch (AcountDAOException | IOException ex) {} });
        eImage.setOnAction((event) -> handleImageClick11(event));
        eConfirmChanges.setOnAction(event -> handleConfirmChangesCharges());
        
        //COLUMNA GASTOS
        // Configurar la CellFactory de la columna
        Callback<TableColumn<Charge, Void>, TableCell<Charge, Void>> cellFactory = (final TableColumn<Charge, Void> param) -> new TableCell<Charge, Void>() {
            private final Button btn1 = createButtonWithImage("../icons/share.png");
            private final Button btn2 = createButtonWithImage("../icons/box.png");
            private final Button btn3 = createButtonWithImage("../icons/papelera_small.png");
            
            
            
            HBox buttonBox = createButtonBox(btn1, btn2, btn3);
            
            {
                buttonBox.setAlignment(Pos.CENTER);
                // Configurar eventos de los botones aquí
                btn1.setOnAction((ActionEvent event) -> {
                    Charge charge = getTableView().getItems().get(getIndex());
                    // Acción del botón 1
                    notImplementedMessage();
                });
                
                btn2.setOnAction((ActionEvent event) -> {
                    Charge charge = getTableView().getItems().get(getIndex());
                    // Acción del botón 2
                    notImplementedMessage();
                });
                
                btn3.setOnAction((ActionEvent event) -> {
                    Charge charge = getTableView().getItems().get(getIndex());
                    // Acción del botón 3
                    try { deleteCharge2(charge); } catch (AcountDAOException | IOException ex) {}
                });
                
                btn1.setOnMouseEntered(e -> setCursor(Cursor.HAND));
                btn1.setOnMouseExited(e -> setCursor(Cursor.DEFAULT));

                btn2.setOnMouseEntered(e -> setCursor(Cursor.HAND));
                btn2.setOnMouseExited(e -> setCursor(Cursor.DEFAULT));

                btn3.setOnMouseEntered(e -> setCursor(Cursor.HAND));
                btn3.setOnMouseExited(e -> setCursor(Cursor.DEFAULT));
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                }
            }
        };

        // Asignar la CellFactory a la columna existente
        optionsCol.setCellFactory(cellFactory);
        
        deleteImage.setOnAction(event -> deleteImage());
        
        //----------------------------IMPRIMIR---------------------------
        bPrint.setOnAction(event -> {
                mostrarAlertayGenerarPDF(tableView);
        });
        
        bImagPrint.setOnAction(event -> {
                mostrarAlertayGenerarPDF(tableView);
        });        
        
    } //--------------------------FIN INICIALIZADOR-----------------------------

    //CERRAR TODAS LAS PESTAÑAS DE LA DERECHA
    @FXML
    public void closeRightStackPane() {
        rightStackPane.setPrefWidth(0);
        vBoxEC.setVisible(false); vBoxEC.setManaged(false);
        vBoxProfile.setVisible(false); vBoxProfile.setManaged(false);
        vBoxAdder.setVisible(false); vBoxAdder.setManaged(false);
        vBoxEditor.setVisible(false); vBoxEditor.setVisible(false);
        vBoxVisualizer.setVisible(false); vBoxVisualizer.setVisible(false);
        tableView.refresh();
        spOpen = false;
        vBoxECOpen = false;
        vBoxProfOpen = false;
        vBoxAdderOpen = false;
        vBoxEditorOpen = false;
        vBoxVisualizerOpen = false;
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
        pPerfilImage2.setVisible(true);
        pImageButton.setVisible(false);
        rightStackPane.setPrefWidth(600);
        vBoxProfile.setVisible(true); vBoxProfile.setManaged(true);
        spOpen = true;
        vBoxProfOpen = true;
    }

    private void openExpenseAdder() {
        closeRightStackPane(); 
        hideNewCategoryDialog1();
        aErrorPrice.setVisible(false);
        aExpAdded.setVisible(false);
        rightStackPane.setPrefWidth(600);
        vBoxAdder.setVisible(true); vBoxAdder.setManaged(true);
        spOpen = true;
        vBoxAdderOpen = true;
        aCategory.setText("Category");
        configureAdderCategorySelector();
    }
    
    private void openExpenseVisualizer() {
        closeRightStackPane();
        rightStackPane.setPrefWidth(600);
        vBoxVisualizer.setVisible(true); vBoxVisualizer.setVisible(true);
        spOpen = true;
        vBoxVisualizerOpen = true;
    }
    
    private void openExpenseEditor() {
        closeRightStackPane();
        hideNewCategoryDialog11();
        rightStackPane.setPrefWidth(600);
        vBoxEditor.setVisible(true); vBoxEditor.setVisible(true);
        spOpen = true;
        vBoxEditorOpen = true;
        configureEditorCategorySelector1();
    }

    @FXML
    public void cancelStackPaneButtonAction(ActionEvent event) {
        closeRightStackPane();
        cancelProfile(null);
    }

    // Método para obtener una referencia al StackPane desde otras clases
    public StackPane getRightStackPane() {
        return rightStackPane;
    }
    
    // Método para crear botones con imagen y fondo personalizado
    private Button createButtonWithImage(String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(15);
        imageView.setFitHeight(15);

        Button button = new Button();
        button.setGraphic(imageView);
        button.setPrefWidth(15);
        button.setPrefHeight(15);
        button.setStyle("-fx-background-color: #404040; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        return button;
    }
    
    private HBox createButtonBox(Button... buttons) {
    HBox box = new HBox(5); // Establece el espacio entre los botones en 5 píxeles
    box.getChildren().addAll(buttons);
    return box;
}


    //--------------------------------FILTROS-----------------------------------
    
    // NO FILTER
    private void noFilter() {
        if(!filter.getText().equals("Filter")) {
            try {
                charges.clear();
                Acount currentAccount;
                currentAccount = Acount.getInstance();
                List<Charge> userCharges = currentAccount.getUserCharges();
                charges.addAll(userCharges);
                filter.setText("Filter");
                mNameField.setVisible(false);
                mCategoryField.setVisible(false);
                mHBox.setVisible(false);
                tableView.setItems(charges);
                adjustHeightToFitContent(tableView);
                
                //Limpia los campos de filtros
                mNameField.clear();
                mCategoryField.clear();
                mFirstDate.setValue(null);
                mLastDate.setValue(null);
                
                mNoFilter.setVisible(false);
            } catch (AcountDAOException | IOException ex) {}
        }
    }
    
    // NAME
    private void nameFilterAppear() {
        filter.setText("Name");
        mNameField.setVisible(true);   
        mNameField.requestFocus();
        
        mCategoryField.setVisible(false);
        mHBox.setVisible(false);
        //Limpia los campos de filtros
        mNameField.clear();
        mCategoryField.clear();
        mFirstDate.setValue(null);
        mLastDate.setValue(null);
        
        mNoFilter.setVisible(true);
    }
    
    private void nameFilter() throws AcountDAOException, IOException {
        Acount currentAccount = Acount.getInstance();
        List<Charge> userCharges = currentAccount.getUserCharges();
        nameFilterList.clear();
        
        // Buscar la categoría seleccionada por nombre
        for (Charge charge : userCharges) {
            if (charge.getName().contains(mNameField.getText())) {
                nameFilterList.add(charge);
            }
        }
        
        tableView.setItems(nameFilterList);
        adjustHeightToFitContent(tableView);
    }
    
    // CATEGORY
    private void catFilterAppear() {
        filter.setText("Category");
        mCategoryField.setVisible(true);   
        mCategoryField.requestFocus();
        
        mNameField.setVisible(false);
        mHBox.setVisible(false);
        //Limpia los campos de filtros
        mNameField.clear();
        mCategoryField.clear();
        mFirstDate.setValue(null);
        mLastDate.setValue(null);
        
        mNoFilter.setVisible(true);
    }
    
    private void catFilter() throws AcountDAOException, IOException {
        Acount currentAccount = Acount.getInstance();
        List<Charge> userCharges = currentAccount.getUserCharges();
        categoryFilterList.clear();
        
        // Buscar la categoría seleccionada por categoría
        for (Charge charge : userCharges) {
            if (charge.getCategory().getName().contains(mCategoryField.getText())) {
                categoryFilterList.add(charge);
            }
        }
        
        tableView.setItems(categoryFilterList);
        adjustHeightToFitContent(tableView);
    }
    
    // DATE
    private void dateFilterAppear() {
        filter.setText("Date");
        mHBox.setVisible(true);
        
        mNameField.setVisible(false);
        mCategoryField.setVisible(false);
        //Limpia los campos de filtros
        mNameField.clear();
        mCategoryField.clear();
        mFirstDate.setValue(null);
        mLastDate.setValue(null);
        
        mNoFilter.setVisible(true);
    }
    
    private void dateFilter() throws AcountDAOException, IOException {
        Acount currentAccount = Acount.getInstance();
        List<Charge> userCharges = currentAccount.getUserCharges();
        dateFilterList.clear();
        
        LocalDate firstDate = mFirstDate.getValue();
        LocalDate lastDate = mLastDate.getValue();

        for (Charge charge : userCharges) {
            LocalDate chargeDate = charge.getDate();

            if (firstDate != null && lastDate == null) {
                // Solo se introduce la primera fecha
                if (!chargeDate.isBefore(firstDate)) {
                    dateFilterList.add(charge);
                }
            } else if (firstDate == null && lastDate != null) {
                // Solo se introduce la última fecha
                if (!chargeDate.isAfter(lastDate)) {
                    dateFilterList.add(charge);
                }
            } else if (firstDate != null && lastDate != null) {
                // Se introducen ambas fechas
                if (!chargeDate.isBefore(firstDate) && !chargeDate.isAfter(lastDate)) {
                    dateFilterList.add(charge);
                }
            }
        }
        
        tableView.setItems(dateFilterList);
        adjustHeightToFitContent(tableView);
    }
    
    //----------------------------EDITAR CATEGORÍAS-----------------------------

    @FXML
    public void handleMenuButtonAction(ActionEvent event) {
    if (event.getSource() instanceof MenuButton) {
        MenuButton menuButton = (MenuButton) event.getSource();
        String selectedOption = menuButton.getText();
        cCategory.setText(selectedOption);

        if (!selectedOption.equals("New Category")) {
            try {
                Acount currentAccount = Acount.getInstance();
                List<Category> userCategories = currentAccount.getUserCategories();
                Category selectedCategory = null;

                // Buscar la categoría seleccionada por nombre
                for (Category category : userCategories) {
                    if (category.getName().equals(selectedOption)) {
                        selectedCategory = category;
                        break;
                    }
                }

                if (selectedCategory != null) {
                    nameCatForEdit.setText(selectedCategory.getName());
                    descCatForEdit.setText(selectedCategory.getDescription());
                    vBoxCatAppear();
                }
            } catch (AcountDAOException | IOException ex) {}
        }
    }
}


    private void configureCategorySelector() {
        // Limpiar las opciones actuales del selector
        sCategory.getItems().clear();

        // Crear una opción para cada categoría existente        
        try {
            Acount currentAccount = Acount.getInstance();
            List<Category> userCategories = currentAccount.getUserCategories();

            for (Category category : userCategories) {
                MenuItem item = new MenuItem(category.getName());
                item.setOnAction(event -> {
                    cCategory.setText(category.getName());
                    nameCatForEdit.setText(category.getName());
                    descCatForEdit.setText(category.getDescription());
                });
                sCategory.getItems().add(item);
            }
        } catch (AcountDAOException | IOException ex) { System.out.println("configureCateogrySelector ERROR"); }

        MenuItem newItem = new MenuItem("New Category");
        newItem.setOnAction(event -> showNewCategoryDialog());
        sCategory.getItems().add(newItem);
    }

    private void handleDeleteButtonAction(ActionEvent event) {
        String selectedCategoryName = cCategory.getText();
        if (!selectedCategoryName.equals("None")) {
            // Mostrar un diálogo de confirmación antes de eliminar la categoría
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete the selected category?");
            alert.setContentText("All expenses with this category will be eliminated as well");
            alert.initModality(Modality.APPLICATION_MODAL);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
            stage.getIcons().add(icon);
            stage.setAlwaysOnTop(true);
            stage.toFront();

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Eliminar la categoría
                    try {
                        Acount currentAccount = Acount.getInstance();
                        List<Category> userCategories = currentAccount.getUserCategories();
                        Category selectedCategory = null;
                        for (Category category : userCategories) {
                            if (category.getName().equals(selectedCategoryName)) {
                                selectedCategory = category;
                                break;
                            }
                        }
                        try {
                            List<Charge> chargesToRemove = new ArrayList<>();
                            for (Charge charge : charges) {
                                if (charge.getCategory().equals(selectedCategory)) {
                                    chargesToRemove.add(charge);
                                }
                            }
                            charges.removeAll(chargesToRemove);
                        } catch (Exception ex) {}
                        currentAccount.removeCategory(selectedCategory);
                        cCategory.setText("None");
                        setupChargeTable(tableView, nameCol, categoryCol, dateCol, charges);
                        vBoxCatDisappear();
                        configureCategorySelector();
                        handleMenuButtonAction(event);
                    } catch (AcountDAOException | IOException ex) { System.out.println("handleDeleteButtonAction ERROR"); }
                }
            });
        }
    }

    private void handleCancelButtonAction(ActionEvent event) {
        hideNewCategoryDialog();
        nombreCat.clear();
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

    private void handleAddCategoryAction(ActionEvent event) throws AcountDAOException, IOException {
        String name = nombreCat.getText().trim();
        String description = descCat.getText().trim();
        
        Acount currentAccount = Acount.getInstance();
        List<Category> userCategories = currentAccount.getUserCategories(); 
        Category selectedCategory = null;
        
        // Buscar la categoría seleccionada por nombre
        for (Category category : userCategories) {
            if (category.getName().equals(name)) {
                selectedCategory = category;
                break;
            }
        }
        
        // Verificar si la categoría ya existe
        if (selectedCategory != null) {
            // Mostrar un mensaje de error si la categoría ya existe
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Category Already Exists");
            alert.setContentText("A category with the same name already exists.");
            alert.initModality(Modality.APPLICATION_MODAL);

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
            stage.getIcons().add(icon);
            stage.setAlwaysOnTop(true);
            stage.toFront();

            alert.showAndWait();
        } else {
            // Agregar la nueva categoría
            try {
                // Llamar al método registerCategory() para crear una nueva categoría
                boolean categoryAdded = currentAccount.registerCategory(name, description);

                if (!categoryAdded) { System.out.println("handleAddCategoryAction ERROR"); }
                
                cCategory.setText(name); // Establecer la nueva categoría como la actual
                vBoxEditCatDisappear();
                vBoxCatAppear();
                configureCategorySelector(); // Actualizar el selector de categorías
                handleCancelButtonAction(null); // Ocultar el VBox de nueva categoría y limpiar el campo de texto
            } catch (AcountDAOException e) {}
        }
    }
    
    private void handleModifyCategory() {        
        try {
            String catName1 = cCategory.getText();
            String catName2 = nombreCat1.getText();
            
            Acount currentAccount = Acount.getInstance();
            List<Category> userCategories = currentAccount.getUserCategories(); 
            Category selectedCategory = null;

            // Buscar la categoría seleccionada por nombre
            for (Category category : userCategories) {
                if (category.getName().equals(catName1)) {
                    selectedCategory = category;
                    break;
                }
            }
            
            String catDesc1 = selectedCategory.getDescription();
            String catDesc2 = descCat1.getText();
            
            if(!catDesc1.equals(catDesc2)) {
                // Actualizar la descripción de la categoría
                selectedCategory.setDescription(catDesc2);
            }

            if(!catName1.equals(catName2)) {
                // Actualizar el nombre de la categoría
                selectedCategory.setName(catName2);

                // Actualizar en el selector de categorías
                for (MenuItem item : sCategory.getItems()) {
                    if (item.getText().equals(catName1)) {
                        item.setText(catName2);
                        break;
                    }
                }
                
                cCategory.setText(catName2); // Actualizar en la interfaz de usuario
            }
            
            configureCategorySelector();

            vBoxEditCatDisappear();
            vBoxCatAppear();
        } catch (AcountDAOException | IOException ex) { System.out.println("handleModifyCategory ERROR"); }
    }
    
    // Métodos Edit
    public void vBoxCatAppear() {
        try {
            vBoxEditCatDisappear();
            String categoryName = cCategory.getText().trim();
            nameCatForEdit.setText(categoryName);
            
            Acount currentAccount = Acount.getInstance();
            List<Category> userCategories = currentAccount.getUserCategories(); 
            Category selectedCategory = null;

            // Buscar la categoría seleccionada por nombre
            for (Category category : userCategories) {
                if (category.getName().equals(categoryName)) {
                    selectedCategory = category;
                    break;
                }
            }
            
            String categoryDesc = selectedCategory.getDescription();
            descCatForEdit.setText(categoryDesc);
            
            cCatEdit.setVisible(true); cCatEdit.setManaged(true);
            cCatConfirmChanges.setVisible(false); cCatConfirmChanges.setManaged(false);
        
            vBoxCat.setVisible(true); vBoxCat.setManaged(true);
            vBoxEditCategory.setVisible(true); vBoxEditCategory.setManaged(true);
        } catch (AcountDAOException | IOException ex) { System.out.println("vBoxCatAppear ERROR"); }
    }
    
    public void vBoxCatDisappear() {
        vBoxCat.setVisible(false); vBoxCat.setManaged(false);
        cCatEdit.setVisible(false); cCatEdit.setManaged(false);
    }
    
    public void vBoxEditCatAppear() {
        try {
            vBoxCatDisappear();
            String categoryName = cCategory.getText().trim();
            nombreCat1.setText(categoryName);
            
            Acount currentAccount = Acount.getInstance();
            List<Category> userCategories = currentAccount.getUserCategories(); 
            Category selectedCategory = null;

            // Buscar la categoría seleccionada por nombre
            for (Category category : userCategories) {
                if (category.getName().equals(categoryName)) {
                    selectedCategory = category;
                    break;
                }
            }
            
            String categoryDesc = selectedCategory.getDescription();
            descCat1.setText(categoryDesc);
            
            cCatConfirmChanges.setVisible(true); cCatConfirmChanges.setManaged(true);
            cCatEdit.setVisible(false); cCatEdit.setManaged(false);

            vBoxEditCategory.setVisible(true); vBoxEditCategory.setManaged(true);
            vBoxEditCat.setVisible(true); vBoxEditCat.setManaged(true);
        } catch (AcountDAOException | IOException ex) { System.out.println("vBoxEditCatAppear ERROR"); }
    }
    
    public void vBoxEditCatDisappear() {
        vBoxEditCat.setVisible(false); vBoxEditCat.setManaged(false);
        cCatConfirmChanges.setVisible(false); cCatConfirmChanges.setManaged(false);
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
            Utils.circularCutout(pImage);
        } else {
            System.out.println("handleImageClick ERROR");
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
        //Habilita cambiar la foto de perfil
        pPerfilImage2.setVisible(false);
        pImageButton.setVisible(true);
    }
    
    public void cancelProfile(ActionEvent event) {
        //Restablecemos antiguos campos
        pName.setText(lastName);
        pSurname.setText(lastSurname);
        pEmail.setText(lastEmail);
        
        pCancel.setVisible(false);
        pChanged.setVisible(false);
        pConfChanges.setVisible(false);
        pEdit.setVisible(true);
        pErrorEmail.setVisible(false);
        //Deshabilita cambiar la foto de perfil
        pPerfilImage2.setVisible(true);
        pImageButton.setVisible(false);
        pName.setDisable(true);
        pSurname.setDisable(true);
        pEmail.setDisable(true);
    }
    
    public void confirmChangesProfile(ActionEvent event) {
        if(!Utils.checkEmail(pEmail.getText())){
            pErrorEmail.setVisible(true);
            pConfChanges.setDisable(true);
        }
        else{
            //Salvamos nuevos campos
            lastName = pName.getText();
            lastSurname = pSurname.getText();
            lastEmail = pEmail.getText();
            
            pImageSecond.setImage(pImage.getImage());
            pPerfilImage2.setImage(pImage.getImage());
            Utils.circularCutout(pImageSecond);
            Utils.circularCutout(pPerfilImage2);
            
            pErrorEmail.setVisible(false);
            pConfChanges.setVisible(false);
            pCancel.setVisible(false);
            pChanged.setVisible(true);
            pEdit.setVisible(true);
            pPerfilImage2.setVisible(true);
            pImageButton.setVisible(false);
            pName.setDisable(true);
            pSurname.setDisable(true);
            pEmail.setDisable(true);
        }
    }

    public void logout(ActionEvent event) throws IOException, AcountDAOException {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(yesButton, buttonTypeCancel);
    alert.setTitle("Logout Confirmation");
    alert.setHeaderText("You are going to logout");
    alert.setContentText("Are you sure that you want to logout?");
    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
    Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
    alertStage.getIcons().add(icon);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == yesButton) {
        // LogOut
        try {
            // Cargar la ventana del segundo proyecto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
            Parent root = loader.load();
            // Configurar la escena
            Scene scene = new Scene(root);

            // Configurar el escenario
            Stage stage = new Stage();
            stage.setMaximized(true); // El tamaño de la ventana se ajusta automáticamente a la pantalla del usuario
            stage.setResizable(false); // No se puede cambiar el tamaño de la pestaña
            stage.initStyle(StageStyle.TRANSPARENT); // Pantalla completa (hay que añadir el botón de cerrar a mano)
            
            stage.getIcons().add(icon);
            
            stage.setScene(scene);
            stage.show();

            // Ocultar la ventana actual después de medio segundo
            javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(Duration.seconds(0.1), evt -> {
                // Cerrar la ventana actual
                ((Node) event.getSource()).getScene().getWindow().hide();
                Acount currentAccount;
                try {
                    currentAccount = Acount.getInstance();
                    currentAccount.logOutUser();
                } catch (AcountDAOException | IOException ex) {
                    // Manejar la excepción si es necesario
                }
            }));
            timeline.play(); // Iniciar la animación de retraso
        } catch (IOException e) {}
    }
}

    
    //-------------------------------AÑADIR GASTO-------------------------------

    private void clearAdderFields() {
        aName.clear();
        aCost.clear();
        aUnits.clear();
        aDesc.clear();
        aCategory.setText("Category");
        aDate.setValue(null);
        Image image = new Image(getClass().getResourceAsStream("../icons/insert_pic.png"));
        aImageView.setImage(image);
        aImageText.setText("");
        aErrorPrice.setVisible(false);
        aExpAdded.setVisible(false);
    }
    
    public void handleImageClick1(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Muestra el cuadro de diálogo de selección de archivo
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            aImageView.setImage(image);
            aImageText.setText(image.getUrl().substring(image.getUrl().lastIndexOf("/") + 1));
        } else {
            System.out.println("handleImageClick1 ERROR");
        }
    }
        
    private void configureAdderCategorySelector() {
        // Limpiar las opciones actuales del selector
        aCategory.getItems().clear();

        // Crear una opción para cada categoría existente       
        try {
            Acount currentAccount = Acount.getInstance();
            List<Category> userCategories = currentAccount.getUserCategories();

            for (Category category : userCategories) {
                MenuItem item = new MenuItem(category.getName());
                item.setOnAction(event -> {
                    aCategory.setText(category.getName());
                });
                aCategory.getItems().add(item);
            }
        } catch (AcountDAOException | IOException ex) {}

        MenuItem newItem = new MenuItem("New Category");
        newItem.setOnAction(event -> showNewCategoryDialog1());
        aCategory.getItems().add(newItem);
    }

    private void showNewCategoryDialog1() {
        vBoxNewCategory1.setVisible(true); vBoxNewCategory1.setManaged(true);

        // Configurar el campo de texto de nombre de categoría
        nombreCat2.setTextFormatter(new TextFormatter<>((Change c) -> {
            if (c.isAdded()) {
                String text = c.getControlNewText();
                if (text.length() > 20) {
                    c.setText("");
                }
            }
            return c;
        }));
        
        descCat2.setText("");

        // Enfocar el campo de texto de nombre de categoría
        nombreCat2.requestFocus();
    }
    private void hideNewCategoryDialog1() {
        vBoxNewCategory1.setVisible(false);
        vBoxNewCategory1.setManaged(false);
        nombreCat2.clear();
    }
    
    private void handleAddCategoryAction1() throws AcountDAOException, IOException {
        String name = nombreCat2.getText().trim();
        String description = descCat2.getText().trim();
        
        Acount currentAccount = Acount.getInstance();
        List<Category> userCategories = currentAccount.getUserCategories(); 
        Category selectedCategory = null;
        
        // Buscar la categoría seleccionada por nombre
        for (Category category : userCategories) {
            if (category.getName().equals(name)) {
                selectedCategory = category;
                break;
            }
        }
        
        // Verificar si la categoría ya existe
        if (selectedCategory != null) {
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
            try {
                // Llamar al método registerCategory() para crear una nueva categoría
                boolean categoryAdded = currentAccount.registerCategory(name, description);

                if (categoryAdded) { System.out.println("handleAddCategoryAction1 ERROR"); }
                
                configureCategorySelector(); // Actualizar el selector de categorías
                configureAdderCategorySelector();
                hideNewCategoryDialog1(); // Ocultar el VBox de nueva categoría y limpiar el campo de texto
            } catch (AcountDAOException e) {}
        }
    }
    
    // Métodos para ajustar el ancho de la columna
    private void increaseFillingCol() {
        double actualWidth = fillingCol.getWidth();
        double newWidth = actualWidth + 600;

        // Asegúrate de que el nuevo ancho no sea negativo
        fillingCol.setPrefWidth(Math.max(newWidth, 0));
    }
    
    private void decreaseFillingCol() {
        double actualWidth = fillingCol.getWidth();
        double newWidth = actualWidth - 600;

        // Asegúrate de que el nuevo ancho no sea negativo
        fillingCol.setPrefWidth(Math.max(newWidth, 0));
    }

    public TableColumn<?,?> getFillingTableCol() {
        return fillingCol;
    }
    
    public void setupChargeTable(TableView<Charge> tableView, TableColumn<Charge, String> nameColumn, TableColumn<Charge, String> categoryColumn, TableColumn<Charge, LocalDate> dateColumn, ObservableList<Charge> charges) {
        // Asigna la lista observable a la tabla
        tableView.setItems(charges);

        // Asigna las propiedades de Charge a cada columna
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryColumn.setCellValueFactory(cellData -> {
            Charge charge = cellData.getValue();
            Category category = charge.getCategory();
            if (category != null) {
                return new SimpleStringProperty(category.getName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
    }

    public void adjustHeightToFitContent(TableView<?> tableView) {
        double contentHeight = tableView.getItems().size() * tableView.getFixedCellSize();
        double maxHeight = 970;
        if(contentHeight > maxHeight) tableView.setPrefHeight(maxHeight);
        else { double totalHeight = contentHeight + 30; tableView.setPrefHeight(totalHeight); }
        if(contentHeight == 0) noChargesHBox.setVisible(true);
        else noChargesHBox.setVisible(false);
    }
    
    //------------------------------GESTIÓN GASTOS------------------------------

    private void addCharge() {
        try {
            String name = aName.getText();
            String costText = aCost.getText();
            String unitsText = aUnits.getText();
            String description = aDesc.getText();
            String categoryText = aCategory.getText(); // Nombre de la categoría (NO LA CATEGORÍA)
            LocalDate date = aDate.getValue();
            Image image = aImageView.getImage();
            
            Image defaultImage = new Image(getClass().getResourceAsStream("../icons/insert_pic.png"));
            if(image.equals(defaultImage)) image = null;

            if (name.isEmpty() || costText.isEmpty() || unitsText.isEmpty() || categoryText.equals("Category") || date == null) {
                aErrorPrice.setText("Please fill in obligatory fields");
                aErrorPrice.setVisible(true);
                return;
            }

            double cost;
            try {
                cost = Double.parseDouble(costText);
            } catch (NumberFormatException e) {
                aErrorPrice.setText("Price not valid");
                aErrorPrice.setVisible(true);
                return;
            }
            
            int units;
            try {
                units = Integer.parseInt(unitsText);
            } catch (NumberFormatException e) {
                aErrorPrice.setText("Units not valid");
                aErrorPrice.setVisible(true);
                return;
            }

            Acount currentAccount = Acount.getInstance();
            List<Charge> userCharges = currentAccount.getUserCharges();
            Charge selectedCharge = null;
        
            // Buscar la categoría seleccionada por nombre
            for (Charge charge : userCharges) {
                if (charge.getName().equals(name)) {
                    selectedCharge = charge;
                    break;
                }
            }
            
            List<Category> userCategories = currentAccount.getUserCategories();
            Category selectedCategory = null;
            for (Category category : userCategories) {
                if (category.getName().equals(categoryText)) {
                    selectedCategory = category;
                    break;
                }
            }

            // Verificar si la categoría ya existe
            if (selectedCharge != null) {
                // Mostrar un mensaje de error si la categoría ya existe
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Charge Already Exists");
                alert.setContentText("A charge with the same name already exists.");
                alert.initModality(Modality.APPLICATION_MODAL);

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setAlwaysOnTop(true);
                stage.toFront();

                alert.showAndWait();
            } else {               
                // Agregar el nuevo cargo
                boolean chargeAdded = currentAccount.registerCharge(name, description, cost, units, image, date, selectedCategory);

                if (chargeAdded) {
                    List<Charge> updatedUserCharges = currentAccount.getUserCharges();
                    Charge newCharge = updatedUserCharges.stream().filter(charge -> charge.getName().equals(name)).findFirst().orElse(null);
                    // Agregar el nuevo cargo a la ObservableList
                    if (newCharge != null) {                        
                        isUpdating = true;
                        charges.add(newCharge);
                        isUpdating = false;
                    }
                } else {
                    // Hubo un problema al agregar la categoría
                    System.out.println("addCharge ERROR 1");
                }
            }
            setupChargeTable(tableView, nameCol, categoryCol, dateCol, charges);
            
            // Limpiar los campos y mostrar un mensaje de éxito
            clearAdderFields();
            aErrorPrice.setVisible(false);
            aExpAdded.setVisible(true);
        } catch (AcountDAOException | IOException ex) { System.out.println("addCharge ERROR 2"); }
    }
    
    public void deleteCharge() throws AcountDAOException, IOException {
        // Obtener los cargos seleccionados
        ObservableList<Charge> selectedCharges = tableView.getSelectionModel().getSelectedItems();

        // Verificar si hay cargos seleccionados
        if (selectedCharges.isEmpty()) {
            // Mostrar alerta de que no hay cargos seleccionados
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Delete Charge");
            alert.setHeaderText("You can't delete any charge");
            alert.setContentText("There is no charge selected for deletion.");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
            stage.getIcons().add(icon);
            alert.showAndWait();
            return;
        }

        // Mostrar alerta de confirmación
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("You are going to delete charges");
        alert.setContentText("Are you sure you want to delete the selected charges?");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
        stage.getIcons().add(icon);
        // Esperar a que el usuario confirme o cancele la acción
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Eliminar los cargos seleccionados
            Acount currentAccount = Acount.getInstance();
            for (Charge charge : selectedCharges) { currentAccount.removeCharge(charge); }
            charges.removeAll(selectedCharges);
            setupChargeTable(tableView, nameCol, categoryCol, dateCol, charges);
        }
    }

    public void deleteAllCharges() throws AcountDAOException, IOException {
        // Verificar si hay cargos seleccionados
        if (tableView.getItems().isEmpty()) {
            // Mostrar alerta de que no hay cargos seleccionados
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Delete All Charges");
            alert.setHeaderText("You can't delete ALL charges");
            alert.setContentText("There are no charges for deletion.");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
            stage.getIcons().add(icon);
            alert.showAndWait();
            return;
        }

        // Mostrar alerta de confirmación
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("You are going to delete all the charges");
        alert.setContentText("Are you sure you want to delete all the charges?");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
        stage.getIcons().add(icon);
        // Esperar a que el usuario confirme o cancele la acción
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Eliminar los cargos seleccionados
            Acount currentAccount = Acount.getInstance();
            List<Charge> userCharges = new ArrayList<>(tableView.getItems()); // Copia de la lista original
            for (Charge charge : userCharges) { 
                currentAccount.removeCharge(charge); 
                charges.remove(charge); 
            }
            setupChargeTable(tableView, nameCol, categoryCol, dateCol, charges);
        }
    }
    
    // BOTONES LATERALES
    public void deleteCharge2(Charge charge) throws AcountDAOException, IOException {
        // Mostrar alerta de confirmación
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("You are going to delete a charge");
        alert.setContentText("Are you sure you want to delete this charge?");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
        stage.getIcons().add(icon);

        // Esperar a que el usuario confirme o cancele la acción
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Eliminar el gasto de la cuenta actual y de la lista de cargos
            Acount currentAccount = Acount.getInstance();
            currentAccount.removeCharge(charge);
            charges.remove(charge);
            setupChargeTable(tableView, nameCol, categoryCol, dateCol, charges);
            adjustHeightToFitContent(tableView);
        }
    }





    //-------------------------------EDITAR GASTOS------------------------------
    
    private void showNewCategoryDialog11() {
        vBoxNewCategory11.setVisible(true); vBoxNewCategory11.setManaged(true);

        // Configurar el campo de texto de nombre de categoría
        nombreCat3.setTextFormatter(new TextFormatter<>((Change c) -> {
            if (c.isAdded()) {
                String text = c.getControlNewText();
                if (text.length() > 20) {
                    c.setText("");
                }
            }
            return c;
        }));
        
        descCat3.setText("");

        // Enfocar el campo de texto de nombre de categoría
        nombreCat3.requestFocus();
    }
    private void hideNewCategoryDialog11() {
        vBoxNewCategory11.setVisible(false);
        vBoxNewCategory11.setManaged(false);
        nombreCat3.clear();
    }
    
    private void configureEditorCategorySelector1() {
        // Limpiar las opciones actuales del selector
        eCategory.getItems().clear();

        // Crear una opción para cada categoría existente       
        try {
            Acount currentAccount = Acount.getInstance();
            List<Category> userCategories = currentAccount.getUserCategories();

            for (Category category : userCategories) {
                MenuItem item = new MenuItem(category.getName());
                item.setOnAction(event -> {
                    eCategory.setText(category.getName());
                });
                eCategory.getItems().add(item);
            }
        } catch (AcountDAOException | IOException ex) {}

        MenuItem newItem = new MenuItem("New Category");
        newItem.setOnAction(event -> showNewCategoryDialog11());
        eCategory.getItems().add(newItem);
    }
    
    private void handleAddCategoryAction11() throws AcountDAOException, IOException {
        String name = nombreCat3.getText().trim();
        String description = descCat3.getText().trim();
        
        Acount currentAccount = Acount.getInstance();
        List<Category> userCategories = currentAccount.getUserCategories(); 
        Category selectedCategory = null;
        
        // Buscar la categoría seleccionada por nombre
        for (Category category : userCategories) {
            if (category.getName().equals(name)) {
                selectedCategory = category;
                break;
            }
        }
        
        // Verificar si la categoría ya existe
        if (selectedCategory != null) {
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
            try {
                // Llamar al método registerCategory() para crear una nueva categoría
                boolean categoryAdded = currentAccount.registerCategory(name, description);

                if (categoryAdded) { System.out.println("handleAddCategoryAction11 ERROR"); }
                
                configureCategorySelector(); // Actualizar el selector de categorías
                configureAdderCategorySelector();
                configureEditorCategorySelector1();
                hideNewCategoryDialog11(); // Ocultar el VBox de nueva categoría y limpiar el campo de texto
            } catch (AcountDAOException e) {}
        }
    }
    
    public void handleImageClick11(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Muestra el cuadro de diálogo de selección de archivo
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            eImageView.setImage(image);
            eImageText.setText(image.getUrl().substring(image.getUrl().lastIndexOf("/") + 1));
        } else {
            System.out.println("handleImageClick11 ERROR");
        }
    }
    
    private void handleConfirmChangesCharges() {        
        try {
            String chargeName1 = gName.getText();
            String chargeName2 = eName.getText();
            
            Acount currentAccount = Acount.getInstance();
            List<Charge> userCharges = currentAccount.getUserCharges(); 
            Charge selectedCharge = null;

            // Buscar el gasto seleccionado por nombre
            for (Charge charge : userCharges) {
                if (charge.getName().equals(chargeName1)) {
                    selectedCharge = charge;
                    break;
                }
            }
            
            String chargeCostString = eCost.getText();
            Double chargeCost1 = selectedCharge.getCost();
            Double chargeCost2;
            try { chargeCost2 = Double.valueOf(chargeCostString); }
            catch (NumberFormatException e) {
                aErrorPrice.setText("Price not valid.");
                aErrorPrice.setVisible(true);
                return;
            }
            
            String chargeUnitsString = eUnits.getText();
            int chargeUnits1 = selectedCharge.getUnits();
            int chargeUnits2;
            try { chargeUnits2 = Integer.parseInt(chargeUnitsString); }
            catch (NumberFormatException e) {
                aErrorPrice.setText("Units not valid.");
                aErrorPrice.setVisible(true);
                return;
            }
            
            String chargeDesc1 = selectedCharge.getDescription();
            String chargeDesc2 = eDesc.getText();
            
            String chargeNewCatName = eCategory.getText();
            List<Category> userCategories = currentAccount.getUserCategories(); 
            Category oldCategory = null;
            // Buscar la categoría seleccionada por nombre
            for (Category category : userCategories) {
                if (category.getName().equals(chargeNewCatName)) {
                    oldCategory = category;
                    break;
                }
            }
            Category chargeCat1 = selectedCharge.getCategory();
            Category chargeCat2 = oldCategory;
            
            LocalDate chargeDate1 = selectedCharge.getDate();
            LocalDate chargeDate2 = eDate.getValue();
            
            Image chargeImage1 = selectedCharge.getImageScan();
            Image chargeImage2 = eImageView.getImage();
            
            if(chargeName2.equals("") || chargeCostString.equals("") || chargeUnitsString.equals("") || chargeDate2 == null) {
                eErrorPrice.setText("Complete all the obligatory fields");
                aErrorPrice.setVisible(true);
            } else {
                boolean updated = false;
                if(!chargeName1.equals(chargeName2)) { selectedCharge.setName(chargeName2); gName.setText(chargeName2); updated = true; }                 // Actualizar el nombre del gasto
                if(!chargeCost1.equals(chargeCost2)) { selectedCharge.setCost(chargeCost2); gCost.setText(chargeCostString); updated = true; }                 // Actualizar el coste del gasto
                if(chargeUnits1 != chargeUnits2) { selectedCharge.setUnits(chargeUnits2); gUnits.setText(chargeUnitsString); updated = true; }                  // Actualizar las unidades del gasto
                if(!chargeDesc1.equals(chargeDesc2)) { selectedCharge.setDescription(chargeDesc2); gDesc.setText(chargeDesc2); updated = true; }     // Actualizar la descripción del gasto
                if(!chargeCat1.equals(chargeCat2)) { selectedCharge.setCategory(chargeCat2); gCategory.setText(chargeNewCatName); updated = true; }        // Actualizar la categoría del gasto
                if(!chargeDate1.equals(chargeDate2)) { selectedCharge.setDate(chargeDate2); gDate.setValue(chargeDate2); updated = true; }                        // Actualizar la fecha del gasto
                if(!chargeImage1.equals(chargeImage2)) { selectedCharge.setImageScan(chargeImage2); gImage.setImage(chargeImage2); updated = true; }       // Actualizar la fecha del gasto

                if (updated) {
                    int index = userCharges.indexOf(selectedCharge);
                    charges.set(index, selectedCharge);
                    tableView.getSelectionModel().select(-1);
                    setupChargeTable(tableView, nameCol, categoryCol, dateCol, charges);
                }
                closeRightStackPane();
            }
        } catch (AcountDAOException | IOException ex) { System.out.println("handleConfirmChangesCharges ERROR"); }
    }
    
    private void deleteImage() {
        Image image = new Image(new File("src/icons/insert_pic.png").toURI().toString());
        eImageView.setImage(image);
    }
    
    //---------------------------------IMPRIMIR---------------------------------
    public void setCharges(ObservableList<Charge> gastos) { //Asignamos los objetos de la lista observable a la real. Es decir los vinculamos para que se actualicen simultánemente
        this.gastos = gastos;
        tablaGastos.setItems(gastos);
    }
    
    
    public static void generatePDFReport(TableView<Charge> tableView, String fileName) throws IOException {
        float startX = 50;
        float startY = 700;
        float cellWidth = 100;
        float cellHeight = 20;

        String[] headers = {"Nombre", "Categoría", "Fecha", "Precio", "Unidades"};
        PDFont headerFont = PDType1Font.HELVETICA_BOLD;
        PDFont dataFont = PDType1Font.HELVETICA;

        float headerFontSize = 12;
        float dataFontSize = 10;

        String downloadDir = System.getProperty("user.home") + "/Downloads/";
        String filePath = downloadDir + fileName;
        
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.TIMES_BOLD, 20);
                contentStream.beginText();
                contentStream.newLineAtOffset(200, 750);
                contentStream.showText("REPORTE DE GASTOS");
                contentStream.endText(); // Fin del texto del título

                float currentX = startX;
                float currentY = startY;

                // Dibujar encabezados
                for (String header : headers) {
                    contentStream.beginText(); // Comienza el texto del encabezado
                    contentStream.setFont(headerFont, headerFontSize);
                    contentStream.newLineAtOffset(currentX, currentY);
                    contentStream.showText(header);
                    contentStream.endText(); // Fin del texto del encabezado
                    currentX += cellWidth;
                }

                currentY -= cellHeight;

                // Dibujar datos
                for (Charge charge : tableView.getItems()) {
                    currentX = startX;

                    String[] fields = new String[]{
                        charge.getName(),
                        charge.getCategory().getName(),
                        charge.getDate().toString(),
                        String.valueOf(charge.getCost()),
                        String.valueOf(charge.getUnits())
                    };

                    for (String field : fields) {
                        contentStream.beginText(); // Comienza el texto de los datos
                        contentStream.setFont(dataFont, dataFontSize);
                        contentStream.newLineAtOffset(currentX, currentY);
                        contentStream.showText(field);
                        contentStream.endText(); // Fin del texto de los datos
                        currentX += cellWidth;
                    }
                    currentY -= cellHeight;
                }
            }

            document.save(filePath);

            // Abrir el archivo PDF después de guardarlo
            if (Desktop.isDesktopSupported()) {
                try {
                    File pdfFile = new File(filePath);
                    if (pdfFile.exists()) {
                        Desktop.getDesktop().open(pdfFile);
                    }
                } catch (IOException e) {}
            }
        }
    }
    
    private void mostrarAlertayGenerarPDF(TableView<Charge> tableView) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Download PDF");
        alert.setHeaderText("Do you want to download a report?");
        alert.setContentText("You will download a PFD with all the expenses in the list");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                generatePDFReport(tableView, "Reporte Gastos.pdf");
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success in Download");
                successAlert.setHeaderText("Your report has been saved to the Downloads folder");
                successAlert.setContentText("The document is opening on your browser");
                successAlert.showAndWait();
            } catch (IOException ex) {}
        }
    }
    
    //----------------------------NOT IMPLEMENTED YET---------------------------
    @FXML
    public void notImplementedMessage() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Not Implemented");
        alert.setHeaderText("This function is not finished yet");
        alert.setContentText("Not enought time to finish it :(");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResourceAsStream("../icons/logo.png"));
        stage.getIcons().add(icon);
        alert.showAndWait();
    }
}