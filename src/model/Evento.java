package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import dao.DAODeporte;
import dao.DAOOlimpiada;
import excepciones.OlimpiadasException;

/**
 * Clase que representa un evento.
 */
public class Evento {
	
	private int id;
	private String nombre;
	private Olimpiada olimpiada;
	private Deporte deporte;
	
	/**
	 * Constructor vacío de la clase Evento.
	 */
	public Evento() {}
	
	/**
	 * Constructor de la clase Evento que recibe un ResultSet.
	 * 
	 * @param rs el ResultSet con los datos del evento
	 * @throws OlimpiadasException si ocurre un error al obtener los datos del ResultSet
	 */
	public Evento(ResultSet rs) throws OlimpiadasException {
		try {
			id = rs.getInt("id_evento");
			nombre = rs.getString("nombre");
			olimpiada = DAOOlimpiada.getOlimpiada(rs.getInt("id_olimpiada"));
			deporte = DAODeporte.getDeporte(rs.getInt("id_deporte"));
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
	}
	
	/**
	 * Obtiene el ID del evento.
	 * 
	 * @return el ID del evento
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Establece el ID del evento.
	 * 
	 * @param id el ID del evento
	 * @return la instancia actual de Evento
	 */
	public Evento setId(int id) {
		this.id = id;
		return this;
	}
	
	/**
	 * Obtiene el nombre del evento.
	 * 
	 * @return el nombre del evento
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Establece el nombre del evento.
	 * 
	 * @param nombre el nombre del evento
	 * @return la instancia actual de Evento
	 */
	public Evento setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	/**
	 * Obtiene la olimpiada del evento.
	 * 
	 * @return la olimpiada del evento
	 */
	public Olimpiada getOlimpiada() {
		return olimpiada;
	}
	
	/**
	 * Establece la olimpiada del evento.
	 * 
	 * @param olimpiada la olimpiada del evento
	 * @return la instancia actual de Evento
	 */
	public Evento setOlimpiada(Olimpiada olimpiada) {
		this.olimpiada = olimpiada;
		return this;
	}
	
	/**
	 * Obtiene el deporte del evento.
	 * 
	 * @return el deporte del evento
	 */
	public Deporte getDeporte() {
		return deporte;
	}
	
	/**
	 * Establece el deporte del evento.
	 * 
	 * @param deporte el deporte del evento
	 * @return la instancia actual de Evento
	 */
	public Evento setDeporte(Deporte deporte) {
		this.deporte = deporte;
		return this;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		return id == other.id;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
