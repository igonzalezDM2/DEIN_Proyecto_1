package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAODeportista;
import dao.DAOEquipo;
import dao.DAOEvento;
import excepciones.AnimalesException;

public class Participacion {
	
	private Deportista deportista;
	private Evento evento;
	private Equipo equipo;
	private Integer edad;
	private String medalla;
	
	public Participacion() {}
	public Participacion(ResultSet rs) throws AnimalesException {
		try {
			deportista = DAODeportista.getDeportista(rs.getInt("id_deportista"));
			evento = DAOEvento.getEvento(rs.getInt("id_evento"));
			equipo = DAOEquipo.getEquipo(rs.getInt("id_equipo"));
			edad = rs.getInt("edad") > 0 ? rs.getInt("edad") : null;
			medalla = rs.getString("medalla");
		} catch (SQLException e) {
			throw new AnimalesException(e);
		}
	}
	public Deportista getDeportista() {
		return deportista;
	}
	public Participacion setDeportista(Deportista deportista) {
		this.deportista = deportista;
		return this;
	}
	public Evento getEvento() {
		return evento;
	}
	public Participacion setEvento(Evento evento) {
		this.evento = evento;
		return this;
	}
	public Equipo getEquipo() {
		return equipo;
	}
	public Participacion setEquipo(Equipo equipo) {
		this.equipo = equipo;
		return this;
	}
	public int getEdad() {
		return edad;
	}
	public Participacion setEdad(int edad) {
		this.edad = edad;
		return this;
	}
	public String getMedalla() {
		return medalla;
	}
	public Participacion setMedalla(String medalla) {
		this.medalla = medalla;
		return this;
	}
	
}
