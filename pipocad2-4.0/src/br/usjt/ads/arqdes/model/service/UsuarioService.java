package br.usjt.ads.arqdes.model.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.ads.arqdes.model.dao.UsuarioDAO;
import br.usjt.ads.arqdes.model.entity.Usuario;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	public Usuario fazerLogin(Usuario usuario) throws IOException {
		return usuarioDAO.fazerLogin(usuario);
	}
}
