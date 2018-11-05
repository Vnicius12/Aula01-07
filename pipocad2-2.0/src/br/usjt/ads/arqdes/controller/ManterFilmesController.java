package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;

/**
 * Servlet implementation class ManterFilmesController
 */
@WebServlet("/manterfilmes.do")
public class ManterFilmesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String acao = request.getParameter("acao");
		RequestDispatcher dispatcher;
		FilmeService fService;
		GeneroService gService;
		Filme filme;
		HttpSession session;

		String titulo = request.getParameter("titulo");
		String descricao = request.getParameter("descricao");
		String diretor = request.getParameter("diretor");
		String posterPath = request.getParameter("posterPath");
		String popularidade = request.getParameter("popularidade") == null
				|| request.getParameter("popularidade").length() == 0 ? "0" : request.getParameter("popularidade");
		String dataLancamento = request.getParameter("dataLancamento") == null
				|| request.getParameter("dataLancamento").length() == 0 ? "" : request.getParameter("dataLancamento");
		String idGenero = request.getParameter("genero.id");
		String chave = request.getParameter("data[search]");
		int[][] ratingRanges = new int[][] { { 81, 100 }, { 61, 80 }, { 41, 60 }, { 21, 40 }, { 0, 20 } };

		switch (acao) {
		case "novo":
			gService = new GeneroService();
			ArrayList<Genero> generos = gService.listarGeneros();
			session = request.getSession();
			session.setAttribute("generos", generos);
			dispatcher = request.getRequestDispatcher("CriarFilme.jsp");
			dispatcher.forward(request, response);
			break;

		case "visualizar":
			fService = new FilmeService();
			Filme vFilme = fService.buscarFilme(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("filme", vFilme);
			dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "criar":
			fService = new FilmeService();
			filme = new Filme();
			filme.setTitulo(titulo);
			filme.setDescricao(descricao);
			filme.setDiretor(diretor);

			gService = new GeneroService();
			Genero genero = new Genero();
			genero.setId(Integer.parseInt(idGenero));
			genero.setNome(gService.buscarGenero(genero.getId()).getNome());
			filme.setGenero(genero);

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				filme.setDataLancamento(formatter.parse(dataLancamento));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				filme.setDataLancamento(null);
			}

			filme.setPopularidade(Double.parseDouble(popularidade));
			filme.setPosterPath(posterPath);

			filme = fService.inserirFilme(filme);

			request.setAttribute("filme", filme);

			dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "editar":
			fService = new FilmeService();
			gService = new GeneroService();
			filme = fService.buscarFilme(Integer.parseInt(request.getParameter("id")));
			ArrayList<Genero> eGeneros = gService.listarGeneros();
			session = request.getSession();
			session.setAttribute("filme", filme);
			session.setAttribute("generos", eGeneros);
			dispatcher = request.getRequestDispatcher("AlterarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "alterar":
			int id = Integer.parseInt(request.getParameter("id"));
			fService = new FilmeService();
			filme = new Filme();
			filme.setId(id);
			filme.setTitulo(titulo);
			filme.setDescricao(descricao);
			filme.setDiretor(diretor);

			gService = new GeneroService();
			Genero aGenero = new Genero();
			aGenero.setId(Integer.parseInt(idGenero));
			aGenero.setNome(gService.buscarGenero(aGenero.getId()).getNome());
			filme.setGenero(aGenero);

			DateFormat aFormatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				filme.setDataLancamento(aFormatter.parse(dataLancamento));
			} catch (ParseException e) {
				e.printStackTrace();
				filme.setDataLancamento(null);
			}

			filme.setPopularidade(Double.parseDouble(popularidade));
			filme.setPosterPath(posterPath);

			filme = fService.alterarFilme(filme);

			request.setAttribute("filme", filme);

			dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "excluir":
			fService = new FilmeService();
			session = request.getSession();
			fService.excluirFilme(Integer.parseInt(request.getParameter("id")));
			dispatcher = request.getRequestDispatcher("manterfilmes.do?acao=listar");
			dispatcher.forward(request, response);
			break;
		case "reiniciar":
			session = request.getSession();
			session.setAttribute("lista", null);
			dispatcher = request.getRequestDispatcher("ListarFilmes.jsp");
			dispatcher.forward(request, response);
			break;
		case "listar_genero":
			session = request.getSession();
			fService = new FilmeService();
			gService = new GeneroService();
			ArrayList<Genero> glista = gService.buscarGenerosFilmes();
			ArrayList<Filme> flista = fService.listarFilmes();
			session.setAttribute("glista", glista);
			session.setAttribute("flista", flista);
			dispatcher = request.getRequestDispatcher("ListarFilmesGenero.jsp");
			dispatcher.forward(request, response);
			break;
		case "listar_popularidade":
			session = request.getSession();
			fService = new FilmeService();
			ArrayList<Filme> listap = fService.listarFilmes();
			session.setAttribute("flista", listap);
			session.setAttribute("ratings", getRatings(listap, ratingRanges));
			session.setAttribute("ranges", ratingRanges);
			dispatcher = request.getRequestDispatcher("ListarFilmesPopularidade.jsp");
			dispatcher.forward(request, response);
			break;
		case "listar_lancamento":
			session = request.getSession();
			fService = new FilmeService();
			ArrayList<Filme> listafd = fService.listarFilmesData();
			ArrayList<ArrayList<Filme>> listas = new ArrayList<ArrayList<Filme>>();
			listas = getLancamentos(listafd);
			session.setAttribute("listas", listas);
			dispatcher = request.getRequestDispatcher("ListarFilmesLancamento.jsp");
			dispatcher.forward(request, response);
			break;
		case "listar":
			session = request.getSession();
			fService = new FilmeService();
			ArrayList<Filme> lista;
			if (chave != null && chave.length() > 0) {
				lista = fService.listarFilmes(chave);
			} else {
				lista = fService.listarFilmes();
			}
			session.setAttribute("lista", lista);
			dispatcher = request.getRequestDispatcher("ListarFilmes.jsp");
			dispatcher.forward(request, response);
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected int[] getRatings(ArrayList<Filme> lista, int[][] r) {
		int[] res = new int[] { 0, 0, 0, 0, 0 };
		int k = 0;

		for (int[] i : r) {
			if (containsRatingInBetween(lista, i[0], i[1])) {
				res[k] = 1;
			}
			k++;
		}

		return res;
	}

	protected boolean containsRatingInBetween(ArrayList<Filme> lista, double a, double b) {

		for (Filme f : lista) {
			if (f.getPopularidade() >= a && f.getPopularidade() <= b) {
				return true;
			}
		}

		return false;
	}

	protected ArrayList<ArrayList<Filme>> getLancamentos(ArrayList<Filme> lista) {
		ArrayList<ArrayList<Filme>> l = new ArrayList<ArrayList<Filme>>();
		l.add(new ArrayList<Filme>());
		Calendar cal = Calendar.getInstance();
		int mesAtual, mesFilme, anoFilme, anoAtual, contador = 0;
		mesAtual = cal.get(Calendar.MONTH);
		anoAtual = cal.get(Calendar.YEAR);
		ArrayList<Integer> anos = new ArrayList<Integer>();

		for (Filme f : lista) {
			cal.setTime(f.getDataLancamento());
			mesFilme = cal.get(Calendar.MONTH);
			anoFilme = cal.get(Calendar.YEAR);
			if (mesAtual == mesFilme && anoFilme == anoAtual) {
				l.get(contador).add(f);
			}

		}

		contador++;

		for (Filme f : lista) {
			cal.setTime(f.getDataLancamento());
			anoFilme = cal.get(Calendar.YEAR);

			if (!anos.contains(anoFilme)) {
				anos.add(anoFilme);
				l.add(new ArrayList<Filme>());
				contador++;
			}

			l.get(contador - 1).add(f);

		}

		return l;
	}

}
