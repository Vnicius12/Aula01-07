package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.usjt.ads.arqdes.model.entity.Filme;
@Repository
public class FilmeDAO {
	
	@PersistenceContext
	EntityManager manager;
	
	public int inserirFilme(Filme filme) throws IOException {
		manager.persist(filme);
		return filme.getId();
	}

	public Filme buscarFilme(int id) throws IOException{
		return manager.find(Filme.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Filme> listarFilmes(String chave) throws IOException {
		Query query = manager.createQuery("select f from Filme f where f.nome like :chave");
		query.setParameter("chave", "%"+chave+"%");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Filme> listarFilmes() throws IOException {
		Query query = manager.createQuery("select f from Filme f");
		return query.getResultList();
	}

}
