/**
 * 
 */
package br.com.coder.utils.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	private String nome;
	private Date nascimento;
	private Sexo sexo;
	private Pessoa pai;
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getNascimento() {
		return this.nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	@Column(name="co_id")
	public Long getId() {
		return id;
	}
	@AnotacaoSetter
	public void setId(Long id) {
		this.id = id;
	}
	public void setPai(Pessoa pai) {
		this.pai = pai;
	}
	public Pessoa getPai() {
		return this.pai;
	}



}
