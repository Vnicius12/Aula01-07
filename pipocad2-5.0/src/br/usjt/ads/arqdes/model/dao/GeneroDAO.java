package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.usjt.ads.arqdes.model.entity.Genero;

@Repository
public class GeneroDAO {
	
	@PersistenceContext
	EntityManager manager;

	public Genero buscarGenero(int id) throws IOException {
		return manager.find(Genero.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Genero> listarGeneros() throws IOException {
		Query query = manager.createQuery("select g from Genero");
		return query.getResultList();
	}
}
