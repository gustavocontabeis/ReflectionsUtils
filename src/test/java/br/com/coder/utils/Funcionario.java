/**
 * 
 */
package br.com.coder.utils;

public class Funcionario extends Pessoa{

	private static final long serialVersionUID = 1L;
	
	@AnotacaoField(value="ok")
	private Integer ctps;
	
	@AnotacaoField(value="ok")
	public Integer ctps2;

	public Integer getCtps() {
		return ctps;
	}

	public void setCtps(Integer ctps) {
		this.ctps = ctps;
	}

}
