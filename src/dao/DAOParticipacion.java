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
import model.Participacion;


/**
 * Clase que representa el DAO de Participacion.
 */
public class DAOParticipacion extends DAOBase{
	
	/**
	 * Obtiene una lista de participaciones por evento.
	 * 
	 * @param evento El evento del que se desean obtener las participaciones.
	 * @return Una lista de participaciones del evento.
	 * @throws OlimpiadasException Si ocurre un error durante la obtención de las participaciones.
	 */
	public static List<Participacion> getParicipacionesByEvento(Evento evento) throws OlimpiadasException {
		List<Participacion> participaciones = new LinkedList<>();
		if (evento != null && evento.getId() > 0) {
			String sql = "SELECT * FROM Participacion WHERE id_evento = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, evento.getId());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					participaciones.add(new Participacion(rs));
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return participaciones;
	}
	
	/**
	 * Obtiene una participacion por deportista y evento.
	 * 
	 * @param deportista El deportista de la participacion.
	 * @param evento El evento de la participacion.
	 * @return La participacion del deportista en el evento.
	 * @throws OlimpiadasException Si ocurre un error durante la obtención de la participacion.
	 */
	public static Participacion getParticipacion(Deportista deportista, Evento evento) throws OlimpiadasException {
		if (deportista != null && deportista.getId() > 0 && evento != null && evento.getId() > 0) {
			String sql = "SELECT * FROM Participacion WHERE id_deportista = ? AND id_evento = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, deportista.getId());
				ps.setInt(2, evento.getId());
				ResultSet rs = ps.executeQuery();
				if (rs.first()) {
					return new Participacion(rs);
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return null;
	}
	
	/**
	 * Añade una participacion.
	 * 
	 * @param participacion La participacion a añadir.
	 * @throws OlimpiadasException Si ocurre un error durante la adición de la participacion.
	 * @throws SQLException Si ocurre un error de SQL durante la adición de la participacion.
	 */
	public static void anadirParticipacion(Participacion participacion) throws OlimpiadasException, SQLException {
		if (participacion != null &&
				participacion.getDeportista() != null &&
				participacion.getDeportista().getId() > 0 &&
				participacion.getEvento() != null &&
				participacion.getEvento().getId() > 0 &&
				participacion.getEquipo() != null &&
				participacion.getEquipo().getId() > 0) {
			
			String sql = "INSERT INTO Participacion ("
					+ "id_deportista, "
					+ "id_evento, "
					+ "id_equipo, "
					+ "edad, "
					+ "medalla) "
					+ "VALUES (?,?,?,?,?)";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setInt(1, participacion.getDeportista().getId());
					ps.setInt(2, participacion.getEvento().getId());
					ps.setInt(3, participacion.getEquipo().getId());
					ps.setInt(4, participacion.getEdad());
					ps.setString(5, participacion.getMedalla().getValor());
					
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
	 * Modifica una participacion.
	 * 
	 * @param participacion La participacion a modificar.
	 * @throws OlimpiadasException Si ocurre un error durante la modificación de la participacion.
	 * @throws SQLException Si ocurre un error de SQL durante la modificación de la participacion.
	 */
	public static void modificarParticipacion (Participacion participacion) throws OlimpiadasException, SQLException {
		if (participacion != null &&
				participacion.getDeportista() != null &&
				participacion.getDeportista().getId() > 0 &&
				participacion.getEvento() != null &&
				participacion.getEvento().getId() > 0 &&
				participacion.getEquipo() != null &&
				participacion.getEquipo().getId() > 0
		) {
			
			String sql = "UPDATE Participacion SET "
					+ "id_equipo = ?, "
					+ "edad = ?, "
					+ "medalla = ? "
					+ "WHERE id_deportista = ? "
					+ "AND id_evento = ?";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setInt(1, participacion.getEquipo().getId());
					ps.setInt(2, participacion.getEdad());
					ps.setString(3, participacion.getMedalla().getValor());
					ps.setInt(4, participacion.getDeportista().getId());
					ps.setInt(5, participacion.getEvento().getId());
					
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
	 * Borra una participacion.
	 * 
	 * @param participacion La participacion a borrar.
	 * @throws OlimpiadasException Si ocurre un error durante la eliminación de la participacion.
	 * @throws SQLException Si ocurre un error de SQL durante la eliminación de la participacion.
	 */
	public static void borrarParticipacion(Participacion participacion) throws SQLException, OlimpiadasException {
		if (participacion != null &&
				participacion.getDeportista() != null &&
				participacion.getDeportista().getId() > 0 &&
				participacion.getEquipo() != null &&
				participacion.getEquipo().getId() > 0) {			
			String sql = "DELETE FROM Participacion WHERE id_deportista = ? AND id_evento = ?";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, participacion.getDeportista().getId());
				ps.setInt(2, participacion.getEvento().getId());
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
