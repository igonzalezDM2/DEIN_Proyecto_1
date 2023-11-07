package controller;

import static utilities.Utilidades.lanzarError;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.DAODeporte;
import dao.DAOEvento;
import dao.DAOOlimpiada;
import dao.DAOParticipacion;
import excepciones.OlimpiadasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Deporte;
import model.Evento;
import model.Olimpiada;
import model.Participacion;

public class OlimpiadasController implements Initializable {

    @FXML
    private Button btnVincularDeporte;

    @FXML
    private Button btnVincularEvento;

    @FXML
    private ComboBox<Deporte> cbDeportes;

    @FXML
    private ComboBox<Evento> cbEvento;

    @FXML
    private ComboBox<Olimpiada> cbOlimpiadas;

    @FXML
    private MenuItem miAnadirDeporte;

    @FXML
    private MenuItem miAnadirDeportista;

    @FXML
    private MenuItem miAnadirEquipo;

    @FXML
    private MenuItem miAnadirEvento;

    @FXML
    private MenuItem miAnadirOlimpiada;

    @FXML
    private MenuItem miBorrarDeporte;

    @FXML
    private MenuItem miBorrarDeportista;

    @FXML
    private MenuItem miBorrarEquipo;

    @FXML
    private MenuItem miBorrarEvento;

    @FXML
    private MenuItem miBorrarOlimpiada;

    @FXML
    private MenuItem miEditarDeporte;

    @FXML
    private MenuItem miEditarDeportista;

    @FXML
    private MenuItem miEditarEquipo;

    @FXML
    private MenuItem miEditarEvento;

    @FXML
    private MenuItem miEditarOlimpiada;

    @FXML
    private TableColumn<Participacion, Integer> tcAltura;

    @FXML
    private TableColumn<Participacion, Integer> tcEdad;

    @FXML
    private TableColumn<Participacion, String> tcMedalla;

    @FXML
    private TableColumn<Participacion, String> tcNombre;

    @FXML
    private TableColumn<Participacion, Integer> tcPeso;

    @FXML
    private TableColumn<Participacion, String> tcSexo;

    @FXML
    private TableView<Participacion> tvDeportistas;

    @FXML
    void anadirDeporte(ActionEvent event) {

    }

    @FXML
    void anadirDeportista(ActionEvent event) {

    }

    @FXML
    void anadirEquipo(ActionEvent event) {

    }

    @FXML
    void anadirEvento(ActionEvent event) {

    }

    @FXML
    void anadirOlimpiada(ActionEvent event) {

    }

    @FXML
    void borrarDeporte(ActionEvent event) {

    }

    @FXML
    void borrarDeportista(ActionEvent event) {

    }

    @FXML
    void borrarEquipo(ActionEvent event) {

    }

    @FXML
    void borrarEvento(ActionEvent event) {

    }

    @FXML
    void borrarOlimpiada(ActionEvent event) {

    }

    @FXML
    void editarDeporte(ActionEvent event) {

    }

    @FXML
    void editarDeportista(ActionEvent event) {

    }

    @FXML
    void editarEquipo(ActionEvent event) {

    }

    @FXML
    void editarEvento(ActionEvent event) {

    }

    @FXML
    void editarOlimpiada(ActionEvent event) {

    }

    @FXML
    void cambioDeporte(ActionEvent event) {
    	try {
    		Evento evento = cbEvento.getSelectionModel().getSelectedItem();
    		if (evento != null) {
    			List<Participacion> participaciones = DAOParticipacion.getParicipacionesByEvento(evento);
    			tvDeportistas.getItems().clear();
    			tvDeportistas.getItems().addAll(participaciones);
    			tvDeportistas.getSelectionModel().selectFirst();
    		}
    	} catch (OlimpiadasException e) {
    		lanzarError(e);
    	}
    }

    @FXML
    void cambioEvento(ActionEvent event) {
    	try {
    		Deporte deporte = cbDeportes.getSelectionModel().getSelectedItem();
    		if (deporte != null) {
    			List<Evento> eventos = DAOEvento.getEventosByDeporte(deporte);
    			cbEvento.getItems().clear();
    			cbEvento.getItems().addAll(eventos);
    			cbEvento.getSelectionModel().selectFirst();
    		}
    	} catch (OlimpiadasException e) {
    		lanzarError(e);
    	}
    }

    @FXML
    void cambioOlimpiada(ActionEvent event) {
    	try {
    		Olimpiada olimpiada = cbOlimpiadas.getSelectionModel().getSelectedItem();
    		if (olimpiada != null) {
    			List<Deporte> deportes = DAODeporte.getDeportesByOlimpiada(olimpiada);
    			cbDeportes.getItems().clear();
    			cbDeportes.getItems().addAll(deportes);
    			cbDeportes.getSelectionModel().selectFirst();
    		}
    	} catch (OlimpiadasException e) {
    		lanzarError(e);
    	}
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			List<Olimpiada> olimpiadas = DAOOlimpiada.getOlimpiadas();
			cbOlimpiadas.getItems().addAll(olimpiadas);
			cbOlimpiadas.getSelectionModel().selectFirst();
		} catch (OlimpiadasException e) {
			lanzarError(e);
		}
		
	}

}
