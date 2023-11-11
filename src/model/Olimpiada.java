package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import enums.Temporada;
import excepciones.OlimpiadasException;

/**
 * Clase que representa una Olimpiada.
 */
public class Olimpiada {

    private int id;
    private String nombre;
    private int anio;
    private Temporada temporada;
    private String ciudad;
    
    /**
     * Constructor vacío de la clase Olimpiada.
     */
    public Olimpiada() {}
    
    /**
     * Constructor de la clase Olimpiada que recibe un ResultSet.
     * 
     * @param rs el ResultSet con los datos de la olimpiada
     * @throws OlimpiadasException si ocurre un error al obtener los datos del ResultSet
     */
    public Olimpiada(ResultSet rs) throws OlimpiadasException {
        try {
            id = rs.getInt("id_olimpiada");
            nombre = rs.getString("nombre");
            anio = rs.getInt("anio");
            temporada = Temporada.getByValor(rs.getString("temporada"));
            ciudad = rs.getString("ciudad");
        } catch (SQLException e) {
            throw new OlimpiadasException(e);
        }
    }
    
    /**
     * Obtiene el ID de la olimpiada.
     * 
     * @return el ID de la olimpiada
     */
    public int getId() {
        return id;
    }
    
    /**
     * Establece el ID de la olimpiada.
     * 
     * @param id el ID de la olimpiada
     * @return la instancia actual de Olimpiada
     */
    public Olimpiada setId(int id) {
        this.id = id;
        return this;
    }
    
    /**
     * Obtiene el nombre de la olimpiada.
     * 
     * @return el nombre de la olimpiada
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre de la olimpiada.
     * 
     * @param nombre el nombre de la olimpiada
     * @return la instancia actual de Olimpiada
     */
    public Olimpiada setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
    
    /**
     * Obtiene el año de la olimpiada.
     * 
     * @return el año de la olimpiada
     */
    public int getAnio() {
        return anio;
    }
    
    /**
     * Establece el año de la olimpiada.
     * 
     * @param anio el año de la olimpiada
     * @return la instancia actual de Olimpiada
     */
    public Olimpiada setAnio(int anio) {
        this.anio = anio;
        return this;
    }
    
    /**
     * Obtiene la temporada de la olimpiada.
     * 
     * @return la temporada de la olimpiada
     */
    public Temporada getTemporada() {
        return temporada;
    }
    
    /**
     * Establece la temporada de la olimpiada.
     * 
     * @param temporada la temporada de la olimpiada
     * @return la instancia actual de Olimpiada
     */
    public Olimpiada setTemporada(Temporada temporada) {
        this.temporada = temporada;
        return this;
    }
    
    /**
     * Obtiene la ciudad de la olimpiada.
     * 
     * @return la ciudad de la olimpiada
     */
    public String getCiudad() {
        return ciudad;
    }
    
    /**
     * Establece la ciudad de la olimpiada.
     * 
     * @param ciudad la ciudad de la olimpiada
     * @return la instancia actual de Olimpiada
     */
    public Olimpiada setCiudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }
    
    /**
     * Calcula el hash code de la olimpiada.
     * 
     * @return el hash code de la olimpiada
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    /**
     * Compara si la olimpiada es igual a otro objeto.
     * 
     * @param obj el objeto a comparar
     * @return true si la olimpiada es igual al objeto, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Olimpiada other = (Olimpiada) obj;
        return id == other.id;
    }
    
    /**
     * Devuelve una representación en forma de cadena de la olimpiada.
     * 
     * @return una representación en forma de cadena de la olimpiada
     */
    @Override
    public String toString() {
        return ciudad + " " + anio;
    }
    
}
