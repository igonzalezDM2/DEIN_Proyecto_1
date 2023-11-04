package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.AnimalesException;
import model.Deporte;


public class DAODeporte extends DAOBase{
	
	public static Deporte getDeporte(int id) throws AnimalesException {
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
				throw new AnimalesException(e);
			}
		}
		return null;
	}
	
	public static void anadirDeporte(Deporte deporte) throws AnimalesException, SQLException {
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
				throw new AnimalesException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new AnimalesException("Los datos introducidos están incompletos");
		}
	}
	
	public static void modificarDeporte (Deporte deporte) throws AnimalesException, SQLException, IOException {
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
				throw new AnimalesException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new AnimalesException("Los datos introducidos están incompletos");
		}
	}
	
	public static void borrarDeporte(Deporte deporte) throws SQLException, AnimalesException {
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
				throw new AnimalesException(e);
			} finally {
				con.close();
			}
		}
		
	}
	
	
}
