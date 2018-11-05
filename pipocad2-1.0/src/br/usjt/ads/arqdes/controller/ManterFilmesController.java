package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String acao = request.getParameter("acao");
		RequestDispatcher dispatcher;
		FilmeService fService;
		GeneroService gService;
		Filme filme;
		
		switch (acao) {
		case "inserir":
			fService = new FilmeService();
			filme = new Filme();
			filme.setTitulo("O Náufrago");
			filme.setDescricao("Chuck Noland (Tom Hanks) um inspetor da Federal Express (FedEx), "
					+ "multinacional encarregada de enviar cargas e correspondências, que tem por "
					+ "função checar vários escritórios da empresa pelo planeta. Porém, em uma de "
					+ "suas costumeiras viagens ocorre um acidente, que o deixa preso em uma ilha "
					+ "completamente deserta por 4 anos. Com sua noiva (Helen Hunt) e seus amigos "
					+ "imaginando que ele morrera no acidente, Chuck precisa lutar para sobreviver, "
					+ "tanto fisicamente quanto emocionalmente, a fim de que um dia consiga retornar "
					+ "civilização.");
			filme.setDiretor("Robert Zemeckis");

			Genero genero = new Genero();
			genero.setId(12);
			genero.setNome("Aventura");
			filme.setGenero(genero);

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				filme.setDataLancamento(formatter.parse("26/01/2001"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				filme.setDataLancamento(null);
			}

			filme.setPopularidade(100.25);
			filme.setPosterPath("img/naufrago.jpg");

			filme = fService.inserirFilme(filme);

			request.setAttribute("filme", filme);

			dispatcher = request.getRequestDispatcher("MostrarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "generos":
			gService = new GeneroService();
			ArrayList<Genero> generos = gService.listarGeneros();
			request.setAttribute("generos", generos);

			dispatcher = request.getRequestDispatcher("MostrarGeneros.jsp");
			dispatcher.forward(request, response);
			break;
		case "buscar":
			int id = -1;
			
			if(!request.getParameter("id").isEmpty()) {
				String pId = request.getParameter("id");
				id = Integer.parseInt(pId);
			}
			
			fService = new FilmeService();
			filme = fService.buscarFilme(id);
			request.setAttribute("filme", filme);

			dispatcher = request.getRequestDispatcher("MostrarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
