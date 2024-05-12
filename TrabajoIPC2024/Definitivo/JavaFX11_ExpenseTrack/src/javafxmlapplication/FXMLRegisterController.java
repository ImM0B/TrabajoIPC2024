package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;

public class FXMLRegisterController implements Initializable {

    @FXML
    private TextField bEmail;
    @FXML
    private Label iniEmail;
    @FXML
    private PasswordField bPassword;
    @FXML
    private Label iniPassword;
    @FXML
    private Label iniRegisterError;
    @FXML
    private TextField bUsername;
    @FXML
    private Label iniUsername;
    @FXML
    private TextField bName;
    @FXML
    private TextField bSurname;
    @FXML
    private Button bAcceptR;
    @FXML
    private Button bCancel;
    private BooleanBinding fieldsValid;
    @FXML
    private Button blogin;
    @FXML
    private VBox midVBoxR;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Deshabilitar el botón de aceptar si algún campo está vacío
        fieldsValid = bEmail.textProperty().isNotEmpty()
                .and(bPassword.textProperty().isNotEmpty())
                .and(bUsername.textProperty().isNotEmpty());

        bAcceptR.disableProperty().bind(fieldsValid.not());

        // Botón Cancelar cierra la ventana
        bCancel.setOnAction((event) -> {
            bCancel.getScene().getWindow().hide();
        });

        // Verificar campos al dar clic en Aceptar
        bAcceptR.setOnAction((event) -> {
            if(!checkFields()) { clearFields(); }
            else if(register()){
                try {
                // Cargar la ventana del segundo proyecto
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSignUp.fxml"));
                Parent root = loader.load();

                // Configurar la escena
                Scene scene = new Scene(root);

                // Configurar el escenario
                Stage stage = new Stage();
                stage.setMaximized(true); // El tamaño de la ventana se ajusta automáticamente a la pantalla del usuario
                stage.setResizable(false); // No se puede cambiar el tamaño de la pestaña
                stage.initStyle(StageStyle.TRANSPARENT); // Pantalla completa (hay q añadir el botón de cerrar a mano)

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

    private boolean checkFields() {
        boolean emailValid = Utils.checkEmail(bEmail.getText());
        boolean passwordValid = Utils.checkPassword(bPassword.getText());

        if (!emailValid) {
            manageError(iniEmail, bEmail);
        } else {
            manageCorrect(iniEmail, bEmail);
        }

        if (!passwordValid) {
            manageError(iniPassword, bPassword);
        } else {
            manageCorrect(iniPassword, bPassword);
        }
        
        return emailValid && passwordValid;
    }

    private void manageError(Label errorLabel, TextField textField) {
        errorLabel.setVisible(true);
    }

    private void manageCorrect(Label errorLabel, TextField textField) {
        errorLabel.setVisible(false);
    }
    
    private void clearFields() {
        bPassword.clear();
        bUsername.clear();
        bName.clear();
        bSurname.clear();
        bEmail.clear();
    }
    
    private boolean register(){
        boolean registered = false;
        try {
            LocalDate fechaRegistro = LocalDate.now();
            Image profileImage = new Image("../icons/profile.png");
            registered = Acount.getInstance().registerUser(bName.getText(), bSurname.getText(), bEmail.getText(),bUsername.getText(), bPassword.getText(),profileImage,fechaRegistro);
        } catch (AcountDAOException  | IOException ex) {}
        finally{
            if(!registered){
                manageError(iniRegisterError, bPassword);
                clearFields();
                return false;
            }
            else return true;
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void loginButtonAction(ActionEvent event) {
    try {
        // Cargar la ventana del segundo proyecto
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
        Parent root = loader.load();
        FXMLLoginController controller = loader.getController();
        
        // Configurar la escena
        Scene scene = new Scene(root);
        
        // Configurar el escenario
        Stage stage = new Stage();
        stage.setMaximized(true); // El tamaño de la ventana se ajusta automáticamente a la pantalla del usuario
        stage.setResizable(false); // No se puede cambiar el tamaño de la pestaña
        stage.initStyle(StageStyle.TRANSPARENT); // Pantalla completa (hay q añadir el botón de cerrar a mano)
        
        // Acceder al VBox desde el controlador
        VBox midVBox = controller.getMidVBox();

        // Cargar la imagen
        Image image = new Image(getClass().getResourceAsStream("../icons/bg_singup.jpg"));

        // Establecer el fondo en el HBox
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        midVBox.setBackground(new Background(backgroundImage));
        
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
    
    // Método para obtener una referencia al VBox desde otras clases
    public VBox getMidVBox() {
        return midVBoxR;
    }
}
