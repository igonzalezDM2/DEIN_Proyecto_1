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

public class BorrarOlimpiadaController implements Initializable {

	private OlimpiadasController controladorPrincipal;
	
    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Olimpiada> cbOlimpiada;

    @FXML
    private Label lblOlimpiada;

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
    
    @FXML
    void cancelar(ActionEvent event) {
    	cerrarVentanaDesdeEvento(event);
    }
    
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
