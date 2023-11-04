package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import enums.Sexo;
import excepciones.AnimalesException;

public class Deportista {
	private int id;
	private String nombre;
	private Sexo sexo;
	private Integer peso;
	private Integer altura;
	private byte[] foto;
	
	public Deportista() {}
	
	public Deportista(ResultSet rs) throws AnimalesException {
		try {
			id = rs.getInt("id_deportista");
			nombre = rs.getString("nombre");
			sexo = Sexo.getByValor(rs.getString("sexo"));
			peso = rs.getInt("peso") > 0 ? rs.getInt("peso") : null;
			altura = rs.getInt("altura") > 0 ? rs.getInt("altura") : null;
			foto = rs.getBytes("foto");
		} catch (SQLException e) {
			throw new AnimalesException(e);
		}
	}
	
	public int getId() {
		return id;
	}
	public Deportista setId(int id) {
		this.id = id;
		return this;
	}
	public String getNombre() {
		return nombre;
	}
	public Deportista setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public Deportista setSexo(Sexo sexo) {
		this.sexo = sexo;
		return this;
	}
	public Integer getPeso() {
		return peso;
	}
	public Deportista setPeso(Integer peso) {
		this.peso = peso;
		return this;
	}
	public Integer getAltura() {
		return altura;
	}
	public Deportista setAltura(Integer altura) {
		this.altura = altura;
		return this;
	}
	public byte[] getFoto() {
		return foto;
	}
	public Deportista setFoto(byte[] foto) {
		this.foto = foto;
		return this;
	}
	
	
}
