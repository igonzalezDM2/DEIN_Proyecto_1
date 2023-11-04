package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.AnimalesException;

public class Equipo {

	private int id;
	private String nombre;
	private String iniciales;
	
	public Equipo() {}
	public Equipo(ResultSet rs) throws AnimalesException {
		try {
			id = rs.getInt("id_equipo");
			nombre = rs.getString("nombre");
			iniciales = rs.getString("iniciales");
		} catch (SQLException e) {
			throw new AnimalesException(e);
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
	
	
	
}
