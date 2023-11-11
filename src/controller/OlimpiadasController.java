package controller;

import static utilities.Utilidades.confirmarSiNo;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import dao.DAODeporte;
import dao.DAOEvento;
import dao.DAOOlimpiada;
import dao.DAOParticipacion;
import excepciones.OlimpiadasException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Deporte;
import model.Evento;
import model.Olimpiada;
import model.Participacion;
import utilities.StringUtils;
/**
 * Clase controladora para la gestión de las Olimpiadas.
 */

public class OlimpiadasController implements Initializable {

    @FXML
    private Button btnAnadirEvento;

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
    private MenuItem miAnadirOlimpiada;

    @FXML
    private MenuItem miBorrarDeporte;

    @FXML
    private MenuItem miBorrarDeportista;

    @FXML
    private MenuItem miBorrarEquipo;
    
    @FXML
    private MenuItem miBorrarOlimpiada;

    @FXML
    private MenuItem miEditarDeporte;

    @FXML
    private MenuItem miEditarDeportista;

    @FXML
    private MenuItem miEditarEquipo;

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
    
    /**
     * Refresca las olimpiadas.
     */
    public void refrescarOlimpiadas() {
        List<Olimpiada> olimpiadas;
        try {
            olimpiadas = DAOOlimpiada.getOlimpiadas();
            cbOlimpiadas.getItems().clear();
            cbOlimpiadas.getItems().addAll(olimpiadas);
            cbOlimpiadas.getSelectionModel().selectFirst();
            refrescarDeportes();
            refrescarEventos();
        } catch (OlimpiadasException e) {
            lanzarError(e);
        }
    }
    
    /**
     * Refresca los deportes.
     */
    public void refrescarDeportes() {
        Olimpiada olimpiada = cbOlimpiadas.getSelectionModel().getSelectedItem();
        try {
            cbDeportes.getItems().clear();
            if (olimpiada != null) {                
                List<Deporte> deportes = DAODeporte.getDeportesByOlimpiada(olimpiada);
                cbDeportes.getItems().addAll(deportes);
                cbDeportes.getSelectionModel().selectFirst();
            }
        } catch (OlimpiadasException e) {
            lanzarError(e);
        }
    }
    
    /**
     * Refresca los eventos.
     */
    public void refrescarEventos() {
        Olimpiada olimpiada = cbOlimpiadas.getSelectionModel().getSelectedItem();
        Deporte deporte = cbDeportes.getSelectionModel().getSelectedItem();
        try {
            cbEvento.getItems().clear();
            if (deporte != null) {                
                List<Evento> eventos= DAOEvento.getEventosByDeporteYOlimpiada(deporte, olimpiada);
                cbEvento.getItems().addAll(eventos);
                cbEvento.getSelectionModel().selectFirst();
                filtrarDatos();
            }
        } catch (OlimpiadasException e) {
            lanzarError(e);
        }
    }
    
    /**
     * Filtra los datos.
     */
    public void filtrarDatos() {
        try {
            tvDeportistas.getItems().clear();
            Evento evento = cbEvento.getSelectionModel().getSelectedItem();
            if (evento != null) {                
                tvDeportistas.getItems().addAll(DAOParticipacion.getParicipacionesByEvento(evento));
                tvDeportistas.refresh();
            }
        } catch (OlimpiadasException e) {
            lanzarError(e);
        }
    }

    @FXML
    void anadirDeporte(ActionEvent event) {
    	abrirEditorDeporte(false);
    }

    @FXML
    void anadirDeportista(ActionEvent event) {
    	abrirEditorDeportista(false);
    }

    @FXML
    void anadirEquipo(ActionEvent event) {
    	abrirEditorEquipo(false);
    }

    @FXML
    void anadirEvento(ActionEvent event) {
    	abrirEditorEvento(null);
    }

    @FXML
    void anadirOlimpiada(ActionEvent event) {
    	abrirEditorOlimpiada(false);
    }

    @FXML
    void borrarDeporte(ActionEvent event) {
    	abrirEliminadorDeporte();
    }

    @FXML
    void borrarDeportista(ActionEvent event) {
    	abrirEliminadorDeportista();
    }

    @FXML
    void borrarEquipo(ActionEvent event) {
    	abrirEliminadorEquipo();
    }

    @FXML
    void borrarEvento(ActionEvent event) {
    	Evento evento = cbEvento.getSelectionModel().getSelectedItem();
    	if (evento != null) {    		
    		confirmarSiNo("¿Desea eliminar el siguiente evento? " + evento.getNombre(),
				() -> {
					try {
						DAOEvento.borrarEvento(evento);
						mostrarInfo("El evento fue borrado");
						refrescarEventos();
					} catch (SQLException | OlimpiadasException e) {
						lanzarError(e);
					}
				}
    		);
    	}
    }

    @FXML
    void borrarOlimpiada(ActionEvent event) {
    	abrirEliminadorOlimpiada();
    }

    @FXML
    void editarDeporte(ActionEvent event) {
    	abrirEditorDeporte(true);
    }

    @FXML
    void editarDeportista(ActionEvent event) {
    	abrirEditorDeportista(true);
    }

    @FXML
    void editarEquipo(ActionEvent event) {
    	abrirEditorEquipo(true);
    }

    @FXML
    void editarEvento(ActionEvent event) {
    	Evento evento = cbEvento.getSelectionModel().getSelectedItem();
    	if (evento != null) {    		
    		abrirEditorEvento(evento);
    	}
    }

    @FXML
    void editarOlimpiada(ActionEvent event) {
    	abrirEditorOlimpiada(true);
    }

    @FXML
    void cambioEvento(ActionEvent event) {
		filtrarDatos();
    }

    @FXML
    void cambioDeporte(ActionEvent event) {
    	try {
    		Olimpiada olimpiada = cbOlimpiadas.getSelectionModel().getSelectedItem();
    		Deporte deporte = cbDeportes.getSelectionModel().getSelectedItem();
    		if (deporte != null) {
    			List<Evento> eventos = DAOEvento.getEventosByDeporteYOlimpiada(deporte, olimpiada);
    			cbEvento.getItems().clear();
    			cbEvento.getItems().addAll(eventos);
    			cbEvento.getSelectionModel().selectFirst();
    			refrescarEventos();
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
    			refrescarDeportes();
    			refrescarEventos();
    		}
    	} catch (OlimpiadasException e) {
    		lanzarError(e);
    	}
    	
    }
    
    /**
     * Formatea la tabla de participaciones.
     */
    private void formatearTabla() {
        tcNombre.setCellValueFactory(param -> {
            Participacion participacion = param.getValue();
            if (participacion != null && participacion.getDeportista() != null) {
                return new SimpleStringProperty(StringUtils.trimToEmpty(participacion.getDeportista().getNombre()));
            }
            return new SimpleStringProperty("");
        });

        tcSexo.setCellValueFactory(param -> {
            Participacion participacion = param.getValue();
            if (participacion != null && participacion.getDeportista() != null && participacion.getDeportista().getSexo() != null) {
                return new SimpleStringProperty(StringUtils.trimToEmpty(participacion.getDeportista().getSexo().getValor()));
            }
            return new SimpleStringProperty("");
        });
        
        tcPeso.setCellValueFactory(param -> {
            Participacion participacion = param.getValue();
            if (participacion != null && participacion.getDeportista() != null) {
                return new SimpleIntegerProperty(participacion.getDeportista().getPeso()).asObject();
            }
            return new SimpleIntegerProperty().asObject();
        });
        
        tcAltura.setCellValueFactory(param -> {
            Participacion participacion = param.getValue();
            if (participacion != null && participacion.getDeportista() != null) {
                return new SimpleIntegerProperty(participacion.getDeportista().getAltura()).asObject();
            }
            return new SimpleIntegerProperty().asObject();
        });
        
        tcEdad.setCellValueFactory(param -> {
            Participacion participacion = param.getValue();
            if (participacion != null) {
                return new SimpleIntegerProperty(participacion.getEdad()).asObject();
            }
            return new SimpleIntegerProperty().asObject();
        });
        
        tcMedalla.setCellValueFactory(param -> {
            Participacion participacion = param.getValue();
            if (participacion != null) {
                return new SimpleStringProperty(participacion.getMedalla().getValor());
            }
            return new SimpleStringProperty("");
        });
        
        
        MenuItem miAnadirParticipacion = new MenuItem("Añadir");
        MenuItem miEditarParticipacion = new MenuItem("Editar");
        MenuItem miBorrarParticipacion = new MenuItem("Borrar");
        
        miAnadirParticipacion.setOnAction(e -> {
            Evento evento = cbEvento.getSelectionModel().getSelectedItem();
            abrirEditorParticipacion(null, evento);
        });
        
        miEditarParticipacion.setOnAction(e -> {
            Evento evento = cbEvento.getSelectionModel().getSelectedItem();
            Participacion participacion = tvDeportistas.getSelectionModel().getSelectedItem();
            if (participacion != null) {
                abrirEditorParticipacion(participacion, evento);
            }
        });
        
        miBorrarParticipacion.setOnAction(e -> {
            Participacion participacion = tvDeportistas.getSelectionModel().getSelectedItem();
            if (participacion != null) {                
                confirmarSiNo("¿Desea eliminar la participación seleccionada?", () -> {
                    try {
                        DAOParticipacion.borrarParticipacion(participacion);
                        mostrarInfo("La participación fue borrada");
                        filtrarDatos();
                    } catch (SQLException | OlimpiadasException ex) {
                        lanzarError(ex);
                    }
                    
                });
            }
        });
        
        ContextMenu cm = new ContextMenu(miAnadirParticipacion, miEditarParticipacion, miBorrarParticipacion);
        cm.setOnShowing(e -> {
            Evento evento = cbEvento.getSelectionModel().getSelectedItem();
            Participacion participacion = tvDeportistas.getSelectionModel().getSelectedItem();
            if (evento != null) {
                miAnadirParticipacion.setVisible(true);
                if (participacion != null) {
                    miEditarParticipacion.setVisible(true);
                    miBorrarParticipacion.setVisible(true);
                } else {
                    miEditarParticipacion.setVisible(false);
                    miBorrarParticipacion.setVisible(false);                
                }
            } else {
                miAnadirParticipacion.setVisible(false);
                miEditarParticipacion.setVisible(false);
                miBorrarParticipacion.setVisible(false);
                
            }
        });
        
        
        tvDeportistas.setContextMenu(cm);
        
    }
    
    /**
     * Abre el editor de olimpiada.
     *
     * @param editar indica si se está editando una olimpiada existente
     */
    private void abrirEditorOlimpiada(boolean editar) {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirOlimpiada.fxml"));
            root = loader.load();
            AnadirOlimpiadaController controlador = loader.getController();
            
            controlador
            .setControladorPrincipal(this)
            .setEditar(editar);
            
            Stage stage = new Stage();
            if (editar) {                
                stage.setTitle("EDITAR OLIMPIADA");
            }
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Abre el eliminador de olimpiada.
     */
    private void abrirEliminadorOlimpiada() {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BorrarOlimpiada.fxml"));
            root = loader.load();
            BorrarOlimpiadaController controlador = loader.getController();
            
            controlador
            .setControladorPrincipal(this);
            
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Abre el editor de deporte.
     *
     * @param editar indica si se está editando un deporte existente
     */
    private void abrirEditorDeporte(boolean editar) {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirDeporte.fxml"));
            root = loader.load();
            AnadirDeporteController controlador = loader.getController();
            
            controlador
            .setControladorPrincipal(this)
            .setEditar(editar);
            
            Stage stage = new Stage();
            if (editar) {                
                stage.setTitle("EDITAR DEPORTE");
            }
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Abre el eliminador de deporte.
     */
    private void abrirEliminadorDeporte() {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BorrarDeporte.fxml"));
            root = loader.load();
            BorrarDeporteController controlador = loader.getController();
            
            controlador
            .setControladorPrincipal(this);
            
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Abre el editor de evento.
     *
     * @param seleccionado el evento seleccionado para editar
     */
    private void abrirEditorEvento(Evento seleccionado) {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirEvento.fxml"));
            root = loader.load();
            AnadirEventoController controlador = loader.getController();
            
            controlador
            .setControladorPrincipal(this)
            .setOlimpiadaPredeterminada(cbOlimpiadas.getSelectionModel().getSelectedItem())
            .setDeportePredeterminado(cbDeportes.getSelectionModel().getSelectedItem())
            .setSeleccionado(seleccionado);
            
            Stage stage = new Stage();
            if (seleccionado != null) {                
                stage.setTitle("EDITAR EVENTO");
            }
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Abre el editor de equipo.
     *
     * @param editar indica si se está editando un equipo existente
     */
    private void abrirEditorEquipo(boolean editar) {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirEquipo.fxml"));
            root = loader.load();
            AnadirEquipoController controlador = loader.getController();
            
            controlador
            .setEditar(editar);
            
            Stage stage = new Stage();
            if (editar) {                
                stage.setTitle("EDITAR EQUIPO");
            }
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Abre el eliminador de equipo.
     */
    private void abrirEliminadorEquipo() {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BorrarEquipo.fxml"));
            root = loader.load();
            
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Abre el editor de deportista.
     *
     * @param editar indica si se está editando un deportista existente
     */
    private void abrirEditorDeportista(boolean editar) {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirDeportista.fxml"));
            root = loader.load();
            AnadirDeportistaController controlador = loader.getController();
            
            controlador
            .setEditar(editar);
            
            Stage stage = new Stage();
            if (editar) {                
                stage.setTitle("EDITAR DEPORTISTA");
            }
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Abre el eliminador de deportista.
     */
    private void abrirEliminadorDeportista() {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BorrarDeportista.fxml"));
            root = loader.load();
            
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Abre el editor de participación.
     *
     * @param participacion la participación a editar (puede ser null si se está añadiendo una nueva participación)
     * @param evento el evento asociado a la participación
     */
    private void abrirEditorParticipacion(Participacion participacion, Evento evento) {
        FlowPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirParticipacion.fxml"));
            root = loader.load();
            AnadirParticipacionController controlador = loader.getController();
            
            controlador
            .setControladorPrincipal(this)
            .setEvento(evento)
            .setSeleccionado(participacion);
            
            Stage stage = new Stage();
            if (participacion != null) {                
                stage.setTitle("EDITAR PARTICIPACIÓN");
            } else {
                stage.setTitle("AÑADIR PARTICIPACIÓN");                
            }
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//INICIALIZAR EL SELECT DE OLIMPIADAS
		refrescarOlimpiadas();
		//FORMATEAR LA TABLA
		formatearTabla();
	}

}
