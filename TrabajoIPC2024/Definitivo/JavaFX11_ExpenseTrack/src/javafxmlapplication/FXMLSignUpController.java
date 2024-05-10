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
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FXMLSignUpController implements Initializable {

    @FXML
    private TextField bEmail;
    @FXML
    private Label iniEmail;
    @FXML
    private PasswordField bPassword;
    @FXML
    private Label iniPassword;
    @FXML
    private PasswordField bPassword2;
    @FXML
    private Label iniPassword2;
    @FXML
    private Button bAccept;
    @FXML
    private Button bCancel;

    private BooleanBinding fieldsValid;
    @FXML
    private Button bforgot;
    @FXML
    private Button bsignup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Deshabilitar el botón de aceptar si algún campo está vacío
        fieldsValid = bEmail.textProperty().isNotEmpty()
                .and(bPassword.textProperty().isNotEmpty())
                .and(bPassword2.textProperty().isNotEmpty());

        bAccept.disableProperty().bind(fieldsValid.not());

        // Botón Cancelar cierra la ventana
        bCancel.setOnAction((event) -> {
            bCancel.getScene().getWindow().hide();
        });

        // Verificar campos al dar clic en Aceptar
        bAccept.setOnAction((event) -> {
            if(!checkFields()) {
                clearPasswordFields();
            }
        });
    }

    private boolean checkFields() {
        boolean emailValid = Utils.checkEmail(bEmail.getText());
        boolean passwordValid = Utils.checkPassword(bPassword.getText());
        boolean passwordsMatch = bPassword.getText().equals(bPassword2.getText());

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

        if (!passwordsMatch) {
            manageError(iniPassword2, bPassword2);
        } else {
            manageCorrect(iniPassword2, bPassword2);
        }

        return emailValid && passwordValid && passwordsMatch;
    }

    private void manageError(Label errorLabel, TextField textField) {
        errorLabel.setVisible(true);
    }

    private void manageCorrect(Label errorLabel, TextField textField) {
        errorLabel.setVisible(false);
    }
    
    private void clearPasswordFields() {
        bPassword.clear();
        bPassword2.clear();
    }
    
    /**
     *
     * @param event
     */
    public void signupButtonAction(ActionEvent event) {
    try {
        // Cargar la ventana del segundo proyecto
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLRegister.fxml"));
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
