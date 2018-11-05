package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;

@Controller
public class ManterFilmesController {
	@Autowired
	private FilmeService fService;
	@Autowired
	private GeneroService gService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
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
	public String inserirFilme(@Valid Filme filme, BindingResult result, Model model) {
		try {
			if (!result.hasFieldErrors()) {
				Genero genero = gService.buscarGenero(filme.getGenero().getId());
				filme.setGenero(genero);
				model.addAttribute("filme", filme);
				fService.inserirFilme(filme);
				return "VisualizarFilme";
			}
			return "CriarFilme";
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	@RequestMapping("/buscar_filmes")
	public String buscarFilmes(HttpSession session, @RequestParam String chave) {
		try {
			ArrayList<Filme> lista;
			
			if(chave != null && chave.length() > 0)
				lista = fService.listarFilmes(chave);
			else
				lista = fService.listarFilmes();
			
			session.setAttribute("lista", lista);
			return "ListarFilmes";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/visualizar_filme")
	public String visualizarFilme(HttpSession session, @RequestParam String id) {
		try {
			Filme verFilme = fService.buscarFilme(Integer.parseInt(id));
			session.setAttribute("filme", verFilme);
			return "VisualizarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "ListarFilmes";
	}
	
	@RequestMapping("/excluir_filme")
	public String excluirFilme(Model model, @RequestParam String id) {
		try {
			fService.excluirFilme(Integer.parseInt(id));
			model.addAttribute("chave", "");
			return "redirect:buscar_filmes";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Erro";
	}
	
	@RequestMapping("/editar_filme")
	public String editarFilme(Model model, @RequestParam String id) {
		try {
			ArrayList<Genero> generos = gService.listarGeneros();
			model.addAttribute("generos", generos);
			model.addAttribute("filme", fService.buscarFilme(Integer.parseInt(id)));
			return "EditarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Erro";
	}
	
	@RequestMapping("/salvar_filme")
	public String salvarFilme(@Valid Filme filme, BindingResult result, Model model) {
		System.out.println(filme);
		try {
			if (!result.hasFieldErrors()) {
				Genero genero = gService.buscarGenero(filme.getGenero().getId());
				filme.setGenero(genero);
				model.addAttribute("filme", filme);
				fService.alterarFilme(filme);
				return "VisualizarFilme";
			}
			return "EditarFilme";
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	@RequestMapping("/listar_genero")
	public String listarGenero(Model model) {
		try {
			ArrayList<Genero> glista = gService.buscarGenerosFilmes();
			ArrayList<Filme> flista = fService.listarFilmes();
			model.addAttribute("glista", glista);
			model.addAttribute("flista", flista);
			return "ListarFilmesGenero";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Erro";
	}
	
	@RequestMapping("/listar_popularidade")
	public String listarPopularidade(Model model) {
		
		try {
			int[][] ratingRanges = new int[][] { { 81, 100 }, { 61, 80 }, { 41, 60 }, { 21, 40 }, { 0, 20 } };
			ArrayList<Filme> lista = fService.listarFilmes();
			model.addAttribute("flista", lista);
			model.addAttribute("ratings", fService.getRatings(lista, ratingRanges));
			model.addAttribute("ranges", ratingRanges);
			return "ListarFilmesPopularidade";
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Erro";
	}
	
	@RequestMapping("/listar_lancamento")
	public String listarLancamento(Model model) {
		
		try {
			ArrayList<Filme> lista = fService.listarFilmes();
			ArrayList<ArrayList<Filme>> listas = new ArrayList<ArrayList<Filme>>();
			listas = fService.getLancamentos(lista);
			model.addAttribute("listas", listas);
			return "ListarFilmesLancamento";
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Erro";
	}
	
}

















