package controller;

import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.checkCampoStrNotNull;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAODeporte;
import dao.DAOEvento;
import dao.DAOOlimpiada;
import excepciones.OlimpiadasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Deporte;
import model.Evento;
import model.Olimpiada;
import utilities.StringUtils;

public class AnadirEventoController implements EditorDeObjeto<Evento>, Initializable {
	
	private Evento seleccionado;
	private OlimpiadasController controladorPrincipal;
	private Olimpiada olimpiadaPredeterminada;
	private Deporte deportePredeterminado;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Deporte> cbDeporte;

    @FXML
    private ComboBox<Olimpiada> cbOlimpiada;

    @FXML
    private TextField tfNombre;
    
    @FXML
    private Label lblOlimpiada;
    
    @FXML
    private Label lblDeporte;
    
    @FXML
    private Label lblTitulo;

    @FXML
    void cancelar(ActionEvent event) {
    	cerrarVentanaDesdeEvento(event);
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
	    	if (seleccionado == null) {
				DAOEvento.anadirEvento(construirObjeto());
				mostrarInfo("Evento añadido");
	    	} else {
	    		DAOEvento.modificarEvento(construirObjeto().setId(seleccionado.getId()));
	    		mostrarInfo("Evento modificado");	    		
	    	}
	    	cerrarVentanaDesdeEvento(event);
    	} catch (OlimpiadasException | SQLException e) {
    		lanzarError(e);
    	}
    		
    }
    
    public AnadirEventoController setOlimpiadaPredeterminada(Olimpiada olimpiadaPredeterminada) {
		if (olimpiadaPredeterminada != null) {
			this.olimpiadaPredeterminada = olimpiadaPredeterminada;
			cbOlimpiada.getSelectionModel().select(cbOlimpiada.getItems().stream().filter(ol -> ol.getId() == olimpiadaPredeterminada.getId()).findFirst().orElse(null));
		}
    	return this;
    }
    
    public AnadirEventoController setDeportePredeterminado(Deporte deportePredeterminado) {
		if (deportePredeterminado != null) {
			this.deportePredeterminado = deportePredeterminado;
			cbDeporte.getSelectionModel().select(cbDeporte.getItems().stream().filter(de -> de.getId() == deportePredeterminado.getId()).findFirst().orElse(null));
		}
    	return this;
    }
    
    public AnadirEventoController setControladorPrincipal(OlimpiadasController controladorPrincipal) {
    	this.controladorPrincipal = controladorPrincipal;
    	return this;
    }

	@Override
	public void comprobarDatos() throws OlimpiadasException {
		checkCampoStrNotNull(tfNombre);
	}

	@Override
	public Evento construirObjeto() throws OlimpiadasException {
		return new Evento().setDeporte(cbDeporte.getSelectionModel().getSelectedItem())
				.setOlimpiada(cbOlimpiada.getSelectionModel().getSelectedItem())
				.setDeporte(cbDeporte.getSelectionModel().getSelectedItem())
				.setNombre(StringUtils.trimToEmpty(tfNombre.getText()));
	}

	@Override
	public void rellenarEditor() {
		if (seleccionado != null) {
			setOlimpiadaPredeterminada(seleccionado.getOlimpiada());
			setDeportePredeterminado(seleccionado.getDeporte());
			tfNombre.setText(StringUtils.trimToEmpty(seleccionado.getNombre()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AnadirEventoController setSeleccionado(Evento seleccionado) {
		if (seleccionado != null) {			
			this.seleccionado = seleccionado;
			cbOlimpiada.setDisable(seleccionado != null);
			cbDeporte.setDisable(seleccionado != null);
			olimpiadaPredeterminada = seleccionado.getOlimpiada();
			deportePredeterminado = seleccionado.getDeporte();
			
			lblTitulo.setText("EDITAR EVENTO");
			tfNombre.setText(seleccionado.getNombre());
			
		}
		return this;
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cbOlimpiada.getItems().addAll(DAOOlimpiada.getOlimpiadas());
			cbOlimpiada.getSelectionModel().selectFirst();
			cbDeporte.getItems().addAll(DAODeporte.getDeportes());
			cbDeporte.getSelectionModel().selectFirst();
			
		} catch (OlimpiadasException e) {
			lanzarError(e);
		}
		
	}

}
