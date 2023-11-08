package controller;

import static dao.DAOOlimpiada.anadirOlimpiada;
import static dao.DAOOlimpiada.modificarOlimpiada;
import static utilities.StringUtils.trimToEmpty;
import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAOOlimpiada;
import enums.Temporada;
import excepciones.OlimpiadasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Olimpiada;
import utilities.StringUtils;
import utilities.Utilidades;

public class AnadirOlimpiadaController implements EditorDeObjeto<Olimpiada>, Initializable {
	private Olimpiada seleccionado;
	private OlimpiadasController controladorPrincipal;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Temporada> cbTemporada;

    @FXML
    private ComboBox<Olimpiada> cbOlimpiada;
    
    @FXML
    private Label lblOlimpiada;

    @FXML
    private TextField tfAnio;

    @FXML
    private TextField tfCiudad;

    @FXML
    private TextField tfNombre;
    
    @FXML
    void cambioOlimpiada() {
    	
    }

    @FXML
    void cancelar(ActionEvent event) {
    	cerrarVentanaDesdeEvento(event);
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
			if (seleccionado == null) {
				anadirOlimpiada(construirObjeto());
				mostrarInfo("La olimpiada se añadió");
			} else {
				modificarOlimpiada(construirObjeto().setId(seleccionado.getId()));
				mostrarInfo("La olimpiada se modificó");
			}
			controladorPrincipal.refrescarOlimpiadas();
			cerrarVentanaDesdeEvento(event);
		} catch (OlimpiadasException | SQLException e) {
			lanzarError(e);
		}
    }

	@Override
	public void comprobarDatos() throws OlimpiadasException {
    	Utilidades.checkCampoStrNotNull(tfNombre);
    	Utilidades.checkCampoInt(tfAnio);
    	Utilidades.checkCampoStrNotNull(tfCiudad);
	}

	@Override
	public Olimpiada construirObjeto() throws OlimpiadasException {
		return new Olimpiada()
				.setNombre(StringUtils.trimToEmpty(tfNombre.getText()))
				.setAnio(Utilidades.parseInt(tfAnio.getText()))
				.setTemporada(cbTemporada.getValue()) //NO PUEDE SER NUNCA NULO PORQUE SE SE SELECCIONA EL PRIMERO POR DEFECTO
				.setCiudad(StringUtils.trimToEmpty(tfCiudad.getText()));
	}

	@Override
	public void rellenarEditor() {
		if (seleccionado != null) {
			tfNombre.setText(trimToEmpty(seleccionado.getNombre()));
			tfAnio.setText(Utilidades.num2str(seleccionado.getAnio()));
			cbTemporada.getSelectionModel().select(seleccionado.getTemporada());
			tfCiudad.setText(trimToEmpty(seleccionado.getCiudad()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AnadirOlimpiadaController setSeleccionado(Olimpiada seleccionado) {
		this.seleccionado = seleccionado;
		rellenarEditor();
		return this;
	}
	
	public AnadirOlimpiadaController setControladorPrincipal(OlimpiadasController controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
		return this;
	}
	
	public AnadirOlimpiadaController setEditar(boolean editar) {
		if (editar) {
			try {
				cbOlimpiada.setVisible(true);
				lblOlimpiada.setVisible(true);
				cbOlimpiada.getItems().addAll(DAOOlimpiada.getOlimpiadas());
				cbOlimpiada.getSelectionModel().selectFirst();
				this.seleccionado = cbOlimpiada.getSelectionModel().getSelectedItem();
				cbOlimpiada.setOnAction(event -> {
					this.seleccionado = cbOlimpiada.getSelectionModel().getSelectedItem();					
					rellenarEditor();
				});
			} catch (OlimpiadasException e) {
				lanzarError(e);
			}
		}
		return this;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbOlimpiada.setVisible(false);
		lblOlimpiada.setVisible(false);
		cbTemporada.getItems().addAll(Temporada.values());
		cbTemporada.getSelectionModel().selectFirst();
	}

    
    

}
