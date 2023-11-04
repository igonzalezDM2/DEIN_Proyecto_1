package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.AnimalesException;
import model.Olimpiada;


public class DAOOlimpiada extends DAOBase{
	
	public static Olimpiada getOlimpiada(int id) throws AnimalesException {
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
				throw new AnimalesException(e);
			}
		}
		return null;
	}
	
	public static void anadirOlimpiada(Olimpiada olimpiada) throws AnimalesException, SQLException {
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
				throw new AnimalesException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new AnimalesException("Los datos introducidos están incompletos");
		}
	}
	
	public static void modificarOlimpiada(Olimpiada olimpiada) throws AnimalesException, SQLException, IOException {
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
				throw new AnimalesException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new AnimalesException("Los datos introducidos están incompletos");
		}
	}
	
	public static void borrarOlimpiada(Olimpiada olimpiada) throws SQLException, AnimalesException {
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
				throw new AnimalesException(e);
			} finally {
				con.close();
			}
		}
		
	}
	
	
}