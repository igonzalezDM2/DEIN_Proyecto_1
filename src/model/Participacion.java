package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import dao.DAODeportista;
import dao.DAOEquipo;
import dao.DAOEvento;
import enums.Medalla;
import excepciones.OlimpiadasException;

/**
 * Clase que representa una participación en las Olimpiadas.
 */
public class Participacion {
	
	private Deportista deportista;
	private Evento evento;
	private Equipo equipo;
	private Integer edad;
	private Medalla medalla;
	
	/**
	 * Constructor vacío de la clase Participacion.
	 */
	public Participacion() {}
	
	/**
	 * Constructor de la clase Participacion que recibe un ResultSet.
	 * 
	 * @param rs el ResultSet con los datos de la participación
	 * @throws OlimpiadasException si ocurre un error al obtener los datos del ResultSet
	 */
	public Participacion(ResultSet rs) throws OlimpiadasException {
		try {
			deportista = DAODeportista.getDeportista(rs.getInt("id_deportista"));
			evento = DAOEvento.getEvento(rs.getInt("id_evento"));
			equipo = DAOEquipo.getEquipo(rs.getInt("id_equipo"));
			edad = rs.getInt("edad") > 0 ? rs.getInt("edad") : null;
			medalla = Medalla.getByValor(rs.getString("medalla"));
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
	}
	
	/**
	 * Obtiene el deportista de la participación.
	 * 
	 * @return el deportista de la participación
	 */
	public Deportista getDeportista() {
		return deportista;
	}
	
	/**
	 * Establece el deportista de la participación.
	 * 
	 * @param deportista el deportista de la participación
	 * @return la instancia actual de Participacion
	 */
	public Participacion setDeportista(Deportista deportista) {
		this.deportista = deportista;
		return this;
	}
	
	/**
	 * Obtiene el evento de la participación.
	 * 
	 * @return el evento de la participación
	 */
	public Evento getEvento() {
		return evento;
	}
	
	/**
	 * Establece el evento de la participación.
	 * 
	 * @param evento el evento de la participación
	 * @return la instancia actual de Participacion
	 */
	public Participacion setEvento(Evento evento) {
		this.evento = evento;
		return this;
	}
	
	/**
	 * Obtiene el equipo de la participación.
	 * 
	 * @return el equipo de la participación
	 */
	public Equipo getEquipo() {
		return equipo;
	}
	
	/**
	 * Establece el equipo de la participación.
	 * 
	 * @param equipo el equipo de la participación
	 * @return la instancia actual de Participacion
	 */
	public Participacion setEquipo(Equipo equipo) {
		this.equipo = equipo;
		return this;
	}
	
	/**
	 * Obtiene la edad de la participación.
	 * 
	 * @return la edad de la participación
	 */
	public int getEdad() {
		return edad;
	}
	
	/**
	 * Establece la edad de la participación.
	 * 
	 * @param edad la edad de la participación
	 * @return la instancia actual de Participacion
	 */
	public Participacion setEdad(int edad) {
		this.edad = edad;
		return this;
	}
	
	/**
	 * Obtiene la medalla de la participación.
	 * 
	 * @return la medalla de la participación
	 */
	public Medalla getMedalla() {
		if (medalla == null) {
			return Medalla.NINGUNA;
		}
		return medalla;
	}
	
	/**
	 * Establece la medalla de la participación.
	 * 
	 * @param medalla la medalla de la participación
	 * @return la instancia actual de Participacion
	 */
	public Participacion setMedalla(Medalla medalla) {
		this.medalla = medalla;
		return this;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(deportista, evento);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participacion other = (Participacion) obj;
		return Objects.equals(deportista, other.deportista) && Objects.equals(evento, other.evento);
	}
	
}
