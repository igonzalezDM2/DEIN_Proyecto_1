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


public class DAOEquipo extends DAOBase{
	
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
