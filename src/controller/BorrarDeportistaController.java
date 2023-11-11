package controller;

import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAODeportista;
import excepciones.OlimpiadasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Deportista;

/**
 * Controlador para borrar un deportista.
 */
public class BorrarDeportistaController implements Initializable {

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
     * ComboBox para seleccionar el deportista a borrar.
     */
    @FXML
    private ComboBox<Deportista> cbDeportista;

    /**
     * Etiqueta para mostrar información sobre el deportista.
     */
    @FXML
    private Label lblDeportista;

    /**
     * Método para borrar un deportista.
     *
     * @param event el evento de acción
     */
    @FXML
    void borrar(ActionEvent event) {
        try {
            DAODeportista.borrarDeportista(cbDeportista.getSelectionModel().getSelectedItem());
            mostrarInfo("El deportista fue borrado");
            cerrarVentanaDesdeEvento(event);
        } catch (SQLException | OlimpiadasException e) {
            lanzarError(e);
        }
    }

    /**
     * Método para cancelar la acción de borrar un deportista.
     *
     * @param event el evento de acción
     */
    @FXML
    void cancelar(ActionEvent event) {
        cerrarVentanaDesdeEvento(event);
    }

    /**
     * Método para inicializar el controlador.
     *
     * @param location  la ubicación utilizada para resolver rutas relativas para el objeto raíz o null si no es conocida
     * @param resources los recursos utilizados para localizar el objeto raíz o null si no es conocido
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cbDeportista.getItems().addAll(DAODeportista.getDeportistas());
            cbDeportista.getSelectionModel().selectFirst();
        } catch (OlimpiadasException e) {
            lanzarError(e);
        }
    }
}
