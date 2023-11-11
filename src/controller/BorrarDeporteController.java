package controller;

import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAODeporte;
import excepciones.OlimpiadasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Deporte;

/**
 * Controlador para borrar un deporte.
 */
public class BorrarDeporteController implements Initializable {

    /**
     * Controlador principal de las Olimpiadas.
     */
    private OlimpiadasController controladorPrincipal;

    /**
     * Botón para borrar.
     */
    @FXML
    private Button btnBorrar;

    /**
     * Botón para cancelar.
     */
    @FXML
    private Button btnCancelar;

    /**
     * ComboBox para seleccionar el deporte a borrar.
     */
    @FXML
    private ComboBox<Deporte> cbDeporte;

    /**
     * Etiqueta para mostrar información sobre la olimpiada.
     */
    @FXML
    private Label lblOlimpiada;

    /**
     * Método para borrar un deporte.
     *
     * @param event el evento de acción
     */
    @FXML
    void borrar(ActionEvent event) {
        Deporte seleccionado = cbDeporte.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                DAODeporte.borrarDeporte(seleccionado);
                controladorPrincipal.refrescarDeportes();
                cerrarVentanaDesdeEvento(event);
                mostrarInfo("El deporte se borró");
            } catch (SQLException | OlimpiadasException e) {
                lanzarError(e);
            }
        }
    }

    /**
     * Método para cancelar la acción.
     *
     * @param event el evento de acción
     */
    @FXML
    void cancelar(ActionEvent event) {
        cerrarVentanaDesdeEvento(event);
    }

    /**
     * Método para establecer el controlador principal de las Olimpiadas.
     *
     * @param controladorPrincipal el controlador principal
     * @return el controlador de borrar deporte
     */
    public BorrarDeporteController setControladorPrincipal(OlimpiadasController controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
        return this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cbDeporte.getItems().addAll(DAODeporte.getDeportes());
            cbDeporte.getSelectionModel().selectFirst();
        } catch (OlimpiadasException e) {
            lanzarError(e);
        }
    }
}
