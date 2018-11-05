package br.usjt.ads.arqdes.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.ads.arqdes.model.dao.FilmeDAO;
import br.usjt.ads.arqdes.model.entity.Filme;

@Service
public class FilmeService {
	@Autowired
	private FilmeDAO dao;
	
	
	public Filme buscarFilme(int id) throws IOException{
		return dao.buscarFilme(id);
	}
	
	public Filme inserirFilme(Filme filme) throws IOException {
		int id = dao.inserirFilme(filme);
		filme.setId(id);
		return filme;
	}

	public ArrayList<Filme> listarFilmes(String chave) throws IOException{
		return dao.listarFilmes(chave);
	}

	public ArrayList<Filme> listarFilmes() throws IOException{
		return dao.listarFilmes();
	}

	public void excluirFilme(int id) throws IOException {
		dao.excluirFilme(id);
	}
	
	public Filme alterarFilme(Filme filme) throws IOException {
		int id = dao.alterarFilme(filme);
		filme.setId(id);
		return filme;
	}
	
	public ArrayList<Filme> listarFilmesData() throws IOException{
		return dao.listarFilmesData();
	}
	
	public int[] getRatings(ArrayList<Filme> lista, int[][] r) {
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

	public boolean containsRatingInBetween(ArrayList<Filme> lista, double a, double b) {

		for (Filme f : lista) {
			if (f.getPopularidade() >= a && f.getPopularidade() <= b) {
				return true;
			}
		}

		return false;
	}
	
	public ArrayList<ArrayList<Filme>> getLancamentos(ArrayList<Filme> lista) {
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
