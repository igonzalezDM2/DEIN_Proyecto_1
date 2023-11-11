package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import excepciones.OlimpiadasException;

/**
 * Clase que representa un equipo.
 */
public class Equipo {

    private int id;
    private String nombre;
    private String iniciales;
    
    /**
     * Constructor vacío de la clase Equipo.
     */
    public Equipo() {}
    
    /**
     * Constructor de la clase Equipo que recibe un ResultSet.
     * 
     * @param rs el ResultSet con los datos del equipo
     * @throws OlimpiadasException si ocurre un error al obtener los datos del ResultSet
     */
    public Equipo(ResultSet rs) throws OlimpiadasException {
        try {
            id = rs.getInt("id_equipo");
            nombre = rs.getString("nombre");
            iniciales = rs.getString("iniciales");
        } catch (SQLException e) {
            throw new OlimpiadasException(e);
        }
    }
    
    /**
     * Obtiene el ID del equipo.
     * 
     * @return el ID del equipo
     */
    public int getId() {
        return id;
    }
    
    /**
     * Establece el ID del equipo.
     * 
     * @param id el ID del equipo
     * @return la instancia actual de Equipo
     */
    public Equipo setId(int id) {
        this.id = id;
        return this;
    }
    
    /**
     * Obtiene el nombre del equipo.
     * 
     * @return el nombre del equipo
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del equipo.
     * 
     * @param nombre el nombre del equipo
     * @return la instancia actual de Equipo
     */
    public Equipo setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
    
    /**
     * Obtiene las iniciales del equipo.
     * 
     * @return las iniciales del equipo
     */
    public String getIniciales() {
        return iniciales;
    }
    
    /**
     * Establece las iniciales del equipo.
     * 
     * @param iniciales las iniciales del equipo
     * @return la instancia actual de Equipo
     */
    public Equipo setIniciales(String iniciales) {
        this.iniciales = iniciales;
        return this;
    }
    
    /**
     * Calcula el hash code del equipo basado en su ID.
     * 
     * @return el hash code del equipo
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    /**
     * Compara si el equipo es igual a otro objeto.
     * 
     * @param obj el objeto a comparar
     * @return true si el equipo es igual al objeto, false en caso contrario
     */
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
    
    /**
     * Devuelve una representación en forma de cadena del equipo.
     * 
     * @return una representación en forma de cadena del equipo
     */
    @Override
    public String toString() {
        return iniciales + " - " + nombre;
    }
    
}
