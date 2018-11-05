package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.service.FilmeService;

@RestController
public class FilmesRest {
	@Autowired
	private FilmeService fService;
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="rest/inserir_filme")
	public ResponseEntity<Filme> criarFilme(@RequestBody Filme filme) {
		try {
			fService.inserirFilme(filme);
			return new ResponseEntity<Filme>(filme, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Filme>(filme, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="rest/buscar_filme")
	public @ResponseBody Filme buscarFilme(int id) {
		Filme filme = null;
		try {
			filme = fService.buscarFilme(id);
			return filme;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return filme;
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.PUT, value="rest/atualizar_filme")
	public ResponseEntity<Filme> atualizarFilme(@RequestBody Filme filme) {
		try {
			fService.atualizarFilme(filme);
			return new ResponseEntity<Filme>(filme, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Filme>(filme, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.DELETE, value="rest/deletar_filme")
	public ResponseEntity<Filme> deletarFilme(@RequestBody Filme filme) {
		try {
			fService.excluirFilme(filme.getId());
			return new ResponseEntity<Filme>(filme, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Filme>(filme, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="rest/filmes")
	public @ResponseBody List<Filme> listagem(String chave) {
		List<Filme> lista = null;
		try{
			if(chave == null || chave.equals("")){
				lista = fService.listarFilmes();
			} else {
				lista = fService.listarFilmes(chave);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		return lista;
	}
	
	
}
