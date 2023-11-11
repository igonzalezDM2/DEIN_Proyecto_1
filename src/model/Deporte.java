package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import excepciones.OlimpiadasException;

/**
 * Clase que representa un deporte.
 */
/**
 * Clase que representa un deporte.
 */
public class Deporte {

	private int id;
	private String nombre;
	
	public Deporte() {}
	
	/**
	 * Constructor de la clase Deporte que recibe un ResultSet.
	 * 
	 * @param rs el ResultSet con los datos del deporte
	 * @throws OlimpiadasException si ocurre un error al obtener los datos del ResultSet
	 */
	public Deporte(ResultSet rs) throws OlimpiadasException {
		try {
			id = rs.getInt("id_deporte");
			nombre = rs.getString("nombre");
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
	}

	/**
	 * Obtiene el ID del deporte.
	 * 
	 * @return el ID del deporte
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el ID del deporte.
	 * 
	 * @param id el ID del deporte
	 * @return la instancia actual de Deporte
	 */
	public Deporte setId(int id) {
		this.id = id;
		return this;
	}

	/**
	 * Obtiene el nombre del deporte.
	 * 
	 * @return el nombre del deporte
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del deporte.
	 * 
	 * @param nombre el nombre del deporte
	 * @return la instancia actual de Deporte
	 */
	public Deporte setNombre(String nombre) {
		this.nombre = nombre;
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
		Deporte other = (Deporte) obj;
		return id == other.id;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	
	
}
