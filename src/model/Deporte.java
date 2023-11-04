package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.AnimalesException;

public class Deporte {

	private int id;
	private String nombre;
	
	public Deporte() {}
	public Deporte(ResultSet rs) throws AnimalesException {
		try {
			id = rs.getInt("id_deporte");
			nombre = rs.getString("nombre");
		} catch (SQLException e) {
			throw new AnimalesException(e);
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
	
	
}
