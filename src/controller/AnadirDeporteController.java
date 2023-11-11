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

/**
 * Controlador para añadir un deporte.
 */
public class AnadirDeporteController implements EditorDeObjeto<Deporte>, Initializable {

    /**
     * Deporte seleccionado.
     */
    private Deporte seleccionado;
    
    /**
     * Controlador principal de las Olimpiadas.
     */
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
    
    /**
     * Método para cambiar el deporte seleccionado.
     * 
     * @param event el evento de cambio de deporte
     */
    @FXML
    void cambioDeporte(ActionEvent event) {
        this.seleccionado = cbDeporte.getSelectionModel().getSelectedItem();
    }
    
    /**
     * Método para cancelar la acción.
     * 
     * @param event el evento de cancelar
     */
    @FXML
    void cancelar(ActionEvent event) {
        cerrarVentanaDesdeEvento(event);
    }
    
    /**
     * Método para guardar el deporte.
     * 
     * @param event el evento de guardar
     */
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
    
    /**
     * Método para establecer el controlador principal de las Olimpiadas.
     * 
     * @param controladorPrincipal el controlador principal
     * @return el controlador de añadir deporte
     */
    public AnadirDeporteController setControladorPrincipal(OlimpiadasController controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
        return this;
    }
    
    /**
     * Método para establecer si se está editando un deporte.
     * 
     * @param editar true si se está editando, false si no
     * @return el controlador de añadir deporte
     */
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
