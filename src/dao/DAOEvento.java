package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.AnimalesException;
import model.Evento;


public class DAOEvento extends DAOBase{
	
	public static Evento getEvento(int id) throws AnimalesException {
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
				throw new AnimalesException(e);
			}
		}
		return null;
	}
	
	public static void anadirEvento(Evento evento) throws AnimalesException, SQLException {
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
				throw new AnimalesException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new AnimalesException("Los datos introducidos están incompletos");
		}
	}
	
	public static void modificarEvento (Evento evento) throws AnimalesException, SQLException, IOException {
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
				throw new AnimalesException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new AnimalesException("Los datos introducidos están incompletos");
		}
	}
	
	public static void borrarEvento(Evento evento) throws SQLException, AnimalesException {
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
				throw new AnimalesException(e);
			} finally {
				con.close();
			}
		}
		
	}
	
	
}
