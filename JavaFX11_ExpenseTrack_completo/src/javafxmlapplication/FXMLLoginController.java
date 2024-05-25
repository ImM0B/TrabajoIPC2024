package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;

public class FXMLLoginController implements Initializable {

    @FXML
    private TextField bUsername;
    @FXML
    private PasswordField bPassword;
    @FXML
    private Label iniPassword;
    @FXML
    private Label iniLoginIncorrect;
    @FXML
    private Button bAccept;
    @FXML
    private Button bCancel;

    private BooleanBinding fieldsValid;
    @FXML
    private VBox midVBox;
    @FXML
    private Label iniUsername;
    
    private TableColumn<?, ?> fillingCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Deshabilitar el botón de aceptar si algún campo está vacío
        fieldsValid = bUsername.textProperty().isNotEmpty()
                .and(bPassword.textProperty().isNotEmpty());

        bAccept.disableProperty().bind(fieldsValid.not());
        
        // Cargar la imagen
        Image image = new Image(getClass().getResourceAsStream("../icons/bg_singup.jpg"));
        
        // Establecer el fondo en el HBox
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        midVBox.setBackground(new Background(backgroundImage));

        // Botón Cancelar cierra la ventana
        bCancel.setOnAction((event) -> {
            bCancel.getScene().getWindow().hide();
        });
        
        bPassword.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
            // Ejecutar el evento del botón bAccept cuando se presiona la tecla Enter
            bAccept.fire();
        }
        });
        
        bUsername.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
            // Ejecutar el evento del botón bAccept cuando se presiona la tecla Enter
            bAccept.fire();  
        }
        });
        
        // Verificar campos al dar clic en Aceptar
        bAccept.setOnAction((event) -> {
            if(!checkFields()) { clearPasswordFields(); }
            else {
                if(loggedIn()){
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
                    
                    // Adaptar tamaño TableColumn
                    TableColumn<?, ?> fillingCol = controller.getFillingTableCol();
                    // Añadir listener para ajustar la columna cuando cambie el tamaño de la escena
                    scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                        adjustFillingColumnWidth(fillingCol, scene);
                    });
                    // Ajuste inicial de la columna
                    adjustFillingColumnWidth(fillingCol, scene);
                    
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
                    } catch (IOException e) { System.out.println(e); }
                }
            }
        });
    }
    
    // Método para ajustar el ancho de la columna
    private void adjustFillingColumnWidth(TableColumn<?, ?> fillingCol, Scene scene) {
        double sceneWidth = scene.getWidth();
        double newWidth = sceneWidth - 962;

        // Asegúrate de que el nuevo ancho no sea negativo
        fillingCol.setPrefWidth(Math.max(newWidth, 0));
    }

    private boolean checkFields() {
        boolean passwordValid = Utils.checkPassword(bPassword.getText());

        if (!passwordValid) {
            manageError(iniPassword, bPassword);
        } else {
            manageCorrect(iniPassword, bPassword);
        }
        
        return passwordValid;
    }

    private void manageError(Label errorLabel, TextField textField) {
        errorLabel.setVisible(true);
    }

    private void manageCorrect(Label errorLabel, TextField textField) {
        errorLabel.setVisible(false);
    }
    
    private void clearPasswordFields() {
        bPassword.clear();
    }
    
    private boolean loggedIn() {
        boolean login = false;
        try {
            login = Acount.getInstance().logInUserByCredentials(bUsername.getText(), bPassword.getText());
        } catch(AcountDAOException | IOException ex) { manageError(iniLoginIncorrect, bPassword); }
        finally {
            if(!login) {
                clearPasswordFields();
                manageError(iniLoginIncorrect, bPassword);
            }
        }    
        return login;
    }
    
    /**
     *
     * @param event
     */
    @FXML
    public void signupButtonAction(ActionEvent event) {
    try {
        // Cargar la ventana del segundo proyecto
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLRegister.fxml"));
        Parent root = loader.load();
        FXMLRegisterController controller = loader.getController();
        
        // Configurar la escena
        Scene scene = new Scene(root);
        
        // Configurar el escenario
        Stage stage = new Stage();
        stage.setMaximized(true); // El tamaño de la ventana se ajusta automáticamente a la pantalla del usuario
        stage.setResizable(false); // No se puede cambiar el tamaño de la pestaña
        stage.initStyle(StageStyle.TRANSPARENT); // Pantalla completa (hay q añadir el botón de cerrar a mano)
        
        // Acceder al VBox desde el controlador
        VBox midVBoxR = controller.getMidVBox();

        // Cargar la imagen
        Image image = new Image(getClass().getResourceAsStream("../icons/bg_singup.jpg"));

        // Establecer el fondo en el HBox
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        midVBoxR.setBackground(new Background(backgroundImage));
        
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
    
    @FXML
    public void forgotButtonAction(ActionEvent event) {
    try {
        // Cargar la ventana del segundo proyecto
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLForgotPass.fxml"));
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
    
    // Método para obtener una referencia al VBox desde otras clases
    public VBox getMidVBox() {
        return midVBox;
    }
}
