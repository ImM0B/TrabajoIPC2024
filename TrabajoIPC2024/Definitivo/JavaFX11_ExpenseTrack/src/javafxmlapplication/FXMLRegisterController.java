package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
            if(!checkFields()) {
                clearPasswordFields();
            }
        });
    }

    private boolean checkFields() {
        boolean emailValid = Utils.checkEmail(bEmail.getText());
        boolean passwordValid = Utils.checkPassword(bPassword.getText());
        //boolean usernameValid = bUsername.getText(); PARA COMPROBAR CON LA BASE DE DATOS

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
        
        //if (!usernameValid) {
        //    manageError(iniUsername, bUsername);
        //} else {
        //    manageCorrect(iniUsername, bUsername);
        //}

        return emailValid && passwordValid; //&& usernameValid;
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

    /**
     *
     * @param event
     */
    @FXML
    public void loginButtonAction(ActionEvent event) {
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
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
