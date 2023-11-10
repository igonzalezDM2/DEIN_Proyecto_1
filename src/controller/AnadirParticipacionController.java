package controller;

import static utilities.Utilidades.num2str;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
	
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Deportista> cbDeportista;

    @FXML
    private ComboBox<Equipo> cbEquipo;

    @FXML
    private ComboBox<Evento> cbEvento;

    @FXML
    private ComboBox<Medalla> cbMedalla;

    @FXML
    private Label lblDeporte;

    @FXML
    private Label lblDeporte1;

    @FXML
    private Label lblOlimpiada;

    @FXML
    private Label lblOlimpiada1;

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField tfEdad;

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }

	@Override
	public void comprobarDatos() throws OlimpiadasException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Participacion construirObjeto() throws OlimpiadasException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rellenarEditor() {
		if (seleccionado != null) {
			cbDeportista.setDisable(true);
			cbEvento.setDisable(true);
			Optional<Deportista> optDeportista = cbDeportista.getItems().stream().filter(d -> d.getId() == seleccionado.getDeportista().getId()).findFirst();
			Optional<Evento> optEvento = cbEvento.getItems().stream().filter(e -> e.getId() == seleccionado.getEvento().getId()).findFirst();
			Optional<Equipo> optEquipo= cbEquipo.getItems().stream().filter(e -> e.getId() == seleccionado.getEquipo().getId()).findFirst();
			if (optDeportista.isPresent()) {
				cbDeportista.getSelectionModel().select(optDeportista.get());				
			}
			if (optEvento.isPresent()) {
				cbEvento.getSelectionModel().select(optEvento.get());				
			}
			if (optEquipo.isPresent()) {
				cbEquipo.getSelectionModel().select(optEquipo.get());				
			}
			tfEdad.setText(num2str(seleccionado.getEdad()));
			cbMedalla.getSelectionModel().select(Medalla.getByValor(seleccionado.getMedalla()));
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public AnadirParticipacionController setSeleccionado(Participacion seleccionado) {
		this.seleccionado = seleccionado;
		return this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbMedalla.getItems().addAll(Medalla.values());
		cbMedalla.getSelectionModel().selectFirst();
		
	}

}
