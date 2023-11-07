package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import excepciones.OlimpiadasException;

public class Equipo {

	private int id;
	private String nombre;
	private String iniciales;
	
	public Equipo() {}
	public Equipo(ResultSet rs) throws OlimpiadasException {
		try {
			id = rs.getInt("id_equipo");
			nombre = rs.getString("nombre");
			iniciales = rs.getString("iniciales");
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
	}
	public int getId() {
		return id;
	}
	public Equipo setId(int id) {
		this.id = id;
		return this;
	}
	public String getNombre() {
		return nombre;
	}
	public Equipo setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	public String getIniciales() {
		return iniciales;
	}
	public Equipo setIniciales(String iniciales) {
		this.iniciales = iniciales;
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
		Equipo other = (Equipo) obj;
		return id == other.id;
	}
	@Override
	public String toString() {
		return iniciales + " - " + nombre;
	}
	
	
	
}
