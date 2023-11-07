package dao;

import java.io.IOException;
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


public class DAOParticipacion extends DAOBase{
	
	public static List<Participacion> getParicipacionesByEvento(Evento evento) throws OlimpiadasException {
		List<Participacion> participaciones = new LinkedList<>();
		if (evento != null && evento.getId() > 0) {
			String sql = "SELECT * FROM Participacion WHERE id_evento = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, evento.getId());
				ResultSet rs = ps.executeQuery();
				if (rs.first()) {
					participaciones.add(new Participacion(rs));
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return null;
	}
	
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
					ps.setString(5, participacion.getMedalla());
					
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
	
	public static void modificarParticipacion (Participacion participacion) throws OlimpiadasException, SQLException, IOException {
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
					ps.setString(3, participacion.getMedalla());
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
				ps.setInt(1, participacion.getEvento().getId());
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
