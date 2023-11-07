package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import excepciones.OlimpiadasException;

public class Deporte {

	private int id;
	private String nombre;
	
	public Deporte() {}
	public Deporte(ResultSet rs) throws OlimpiadasException {
		try {
			id = rs.getInt("id_deporte");
			nombre = rs.getString("nombre");
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
	}
	public int getId() {
		return id;
	}
	public Deporte setId(int id) {
		this.id = id;
		return this;
	}
	public String getNombre() {
		return nombre;
	}
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
