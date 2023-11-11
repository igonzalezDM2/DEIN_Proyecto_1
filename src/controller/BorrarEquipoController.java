package controller;

import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAOEquipo;
import excepciones.OlimpiadasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Equipo;

/**
 * Controlador para borrar un equipo.
 */
public class BorrarEquipoController implements Initializable {

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
     * ComboBox para seleccionar el equipo a borrar.
     */
    @FXML
    private ComboBox<Equipo> cbEquipo;

    /**
     * Etiqueta para mostrar información sobre el equipo.
     */
    @FXML
    private Label lblEquipo;

    /**
     * Método para borrar un equipo.
     *
     * @param event el evento de acción
     */
    @FXML
    void borrar(ActionEvent event) {
        try {
            Equipo seleccionado = cbEquipo.getSelectionModel().getSelectedItem();
            DAOEquipo.borrarEquipo(seleccionado);
            mostrarInfo("El equipo fue borrado");
            cerrarVentanaDesdeEvento(event);
        } catch (SQLException | OlimpiadasException e) {
            lanzarError(e);
        }
    }

    /**
     * Método para cancelar la acción de borrar un equipo.
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
     * @param location  la ubicación del FXML
     * @param resources los recursos utilizados para localizar el FXML
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cbEquipo.getItems().addAll(DAOEquipo.getEquipos());
            cbEquipo.getSelectionModel().selectFirst();
        } catch (OlimpiadasException e) {
            lanzarError(e);
        }
    }
}
