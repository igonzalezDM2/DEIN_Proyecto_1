package controller;

import static utilities.StringUtils.trimToEmpty;
import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.checkCampoStrNotNull;
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
import javafx.scene.control.TextField;
import model.Equipo;
import utilities.StringUtils;

public class AnadirEquipoController implements EditorDeObjeto<Equipo>, Initializable {
	
	private Equipo seleccionado;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Equipo> cbEquipo;

    @FXML
    private Label lblEquipo;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfIniciales;

    @FXML
    void cambioEquipo(ActionEvent event) {
    	setSeleccionado(cbEquipo.getSelectionModel().getSelectedItem());
    	rellenarEditor();
    }

    @FXML
    void cancelar(ActionEvent event) {
    	cerrarVentanaDesdeEvento(event);
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
    		if (seleccionado != null) {
    			DAOEquipo.modificarEquipo(construirObjeto().setId(seleccionado.getId()));
    			mostrarInfo("El equipo fue editado");
    		} else {
    			DAOEquipo.anadirEquipo(construirObjeto());
    			mostrarInfo("El equipo fue añadido");
    		}
    		cerrarVentanaDesdeEvento(event);
    	} catch (OlimpiadasException | SQLException e) {
    		lanzarError(e);
    	}
    }

	@Override
	public void comprobarDatos() throws OlimpiadasException {
		checkCampoStrNotNull(tfNombre);
		checkCampoStrNotNull(tfIniciales);
	}

	@Override
	public Equipo construirObjeto() throws OlimpiadasException {
		return new Equipo()
				.setIniciales(StringUtils.trimToNull(tfIniciales.getText()))
				.setNombre(StringUtils.trimToNull(tfNombre.getText()));
	}

	@Override
	public void rellenarEditor() {
		if (seleccionado != null) {
			tfNombre.setText(trimToEmpty(seleccionado.getNombre()));
			tfIniciales.setText(trimToEmpty(seleccionado.getIniciales()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AnadirEquipoController setSeleccionado(Equipo seleccionado) {
		this.seleccionado = seleccionado;
		return this;
	}
	
	public AnadirEquipoController setEditar(boolean editar) {
		if (editar) {
			try {
				cbEquipo.setVisible(true);
				lblEquipo.setVisible(true);
				cbEquipo.getItems().addAll(DAOEquipo.getEquipos());
				cbEquipo.getSelectionModel().selectFirst();
				setSeleccionado(cbEquipo.getSelectionModel().getSelectedItem());
				rellenarEditor();
			} catch (OlimpiadasException e) {
				lanzarError(e);
			}
		}
		return this;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbEquipo.setVisible(false);
		lblEquipo.setVisible(false);
	}

}
