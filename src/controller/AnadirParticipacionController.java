package controller;

import static utilities.StringUtils.isBlank;
import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.checkCampoInt;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;
import static utilities.Utilidades.num2str;
import static utilities.Utilidades.parseInt;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.DAODeportista;
import dao.DAOEquipo;
import dao.DAOParticipacion;
import enums.Medalla;
import excepciones.OlimpiadasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.Participacion;

public class AnadirParticipacionController implements EditorDeObjeto<Participacion>, Initializable{

	private Participacion seleccionado;
	private Evento evento;
	private OlimpiadasController controladorPrincipal;
	
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Deportista> cbDeportista;

    @FXML
    private ComboBox<Equipo> cbEquipo;

    @FXML
    private ComboBox<Medalla> cbMedalla;

    @FXML
    private Label lblDeporte;

    @FXML
    private Label lblEquipo;

    @FXML
    private Label lblOlimpiada;

    @FXML
    private Label lblEdad;

    @FXML
    private Label lblMedalla;

    @FXML
    private Label lblTitulo;


    @FXML
    private TextField tfEdad;

    @FXML
    void cancelar(ActionEvent event) {
    	cerrarVentanaDesdeEvento(event);
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
			comprobarDatos();
			if (seleccionado != null) {
				DAOParticipacion.modificarParticipacion(construirObjeto());
				mostrarInfo("Se modificó la participación");
			} else {
				DAOParticipacion.anadirParticipacion(construirObjeto());
				mostrarInfo("Se añadió la participación");
			}
			cerrarVentanaDesdeEvento(event);
			controladorPrincipal.filtrarDatos();
		} catch (OlimpiadasException | SQLException e) {
			lanzarError(e);
		}
    }

	@Override
	public void comprobarDatos() throws OlimpiadasException {
		if (evento == null) {
			throw new OlimpiadasException("Ningún evento seleccionado");
		}
		if (!isBlank(tfEdad.getText())) {
			checkCampoInt(tfEdad);
		}
		//Los demás son "comboboxes" que por defecto tienen un valor seleccionado
	}

	@Override
	public Participacion construirObjeto() throws OlimpiadasException {
		return new Participacion()
				.setDeportista(cbDeportista.getSelectionModel().getSelectedItem())
				.setEvento(evento)
				.setEquipo(cbEquipo.getSelectionModel().getSelectedItem())
				.setEdad(parseInt(tfEdad.getText()))
				.setMedalla(cbMedalla.getSelectionModel().getSelectedItem());
	}

	@Override
	public void rellenarEditor() {
		if (seleccionado != null) {
			cbDeportista.setDisable(true);
			Optional<Deportista> optDeportista = cbDeportista.getItems().stream().filter(d -> d.getId() == seleccionado.getDeportista().getId()).findFirst();
			Optional<Equipo> optEquipo= cbEquipo.getItems().stream().filter(e -> e.getId() == seleccionado.getEquipo().getId()).findFirst();
			if (optDeportista.isPresent()) {
				cbDeportista.getSelectionModel().select(optDeportista.get());				
			}
			if (optEquipo.isPresent()) {
				cbEquipo.getSelectionModel().select(optEquipo.get());				
			}
			tfEdad.setText(num2str(seleccionado.getEdad()));
			cbMedalla.getSelectionModel().select(seleccionado.getMedalla());
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public AnadirParticipacionController setSeleccionado(Participacion seleccionado) {
		this.seleccionado = seleccionado;
		cargarDeportistas();
		rellenarEditor();
		return this;
	}
	
	public AnadirParticipacionController setEvento(Evento evento) {
		this.evento = evento;
		return this;
	}
	
	public AnadirParticipacionController setControladorPrincipal(OlimpiadasController controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
		return this;
	}
	
	private void cargarDeportistas() {
		try {
			if (seleccionado == null) {
				List<Deportista> deportistasEnElEvento = DAODeportista.getDeportistasPorEvento(evento);
				cbDeportista.getItems().addAll(
						DAODeportista.getDeportistas()
						.stream()
						.filter(dep -> !deportistasEnElEvento.contains(dep))
						.toList()
						);
				
			} else {
				cbDeportista.getItems().add(seleccionado.getDeportista());
			}
			
			cbDeportista.getSelectionModel().selectFirst();
			
		} catch (OlimpiadasException e) {
			lanzarError(e);
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cbEquipo.getItems().addAll(DAOEquipo.getEquipos());
			cbEquipo.getSelectionModel().selectFirst();
			cbMedalla.getItems().addAll(Medalla.values());
			cbMedalla.getSelectionModel().selectFirst();
		} catch (OlimpiadasException e) {
			lanzarError(e);
		}
		
		
		
	}

}
