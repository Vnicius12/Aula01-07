package br.usjt.ads.arqdes.model.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class Filme {
	private int id;
	@NotNull(message="Título em branco")
	@Size(min=2, max=100, message="Use titulos entre 2 a 100 caracteres")
	private String titulo;
	@NotNull 
	@Size(min=1, max=4000, message="Descrição entre 1 a 4000 caracteres")
	private String descricao;
	private double popularidade;
	@NotNull 
	@Past(message="Data está no futuro")
	private Date dataLancamento;
	@NotNull 
	@Size(min=1, max=200, message="Poster entre 1 a 200 caracteres")
	private String posterPath;
	@NotNull 
	@Size(min=1, max=60, message="Diretor entre 1 a 60 caracteres")
	private String diretor;
	private Genero genero;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getPopularidade() {
		return popularidade;
	}
	public void setPopularidade(double popularidade) {
		this.popularidade = popularidade;
	}
	public Date getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public String getPosterPath() {
		return posterPath;
	}
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	public String getDiretor() {
		return diretor;
	}
	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	@Override
	public String toString() {
		return "Filme [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", popularidade=" + popularidade
				+ ", dataLancamento=" + dataLancamento + ", posterPath=" + posterPath + ", diretor=" + diretor
				+ ", genero=" + genero + "]";
	}

}
