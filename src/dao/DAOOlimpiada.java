package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import excepciones.OlimpiadasException;
import model.Olimpiada;


/**
 * Clase DAOOlimpiada
 * 
 * Esta clase proporciona métodos para acceder a la base de datos y realizar operaciones relacionadas con la entidad Olimpiada.
 */
public class DAOOlimpiada extends DAOBase {
	
	/**
	 * Obtiene una lista de olimpiadas.
	 * 
	 * @return una lista de objetos Olimpiada
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static List<Olimpiada> getOlimpiadas() throws OlimpiadasException {
		List<Olimpiada> olimpiadas = new LinkedList<>();
		try (Connection con = getConexion()) {
			Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM Olimpiada ORDER BY anio");
				while (rs.next()) {
					olimpiadas.add(new Olimpiada(rs));
				}
		} catch (SQLException e) {
			throw new OlimpiadasException(e);
		}
		return olimpiadas;
	}
	
	/**
	 * Obtiene una olimpiada por su id.
	 * 
	 * @param id el id de la olimpiada
	 * @return un objeto Olimpiada
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static Olimpiada getOlimpiada(int id) throws OlimpiadasException {
		if (id > 0) {
			String sql = "SELECT * FROM Olimpiada WHERE id_olimpiada = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.first()) {
					return new Olimpiada(rs);
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return null;
	}
	
	/**
	 * Añade una olimpiada a la base de datos.
	 * 
	 * @param olimpiada la olimpiada a añadir
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void anadirOlimpiada(Olimpiada olimpiada) throws OlimpiadasException, SQLException {
		if (olimpiada != null) {
			
			String sql = "INSERT INTO Olimpiada ("
					+ "nombre, "
					+ "anio, "
					+ "temporada, "
					+ "ciudad) "
					+ "VALUES (?,?,?,?)";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, olimpiada.getNombre());
					ps.setInt(2, olimpiada.getAnio());
					ps.setString(3, olimpiada.getTemporada().getValor());//no puede ser null
					ps.setString(4, olimpiada.getCiudad());
					
					ps.executeUpdate();
					
					ResultSet keys = ps.getGeneratedKeys();
					if (keys.first()) {
						olimpiada.setId(keys.getInt(1));
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
	 * Modifica una olimpiada en la base de datos.
	 * 
	 * @param olimpiada la olimpiada a modificar
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void modificarOlimpiada(Olimpiada olimpiada) throws OlimpiadasException, SQLException {
		if (olimpiada != null && olimpiada.getId() > 0) {
			
			String sql = "UPDATE Olimpiada SET "
					+ "nombre = ?, "
					+ "anio = ?, "
					+ "temporada = ?, "
					+ "ciudad = ? "
					+ "WHERE id_olimpiada = ?";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, olimpiada.getNombre());
					ps.setInt(2, olimpiada.getAnio());
					ps.setString(3, olimpiada.getTemporada().getValor());//no puede ser nulo
					ps.setString(4, olimpiada.getCiudad());
					ps.setInt(5, olimpiada.getId());
					
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
	 * Borra una olimpiada de la base de datos.
	 * 
	 * @param olimpiada la olimpiada a borrar
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static void borrarOlimpiada(Olimpiada olimpiada) throws SQLException, OlimpiadasException {
		if (olimpiada != null && olimpiada.getId() > 0) {			
			String sql = "DELETE FROM Olimpiada WHERE id_olimpiada = ?";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, olimpiada.getId());
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
