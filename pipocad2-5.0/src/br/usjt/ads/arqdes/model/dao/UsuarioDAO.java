package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;


import br.usjt.ads.arqdes.model.entity.Usuario;

@Repository
public class UsuarioDAO {
	
	@PersistenceContext
	EntityManager manager;
	
	public Usuario fazerLogin(Usuario usuario) throws IOException {
		Query query = manager.createQuery("select u from Usuario where usuario = :usuario and senha = :senha");
		query.setParameter("usuario", usuario.getUsuario());
		query.setParameter("senha", usuario.getSenha());
		return (Usuario) query.getSingleResult();
	}
}
