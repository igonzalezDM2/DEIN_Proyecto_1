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

public class BorrarEquipoController implements Initializable {

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Equipo> cbEquipo;

    @FXML
    private Label lblEquipo;

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

    @FXML
    void cancelar(ActionEvent event) {
    	cerrarVentanaDesdeEvento(event);
    }

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
