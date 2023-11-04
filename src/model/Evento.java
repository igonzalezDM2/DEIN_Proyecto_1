package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAODeporte;
import dao.DAOOlimpiada;
import excepciones.AnimalesException;

public class Evento {
	
	private int id;
	private String nombre;
	private Olimpiada olimpiada;
	private Deporte deporte;
	
	public Evento() {}
	public Evento(ResultSet rs) throws AnimalesException {
		try {
			id = rs.getInt("id_evento");
			nombre = rs.getString("nombre");
			olimpiada = DAOOlimpiada.getOlimpiada(rs.getInt("id_olimpiada"));
			deporte = DAODeporte.getDeporte(rs.getInt("id_deporte"));
		} catch (SQLException e) {
			throw new AnimalesException(e);
		}
	}
	
	public int getId() {
		return id;
	}
	public Evento setId(int id) {
		this.id = id;
		return this;
	}
	public String getNombre() {
		return nombre;
	}
	public Evento setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	public Olimpiada getOlimpiada() {
		return olimpiada;
	}
	public Evento setOlimpiada(Olimpiada olimpiada) {
		this.olimpiada = olimpiada;
		return this;
	}
	public Deporte getDeporte() {
		return deporte;
	}
	public Evento setDeporte(Deporte deporte) {
		this.deporte = deporte;
		return this;
	}
	
	
}
