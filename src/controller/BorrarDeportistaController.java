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

public class BorrarDeportistaController implements Initializable {

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Deportista> cbDeportista;

    @FXML
    private Label lblDeportista;

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

    @FXML
    void cancelar(ActionEvent event) {
    	cerrarVentanaDesdeEvento(event);
    }

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
