package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import excepciones.OlimpiadasException;
import model.Deportista;
import model.Evento;


/**
 * Clase DAODeportista
 * 
 * Esta clase se encarga de realizar operaciones de acceso a datos relacionadas con los deportistas.
 */
public class DAODeportista extends DAOBase{
	
	/**
	 * Obtiene una lista de deportistas por evento.
	 * 
	 * @param evento el evento del cual se quieren obtener los deportistas
	 * @return una lista de deportistas que participan en el evento
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static List<Deportista> getDeportistasPorEvento(Evento evento) throws OlimpiadasException {
		List<Deportista> lista = new LinkedList<>();
		if (evento != null && evento.getId() > 0) {			
			String sql = "SELECT * FROM Deportista INNER JOIN Participacion ON Participacion.id_deportista = Deportista.id_deportista WHERE Participacion.id_evento = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, evento.getId());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					lista.add(new Deportista(rs));
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return lista;
	}

	/**
	 * Obtiene una lista de todos los deportistas.
	 * 
	 * @return una lista de todos los deportistas
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static List<Deportista> getDeportistas() throws OlimpiadasException {
		List<Deportista> lista = new LinkedList<>();
			String sql = "SELECT * FROM Deportista";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					lista.add(new Deportista(rs));
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		return lista;
	}
	
	/**
	 * Obtiene un deportista por su ID.
	 * 
	 * @param id el ID del deportista a obtener
	 * @return el deportista con el ID especificado, o null si no se encuentra
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static Deportista getDeportista(int id) throws OlimpiadasException {
		if (id > 0) {
			String sql = "SELECT * FROM Deportista WHERE id_deportista = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.first()) {
					return new Deportista(rs);
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return null;
	}
	
	/**
	 * Añade un deportista a la base de datos.
	 * 
	 * @param deportista el deportista a añadir
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void anadirDeportista(Deportista deportista) throws OlimpiadasException, SQLException {
		if (deportista != null) {
			
			String sql = "INSERT INTO Deportista ("
					+ "nombre, "
					+ "sexo, "
					+ "peso, "
					+ "altura, "
					+ "foto) "
					+ "VALUES (?,?,?,?,?)";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, deportista.getNombre());
					ps.setString(2, deportista.getSexo() != null ? deportista.getSexo().getValor() : null);
					ps.setInt(3, deportista.getPeso());
					ps.setInt(4, deportista.getAltura());
					ps.setBytes(5, deportista.getFoto());
					
					ps.executeUpdate();
					
					ResultSet keys = ps.getGeneratedKeys();
					if (keys.first()) {
						deportista.setId(keys.getInt(1));
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
	 * Modifica los datos de un deportista en la base de datos.
	 * 
	 * @param deportista el deportista a modificar
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	public static void modificarDeportista(Deportista deportista) throws OlimpiadasException, SQLException {
		if (deportista != null && deportista.getId() > 0) {
			
			String sql = "UPDATE Deportista SET "
					+ "nombre = ?, "
					+ "sexo = ?, "
					+ "peso = ?, "
					+ "altura = ?, "
					+ "foto = ? "
					+ "WHERE id_deportista = ?";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, deportista.getNombre());
					ps.setString(2, deportista.getSexo() != null ? deportista.getSexo().getValor() : null);
					ps.setInt(3, deportista.getPeso());
					ps.setInt(4, deportista.getAltura());
					ps.setBytes(5, deportista.getFoto());
					ps.setInt(6, deportista.getId());
					
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
	 * Borra un deportista de la base de datos.
	 * 
	 * @param deportista el deportista a borrar
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 * @throws OlimpiadasException si ocurre un error al acceder a la base de datos
	 */
	public static void borrarDeportista(Deportista deportista) throws SQLException, OlimpiadasException {
		if (deportista != null && deportista.getId() > 0) {			
			String sql = "DELETE FROM Deportista WHERE id_deportista = ?";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, deportista.getId());
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
