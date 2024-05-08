/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author jsoler
 */
public class VistaTablaController implements Initializable {

    private ObservableList<Persona> datos = null; // Colecci�n vinculada a la vista.

    @FXML
    private Button addButton;
    @FXML
    private Button modificarButton;
    @FXML
    private Button borrarButton;
    @FXML
    private TableColumn<?, ?> nombreColumn;
    @FXML
    private TableColumn<?, ?> apellidosColumn;
    @FXML
    private TableView<?> personasTableV;

    private void inicializaModelo() {
        ArrayList<Persona> misdatos = new ArrayList<Persona>();
        misdatos.add(new Persona("Pepe", "García","/resources/images/Lloroso.png"));
        misdatos.add(new Persona("María", "Pérez","/resources/images/Sonriente.png"));
        datos = FXCollections.observableList(misdatos);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializaModelo();
        //Completar
       
    }

    @FXML
    private void anyadir(ActionEvent event) {
        //selección -1 del tableview
        personasTableV.getSelectionModel().clearSelection();
        
        //Completar
    }

    @FXML
    private void modificar(ActionEvent event) {
        //Completar
    }

    @FXML
    private void borrar(ActionEvent event) {
        //Completar
    }

class ImagenTabCell extends TableCell<Persona, String> {
        private ImageView view = new ImageView();
        private Image imagen;

        @Override
        protected void updateItem(String t, boolean bln) {
            super.updateItem(t, bln); 
            if (t == null || bln) {
                setText(null);
                setGraphic(null);
            } else {
                imagen = new Image(t,25,25,true,true);
                view.setImage(imagen);
                setGraphic(view);
            }
        }
    }

}
