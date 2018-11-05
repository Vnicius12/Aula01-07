package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import br.usjt.ads.arqdes.model.entity.Usuario;

@Repository
public class UsuarioDAO {
	Connection conn;
	
	@Autowired
	public UsuarioDAO(DataSource data) throws IOException {
		try {
			conn = data.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	
	public Usuario fazerLogin(Usuario usuario) throws IOException {
		String sql = "select u from usuario u where u.usuario = ? and u.senha = ?";
		
		try(PreparedStatement pst = conn.prepareStatement(sql);){
			
			pst.setString(1, usuario.getUsuario());
			pst.setString(2, usuario.getSenha());
			
			try(ResultSet rs = pst.executeQuery()) {
				if(rs.next()) {
					return usuario;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return null;
	}
}
