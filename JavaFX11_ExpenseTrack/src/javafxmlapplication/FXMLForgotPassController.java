/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import javax.mail.Session;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLForgotPassController implements Initializable {

    private BooleanBinding fieldsValid;    
    @FXML
    private TextField bDir;
    @FXML
    private Label iniDir;
    @FXML
    private Label messageSent;
    @FXML
    private Label messageSent2;
    @FXML
    private Button bSend;
    @FXML
    private Button bReSend;
    @FXML
    private HBox bottomHBox;
    @FXML
    private Button bSend1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldsValid = bDir.textProperty().isNotEmpty();
        
        bSend.disableProperty().bind(fieldsValid.not());
        
        bSend.setOnAction((event) -> {
            if(!checkField()) { clearField(); }
            else {
                messageSent2.setVisible(false);
                messageSent.setVisible(true);
                bottomHBox.setVisible(true);
                bSend1.setVisible(true);
                bSend.setVisible(false);
                
                // Generar una nueva contraseña
                //String newPassword = generateNewPassword();

                // Enviar correo de recuperación de contraseña
                //sendPasswordRecoveryEmail(bDir.getText(), newPassword);
            }
        });
        
        bReSend.setOnAction((event) -> {
            if(!checkField()) { clearField(); }
            else {
                messageSent.setVisible(false);
                messageSent2.setVisible(true);
                
                // Generar una nueva contraseña
                //String newPassword = generateNewPassword();

                // Enviar correo de recuperación de contraseña
                //sendPasswordRecoveryEmail(bDir.getText(), newPassword);
            }
        });
    
        bDir.textProperty().addListener((observable, oldValue, newValue) -> {
            bSend.setVisible(true);
            bSend1.setVisible(false);
        });
    
    
    }
    
    private boolean checkField() {
        boolean emailValid = Utils.checkEmail(bDir.getText());
        if (!emailValid) {
            manageError(iniDir, bDir);
        } else {
            manageCorrect(iniDir, bDir);
        }
        return emailValid;
    }
    
    private void manageError(Label errorLabel, TextField textField) {
        errorLabel.setVisible(true);
    }
    
    private void manageCorrect(Label errorLabel, TextField textField) {
        errorLabel.setVisible(false);
    }
        
    private void clearField() {
        bDir.clear();
        messageSent.setVisible(false);
        messageSent2.setVisible(false);
    }

        /**
         *
         * @param recipientEmail
         * @param newPassword
         */
    //public void sendPasswordRecoveryEmail(String recipientEmail, String newPassword) {
        // Configura las propiedades del sistema para SMTP
        //Properties properties = new Properties();
        //properties.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto al host SMTP de tu proveedor de correo electrónico
        //properties.put("mail.smtp.port", "587"); // Puerto SMTP (generalmente 587 para TLS)
        //properties.put("mail.smtp.auth", "true"); // Autenticación requerida
        //properties.put("mail.smtp.starttls.enable", "true"); // Habilitar TLS

        // Configura la autenticación del servidor de correo
        //Authenticator auth = new Authenticator() {
        //    @Override
        //    protected PasswordAuthentication getPasswordAuthentication() {
        //        return new PasswordAuthentication("expensetrack0@gmail.com", "vjgn ysmb rapg prul");
        //    }
        //};

        // Crea una nueva sesión de correo
        //Session session = Session.getInstance(properties, auth);

        //try {
            // Crea un mensaje de correo electrónico
        //    MimeMessage message = new MimeMessage(session);
        //    message.setFrom(new InternetAddress("expensetrack0@gmail.com"));
        //    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        //    message.setSubject("Recuperación de contraseña");
        //    message.setText("Tu nueva contraseña es: " + newPassword);

            // Envía el mensaje de correo electrónico
        //    Transport.send(message);
        //    System.out.println("Correo electrónico de recuperación de contraseña enviado exitosamente.");
        //} catch (MessagingException ex) {
        //    System.out.println("Error al enviar el correo electrónico. " + ex);
        //}
    //}

    //private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    //private static final int PASSWORD_LENGTH = 10;

    //public static String generateNewPassword() {
    //    SecureRandom random = new SecureRandom();
    //    StringBuilder newPassword = new StringBuilder(PASSWORD_LENGTH);
        
    //    for (int i = 0; i < PASSWORD_LENGTH; i++) {
    //        int randomIndex = random.nextInt(CHARACTERS.length());
    //        newPassword.append(CHARACTERS.charAt(randomIndex));
    //    }
        
    //    return newPassword.toString();
    //}
}
