package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.OlimpiadasException;
import model.Deportista;


public class DAODeportista extends DAOBase{
	
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
	
	public static void anadirDeportista(Deportista deportista) throws OlimpiadasException, SQLException, IOException {
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
	
	public static void modificarDeportista(Deportista deportista) throws OlimpiadasException, SQLException, IOException {
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
