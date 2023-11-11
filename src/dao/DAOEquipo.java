package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import excepciones.OlimpiadasException;
import model.Equipo;


/**
 * Clase DAOEquipo
 * 
 * Esta clase proporciona métodos para acceder a la base de datos y realizar operaciones relacionadas con la entidad Equipo.
 */
public class DAOEquipo extends DAOBase{
	
	/**
	 * Obtiene una lista de todos los equipos.
	 * 
	 * @return una lista de objetos Equipo
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static List<Equipo> getEquipos() throws OlimpiadasException {
		List<Equipo> equipos = new LinkedList<>();
		String sql = "SELECT * FROM Equipo";
		try(Connection con = getConexion()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				equipos.add(new Equipo(rs));
			}
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
		return equipos;
	}
	
	/**
	 * Obtiene un equipo por su id.
	 * 
	 * @param id el id del equipo a obtener
	 * @return el objeto Equipo correspondiente al id, o null si no se encuentra
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static Equipo getEquipo(int id) throws OlimpiadasException {
		if (id > 0) {
			String sql = "SELECT * FROM Equipo WHERE id_equipo = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.first()) {
					return new Equipo(rs);
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return null;
	}
	
	/**
	 * Añade un equipo a la base de datos.
	 * 
	 * @param equipo el equipo a añadir
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void anadirEquipo(Equipo equipo) throws OlimpiadasException, SQLException {
		if (equipo != null) {
			
			String sql = "INSERT INTO Equipo ("
					+ "nombre, "
					+ "iniciales) "
					+ "VALUES (?,?)";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, equipo.getNombre());
					ps.setString(2, equipo.getIniciales());
					
					ps.executeUpdate();
					
					ResultSet keys = ps.getGeneratedKeys();
					if (keys.first()) {
						equipo.setId(keys.getInt(1));
					}
				}
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
				throw new OlimpiadasException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new OlimpiadasException("Los datos introducidos están incompletos");
		}
	}
	
	/**
	 * Modifica un equipo en la base de datos.
	 * 
	 * @param equipo el equipo a modificar
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void modificarEquipo(Equipo equipo) throws OlimpiadasException, SQLException {
		if (equipo != null && equipo.getId() > 0) {
			
			String sql = "UPDATE Equipo SET "
					+ "nombre = ?, "
					+ "iniciales = ? "
					+ "WHERE id_equipo = ?";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, equipo.getNombre());
					ps.setString(2, equipo.getIniciales());
					ps.setInt(3, equipo.getId());
					
					ps.executeUpdate();
				}
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
				throw new OlimpiadasException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new OlimpiadasException("Los datos introducidos están incompletos");
		}
	}
	
	/**
	 * Borra un equipo de la base de datos.
	 * 
	 * @param equipo el equipo a borrar
	 * @throws SQLException si ocurre un error al acceder a la base de datos
	 * @throws OlimpiadasException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void borrarEquipo(Equipo equipo) throws SQLException, OlimpiadasException {
		if (equipo != null && equipo.getId() > 0) {			
			String sql = "DELETE FROM Equipo WHERE id_equipo = ?";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, equipo.getId());
				ps.executeUpdate();
				con.commit();
			} catch (SQLException e) {
				con.rollback();
				throw new OlimpiadasException(e);
			} finally {
				con.close();
			}
		}
		
	}
	
	
}
