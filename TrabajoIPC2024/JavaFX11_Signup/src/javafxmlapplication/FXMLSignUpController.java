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
        textField.setStyle("-fx-background-color: #FCE5E0");
    }

    private void manageCorrect(Label errorLabel, TextField textField) {
        errorLabel.setVisible(false);
        textField.setStyle("");
    }
    
    private void clearPasswordFields() {
        bPassword.clear();
        bPassword2.clear();
    }
}
