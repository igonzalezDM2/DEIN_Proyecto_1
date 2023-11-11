package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import enums.Sexo;
import excepciones.OlimpiadasException;

/**
 * Clase que representa un deportista.
 */
public class Deportista {
	private int id;
	private String nombre;
	private Sexo sexo;
	private Integer peso;
	private Integer altura;
	private byte[] foto;
	
	/**
	 * Constructor vacío de la clase Deportista.
	 */
	public Deportista() {}
	
	/**
	 * Constructor de la clase Deportista que recibe un ResultSet.
	 * 
	 * @param rs el ResultSet con los datos del deportista
	 * @throws OlimpiadasException si ocurre un error al obtener los datos del ResultSet
	 */
	public Deportista(ResultSet rs) throws OlimpiadasException {
		try {
			id = rs.getInt("id_deportista");
			nombre = rs.getString("nombre");
			sexo = Sexo.getByValor(rs.getString("sexo"));
			peso = rs.getInt("peso") > 0 ? rs.getInt("peso") : null;
			altura = rs.getInt("altura") > 0 ? rs.getInt("altura") : null;
			foto = rs.getBytes("foto");
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
	}
	
	/**
	 * Obtiene el ID del deportista.
	 * 
	 * @return el ID del deportista
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Establece el ID del deportista.
	 * 
	 * @param id el ID del deportista
	 * @return la instancia actual de Deportista
	 */
	public Deportista setId(int id) {
		this.id = id;
		return this;
	}
	
	/**
	 * Obtiene el nombre del deportista.
	 * 
	 * @return el nombre del deportista
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Establece el nombre del deportista.
	 * 
	 * @param nombre el nombre del deportista
	 * @return la instancia actual de Deportista
	 */
	public Deportista setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	/**
	 * Obtiene el sexo del deportista.
	 * 
	 * @return el sexo del deportista
	 */
	public Sexo getSexo() {
		return sexo;
	}
	
	/**
	 * Establece el sexo del deportista.
	 * 
	 * @param sexo el sexo del deportista
	 * @return la instancia actual de Deportista
	 */
	public Deportista setSexo(Sexo sexo) {
		this.sexo = sexo;
		return this;
	}
	
	/**
	 * Obtiene el peso del deportista.
	 * 
	 * @return el peso del deportista
	 */
	public Integer getPeso() {
		return peso;
	}
	
	/**
	 * Establece el peso del deportista.
	 * 
	 * @param peso el peso del deportista
	 * @return la instancia actual de Deportista
	 */
	public Deportista setPeso(Integer peso) {
		this.peso = peso;
		return this;
	}
	
	/**
	 * Obtiene la altura del deportista.
	 * 
	 * @return la altura del deportista
	 */
	public Integer getAltura() {
		return altura;
	}
	
	/**
	 * Establece la altura del deportista.
	 * 
	 * @param altura la altura del deportista
	 * @return la instancia actual de Deportista
	 */
	public Deportista setAltura(Integer altura) {
		this.altura = altura;
		return this;
	}
	
	/**
	 * Obtiene la foto del deportista.
	 * 
	 * @return la foto del deportista
	 */
	public byte[] getFoto() {
		return foto;
	}
	
	/**
	 * Establece la foto del deportista.
	 * 
	 * @param foto la foto del deportista
	 * @return la instancia actual de Deportista
	 */
	public Deportista setFoto(byte[] foto) {
		this.foto = foto;
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
		Deportista other = (Deportista) obj;
		return id == other.id;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
