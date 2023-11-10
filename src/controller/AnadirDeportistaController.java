package controller;

import static utilities.StringUtils.trimToEmpty;
import static utilities.Utilidades.byte2Image;
import static utilities.Utilidades.cerrarVentanaDesdeEvento;
import static utilities.Utilidades.checkCampoInt;
import static utilities.Utilidades.checkCampoStrNotNull;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;
import static utilities.Utilidades.num2str;
import static utilities.Utilidades.parseInt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAODeportista;
import enums.Sexo;
import excepciones.OlimpiadasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Deportista;
import utilities.StringUtils;

public class AnadirDeportistaController implements EditorDeObjeto<Deportista>, Initializable {
	
	private byte[] imagenCargada;
	private Deportista seleccionado;
	

    @FXML
    private Button btnAnadirImagen;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Deportista> cbDeportista;

    @FXML
    private ComboBox<Sexo> cbSexo;

    @FXML
    private ImageView ivFoto;

    @FXML
    private Label lblDeportista;

    @FXML
    private TextField tfAltura;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfPeso;

    @FXML
    void anadirImagen(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	fc.setSelectedExtensionFilter(new ExtensionFilter("Imágenes (JPG, PNG)", "*.jpg", "*.png"));
    	File fichero = fc.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
    	if (fichero != null) {
    		try (FileInputStream fis = new FileInputStream(fichero)) {
    			this.imagenCargada = fis.readAllBytes();
    			mostrarImagen();
    		} catch (IOException e) {
    			lanzarError(e);
    		}
    	}
    }
    
    @FXML
    void cambioDeportista() {
    	setSeleccionado(cbDeportista.getSelectionModel().getSelectedItem());
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
	    		DAODeportista.modificarDeportista(construirObjeto().setId(seleccionado.getId()));
	    		mostrarInfo("El deportista se editó");
	    	} else {
	    		DAODeportista.anadirDeportista(construirObjeto());
	    		mostrarInfo("El deportista se añadió");
	    	}
	    	cerrarVentanaDesdeEvento(event);
		} catch (OlimpiadasException | SQLException e) {
			lanzarError(e);
		}
    }
    
    public void setEditar(boolean editar) {
    	if (editar) {
    		try {
    			lblDeportista.setVisible(true);
    			cbDeportista.setVisible(true);
				cbDeportista.getItems().addAll(DAODeportista.getDeportistas());
				cbDeportista.getSelectionModel().selectFirst();
				cambioDeportista();
			} catch (OlimpiadasException e) {
				lanzarError(e);
			}
    	}
    }
    
    private void mostrarImagen() {
    	try {
			ivFoto.setImage(byte2Image(imagenCargada));
		} catch (OlimpiadasException e) {
			lanzarError(e);
		}
    }

	@Override
	public void comprobarDatos() throws OlimpiadasException {
		checkCampoStrNotNull(tfAltura);
		if (!StringUtils.isBlank(tfPeso.getText())) {
			checkCampoInt(tfPeso);
		}
		if (!StringUtils.isBlank(tfAltura.getText())) {
			checkCampoInt(tfAltura);
		}
	}

	@Override
	public Deportista construirObjeto() throws OlimpiadasException {
		return new Deportista()
				.setNombre(trimToEmpty(tfNombre.getText()))
				.setSexo(cbSexo.getValue())
				.setPeso(parseInt(tfPeso.getText()))
				.setAltura(parseInt(tfAltura.getText()))
				.setFoto(imagenCargada);
	}

	@Override
	public void rellenarEditor() {
		if (seleccionado != null) {
			tfNombre.setText(trimToEmpty(seleccionado.getNombre()));
			cbSexo.getSelectionModel().select(seleccionado.getSexo());
			tfPeso.setText(num2str(seleccionado.getPeso()));
			tfAltura.setText(num2str(seleccionado.getAltura()));
			imagenCargada = seleccionado.getFoto();
			mostrarImagen();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AnadirDeportistaController setSeleccionado(Deportista seleccionado) {
		this.seleccionado = seleccionado;
		return this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblDeportista.setVisible(false);
		cbDeportista.setVisible(false);
		cbSexo.getItems().addAll(Sexo.values());
		cbSexo.getSelectionModel().selectFirst();
	}

}