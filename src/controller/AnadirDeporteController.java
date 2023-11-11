package controller;

import static utilities.StringUtils.trimToEmpty;
import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.checkCampoStrNotNull;
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
import javafx.scene.control.TextField;
import model.Deporte;

public class AnadirDeporteController implements EditorDeObjeto<Deporte>, Initializable {

	private Deporte seleccionado;
	private OlimpiadasController controladorPrincipal;
	
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Deporte> cbDeporte;

    @FXML
    private Label lblDeporte;

    @FXML
    private TextField tfNombre;


    @FXML
    void cambioDeporte(ActionEvent event) {
    	this.seleccionado = cbDeporte.getSelectionModel().getSelectedItem();
    }

    @FXML
    void cancelar(ActionEvent event) {
    	cerrarVentanaDesdeEvento(event);
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
    		comprobarDatos();
	    	if (seleccionado == null) {
				DAODeporte.anadirDeporte(construirObjeto());
	    	} else {
	    		DAODeporte.modificarDeporte(construirObjeto().setId(seleccionado.getId()));
	    	}
	    	controladorPrincipal.refrescarDeportes();
	    	mostrarInfo("El deporte se añadió");
	    	cerrarVentanaDesdeEvento(event);
    	} catch (OlimpiadasException | SQLException e) {
    		lanzarError(e);
    	}
    }

	@Override
	public void comprobarDatos() throws OlimpiadasException {
		checkCampoStrNotNull(tfNombre);
	}

	@Override
	public Deporte construirObjeto() throws OlimpiadasException {
		return new Deporte().setNombre(trimToEmpty(tfNombre.getText()));
	}

	@Override
	public void rellenarEditor() {
		tfNombre.setText(trimToEmpty(seleccionado.getNombre()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public AnadirDeporteController setSeleccionado(Deporte seleccionado) {
		this.seleccionado = seleccionado;
		return this;
	}
	
	public AnadirDeporteController setControladorPrincipal(OlimpiadasController controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
		return this;
	}
	
	public AnadirDeporteController setEditar(boolean editar) {
		if (editar) {			
			try {
				cbDeporte.setVisible(true);
				lblDeporte.setVisible(true);
				cbDeporte.getItems().addAll(DAODeporte.getDeportes());
				cbDeporte.getSelectionModel().selectFirst();
			} catch (OlimpiadasException e) {
				lanzarError(e);
			}
		}
		return this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbDeporte.setVisible(false);
		lblDeporte.setVisible(false);
	}
}
