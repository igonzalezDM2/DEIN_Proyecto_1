package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import excepciones.OlimpiadasException;
import model.Deporte;
import model.Olimpiada;


/**
 * Clase DAODeporte
 * 
 * Esta clase proporciona métodos para acceder a la base de datos y realizar operaciones relacionadas con la entidad Deporte.
 */
public class DAODeporte extends DAOBase{
	
	/**
	 * Obtiene una lista de todos los deportes.
	 * 
	 * @return una lista de objetos Deporte
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static List<Deporte> getDeportes() throws OlimpiadasException {
		List<Deporte> deportes = new LinkedList<>();
		String sql = "SELECT * FROM Deporte";
		try(Connection con = getConexion()) {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				deportes.add(new Deporte(rs));
			}
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
		return deportes;
	}
	
	/**
	 * Obtiene una lista de deportes por olimpiada.
	 * 
	 * @param olimpiada la olimpiada para la cual se obtienen los deportes
	 * @return una lista de objetos Deporte
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static List<Deporte> getDeportesByOlimpiada(Olimpiada olimpiada) throws OlimpiadasException {
		List<Deporte> deportes = new LinkedList<>();
		if (olimpiada != null && olimpiada.getId() > 0) {
			String sql = "SELECT Deporte.* FROM Deporte INNER JOIN Evento ON Deporte.id_deporte = Evento.id_deporte WHERE id_olimpiada = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, olimpiada.getId());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					deportes.add(new Deporte(rs));
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return deportes;
	}
	
	/**
	 * Obtiene un deporte por su ID.
	 * 
	 * @param id el ID del deporte a obtener
	 * @return el objeto Deporte correspondiente al ID, o null si no se encuentra
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static Deporte getDeporte(int id) throws OlimpiadasException {
		if (id > 0) {
			String sql = "SELECT * FROM Deporte WHERE id_deporte = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.first()) {
					return new Deporte(rs);
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return null;
	}
	
	/**
	 * Añade un deporte a la base de datos.
	 * 
	 * @param deporte el deporte a añadir
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void anadirDeporte(Deporte deporte) throws OlimpiadasException, SQLException {
		if (deporte != null) {
			
			String sql = "INSERT INTO Deporte ("
					+ "nombre) "
					+ "VALUES (?)";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, deporte.getNombre());
					
					ps.executeUpdate();
					
					ResultSet keys = ps.getGeneratedKeys();
					if (keys.first()) {
						deporte.setId(keys.getInt(1));
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
	 * Modifica un deporte en la base de datos.
	 * 
	 * @param deporte el deporte a modificar
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void modificarDeporte (Deporte deporte) throws OlimpiadasException, SQLException {
		if (deporte != null && deporte.getId() > 0) {
			
			String sql = "UPDATE Deporte SET "
					+ "nombre = ? "
					+ "WHERE id_deporte = ?";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, deporte.getNombre());
					ps.setInt(2, deporte.getId());
					
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
	 * Borra un deporte de la base de datos.
	 * 
	 * @param deporte el deporte a borrar
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void borrarDeporte(Deporte deporte) throws SQLException, OlimpiadasException {
		if (deporte != null && deporte.getId() > 0) {			
			String sql = "DELETE FROM Deporte WHERE id_deporte = ?";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, deporte.getId());
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
