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

/**
 * Controlador para añadir un equipo.
 */
public class AnadirEquipoController implements EditorDeObjeto<Equipo>, Initializable {
	
	/**
	 * Equipo seleccionado.
	 */
	private Equipo seleccionado;

    /**
     * Botón para cancelar.
     */
    @FXML
    private Button btnCancelar;

    /**
     * Botón para guardar.
     */
    @FXML
    private Button btnGuardar;

    /**
     * ComboBox para seleccionar un equipo.
     */
    @FXML
    private ComboBox<Equipo> cbEquipo;

    /**
     * Label para mostrar el equipo seleccionado.
     */
    @FXML
    private Label lblEquipo;

    /**
     * TextField para ingresar el nombre del equipo.
     */
    @FXML
    private TextField tfNombre;

    /**
     * TextField para ingresar las iniciales del equipo.
     */
    @FXML
    private TextField tfIniciales;

    /**
     * Método para cambiar el equipo seleccionado.
     * 
     * @param event el evento de acción
     */
    @FXML
    void cambioEquipo(ActionEvent event) {
    	setSeleccionado(cbEquipo.getSelectionModel().getSelectedItem());
    	rellenarEditor();
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
     * Método para guardar la información del equipo.
     * 
     * @param event el evento de acción
     */
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
	
	/**
	 * Método para establecer si se está editando un equipo.
	 * 
	 * @param editar true si se está editando, false de lo contrario
	 * @return el controlador actualizado
	 */
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
