package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import excepciones.OlimpiadasException;
import model.Deporte;
import model.Evento;
import model.Olimpiada;


public class DAOEvento extends DAOBase{
	
	public static List<Evento> getEventosByDeporteYOlimpiada(Deporte deporte, Olimpiada olimpiada) throws OlimpiadasException {
		List<Evento> eventos = new LinkedList<>();
		if (deporte != null && deporte.getId() > 0 && olimpiada != null && olimpiada.getId() > 0) {
			String sql = "SELECT * FROM Evento WHERE id_deporte = ? AND id_olimpiada = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, deporte.getId());
				ps.setInt(2, olimpiada.getId());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					eventos.add(new Evento(rs));
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return eventos;
	}
	
	public static List<Evento> getEventosByDeporte(Deporte deporte) throws OlimpiadasException {
		List<Evento> eventos = new LinkedList<>();
		if (deporte != null && deporte.getId() > 0) {
			String sql = "SELECT * FROM Evento WHERE id_deporte = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, deporte.getId());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					eventos.add(new Evento(rs));
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return eventos;
	}
	
	public static Evento getEvento(int id) throws OlimpiadasException {
		if (id > 0) {
			String sql = "SELECT * FROM Evento WHERE id_evento = ?";
			try(Connection con = getConexion()) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.first()) {
					return new Evento(rs);
				}
			} catch (SQLException e) {
				throw new OlimpiadasException(e);
			}
		}
		return null;
	}
	
	public static void anadirEvento(Evento evento) throws OlimpiadasException, SQLException {
		if (evento != null) {
			
			String sql = "INSERT INTO Evento ("
					+ "nombre, "
					+ "id_olimpiada, "
					+ "id_deporte) "
					+ "VALUES (?,?,?)";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, evento.getNombre());
					ps.setInt(2, evento.getOlimpiada().getId());
					ps.setInt(3, evento.getDeporte().getId());
					
					ps.executeUpdate();
					
					ResultSet keys = ps.getGeneratedKeys();
					if (keys.first()) {
						evento.setId(keys.getInt(1));
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
	
	public static void modificarEvento (Evento evento) throws OlimpiadasException, SQLException {
		if (evento != null && evento.getId() > 0) {
			
			String sql = "UPDATE Evento SET "
					+ "nombre = ?, "
					+ "id_olimpiada = ?, "
					+ "id_deporte = ? "
					+ "WHERE id_evento = ?";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, evento.getNombre());
					ps.setInt(2, evento.getOlimpiada().getId());
					ps.setInt(3, evento.getDeporte().getId());
					ps.setInt(4, evento.getId());
					
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
	
	public static void borrarEvento(Evento evento) throws SQLException, OlimpiadasException {
		if (evento != null && evento.getId() > 0) {			
			String sql = "DELETE FROM Evento WHERE id_evento = ?";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, evento.getId());
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
