package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.entity.Usuario;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;
import br.usjt.ads.arqdes.model.service.UsuarioService;

@Controller
public class ManterFilmesController {
	@Autowired
	private FilmeService fService;
	@Autowired
	private GeneroService gService;
	@Autowired
	private UsuarioService uService;
	
	public ManterFilmesController() {
		fService = new FilmeService();
		gService = new GeneroService();
	}
	
	@RequestMapping("/")
	public String inicio() {
		return "index";
	}
	
	@RequestMapping("/inicio")
	public String inicio1() {
		return "index";
	}
	
	@RequestMapping("/listar_filmes")
	public String listarFilmes(HttpSession session){
		session.setAttribute("lista", null);
		return "ListarFilmes";
	}
	
	@RequestMapping("/novo_filme")
	public String novoFilme(HttpSession session) {
		try {
			ArrayList<Genero> generos = gService.listarGeneros();
			session.setAttribute("generos", generos);
			return "CriarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	@RequestMapping("/inserir_filme")
	public String inserirFilme(Filme filme, Model model) {
		try {
			Genero genero = gService.buscarGenero(filme.getGenero().getId());
			filme.setGenero(genero);
			model.addAttribute("filme", filme);
			fService.inserirFilme(filme);
			return "VisualizarFilme";
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}
	
	@RequestMapping("/tela_login")
	public String telaLogin() {
		return "TelaLogin";
	}
	
	@RequestMapping("/fazer_login")
	public String fazerLogin(Usuario usuario, HttpSession session) {
		try {
			usuario = uService.fazerLogin(usuario);
			session.setAttribute("usuario", usuario);
			return "redirect:/inicio";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "erro";
	}

}

















