package controller;

import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAOOlimpiada;
import excepciones.OlimpiadasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Olimpiada;

/**
 * Controlador para borrar una Olimpiada.
 */
public class BorrarOlimpiadaController implements Initializable {

    /**
     * Controlador principal.
     */
    private OlimpiadasController controladorPrincipal;
    
    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Olimpiada> cbOlimpiada;

    @FXML
    private Label lblOlimpiada;

    /**
     * Método para borrar una Olimpiada.
     * 
     * @param event el evento del botón borrar
     */
    @FXML
    void borrar(ActionEvent event) {
        Olimpiada seleccionado = cbOlimpiada.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                DAOOlimpiada.borrarOlimpiada(seleccionado);
                controladorPrincipal.refrescarOlimpiadas();
                cerrarVentanaDesdeEvento(event);
                mostrarInfo("La olimpiada se borró");
            } catch (SQLException | OlimpiadasException e) {
                lanzarError(e);
            }
        }
    }
    
    /**
     * Método para cancelar la operación de borrado.
     * 
     * @param event el evento del botón cancelar
     */
    @FXML
    void cancelar(ActionEvent event) {
        cerrarVentanaDesdeEvento(event);
    }
    
    /**
     * Establece el controlador principal.
     * 
     * @param controladorPrincipal el controlador principal
     * @return el controlador actualizado
     */
    public BorrarOlimpiadaController setControladorPrincipal(OlimpiadasController controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
        return this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cbOlimpiada.getItems().addAll(DAOOlimpiada.getOlimpiadas());
            cbOlimpiada.getSelectionModel().selectFirst();
        } catch (OlimpiadasException e) {
            lanzarError(e);
        }
    }

}
