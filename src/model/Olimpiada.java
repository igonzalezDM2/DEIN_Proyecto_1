package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import enums.Temporada;
import excepciones.AnimalesException;

public class Olimpiada {

	private int id;
	private String nombre;
	private int anio;
	private Temporada temporada;
	private String ciudad;
	
	public Olimpiada() {}
	public Olimpiada(ResultSet rs) throws AnimalesException {
		try {
			id = rs.getInt("id_olimpiada");
			nombre = rs.getString("nombre");
			anio = rs.getInt("anio");
			temporada = Temporada.getByValor(rs.getString("temporada"));
			ciudad = rs.getString("ciudad");
		} catch (SQLException e) {
			throw new AnimalesException(e);
		}
	}
	public int getId() {
		return id;
	}
	public Olimpiada setId(int id) {
		this.id = id;
		return this;
	}
	public String getNombre() {
		return nombre;
	}
	public Olimpiada setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	public int getAnio() {
		return anio;
	}
	public Olimpiada setAnio(int anio) {
		this.anio = anio;
		return this;
	}
	public Temporada getTemporada() {
		return temporada;
	}
	public Olimpiada setTemporada(Temporada temporada) {
		this.temporada = temporada;
		return this;
	}
	public String getCiudad() {
		return ciudad;
	}
	public Olimpiada setCiudad(String ciudad) {
		this.ciudad = ciudad;
		return this;
	}
	
}
