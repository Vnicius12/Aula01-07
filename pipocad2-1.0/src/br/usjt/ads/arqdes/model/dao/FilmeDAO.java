package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;

public class FilmeDAO {
	
	public int inserirFilme(Filme filme) throws IOException {
		int id = -1;
		String sql = "INSERT INTO filme (titulo, descricao, diretor, posterpath, "
				+ "popularidade, data_lancamento, id_genero) VALUES (?,?,?,?,?,?,?)";
		
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);){
			
			pst.setString(1, filme.getTitulo());
			pst.setString(2, filme.getDescricao());
			pst.setString(3, filme.getDiretor());
			pst.setString(4, filme.getPosterPath());
			pst.setDouble(5, filme.getPopularidade());
			pst.setDate(6, new java.sql.Date(filme.getDataLancamento().getTime()));
			pst.setInt(7, filme.getGenero().getId());			
			pst.execute();
			
			//obter o id criado
			String query = "SELECT LAST_INSERT_ID()";
			try(PreparedStatement pst1 = conn.prepareStatement(query);
				ResultSet rs = pst1.executeQuery();){

				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return id;
	}

	public Filme buscarFilme(int id) throws IOException{
		Filme filme = new Filme();
		String comando = "SELECT titulo, descricao, diretor, posterpath, popularidade, data_lancamento, id_genero FROM filme WHERE id=?";
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(comando);){
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				filme.setId(id);
				filme.setTitulo(rs.getString("titulo"));
				filme.setDescricao(rs.getString("descricao"));
				filme.setDiretor(rs.getString("diretor"));
				filme.setPosterPath(rs.getString("posterpath"));
				filme.setPopularidade(rs.getDouble("popularidade"));
				filme.setDataLancamento(rs.getDate("data_lancamento"));
				filme.setGenero(new Genero());
				filme.getGenero().setId(rs.getInt("id_genero"));
			} else {
				filme.setId(-1);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		
		return filme;
	}
	
	public int excluirFilme(int idFilme) throws IOException {
		  int id = -1;
	      String comando = "DELETE FROM filme WHERE id=?";
	      
	      try(Connection conn = ConnectionFactory.getConnection();
	    		  PreparedStatement pst = conn.prepareStatement(comando);){
	    	  pst.setInt(1, idFilme);
	    	  pst.execute();
	    	  id = idFilme;
	      }
	      catch(SQLException e){
	    	  e.printStackTrace();
	    	  throw new IOException(e);
	      }
	      
	      return id;
	 }
	
	public int atualizarFilme(Filme filme) throws IOException {
		int id = -1;
		String comando = "UPDATE filme SET titulo = ?, descricao = ?, diretor = ?, posterpath = ?, popularidade = ?, data_lancamento = ?, id_genero = ? WHERE id=?";
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(comando);){
			
			pst.setString(1, filme.getTitulo());
			pst.setString(2, filme.getDescricao());
			pst.setString(3, filme.getDiretor());
			pst.setString(4, filme.getPosterPath());
			pst.setDouble(5, filme.getPopularidade());
			pst.setObject(6, filme.getDataLancamento());
			pst.setInt(7, filme.getGenero().getId());
			pst.setInt(8, filme.getId());
			pst.execute();
			id = filme.getId();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		
		return id;
	}
	
	public ArrayList<Filme> buscarFilmes() throws IOException {
		ArrayList<Filme> filmes = new ArrayList<Filme>();
		
		String comando = "SELECT id = ?, titulo = ?, descricao = ?, diretor = ?, posterpath = ?, popularidade = ?, data_lancamento = ?, id_genero = ? FROM filme";
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(comando);
				ResultSet rs = pst.executeQuery();){
			
			while(rs.next()) {
				Filme filme = new Filme();
				filme.setId(rs.getInt("id"));
				filme.setTitulo(rs.getString("titulo"));
				filme.setDescricao(rs.getString("descricao"));
				filme.setDiretor(rs.getString("diretor"));
				filme.setPosterPath(rs.getString("posterpath"));
				filme.setPopularidade(rs.getDouble("popularidade"));
				filme.setDataLancamento(rs.getDate("data_lancamento"));
				filme.setGenero(new Genero());
				filme.getGenero().setId(rs.getInt("id_genero"));
				filmes.add(filme);
			}	
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		
		return filmes;
	}

}
