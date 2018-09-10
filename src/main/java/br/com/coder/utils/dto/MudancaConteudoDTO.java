/**
 * 
 */
package br.com.coder.utils.dto;

public class MudancaConteudoDTO {

	private Long valorIdRegistro;
	private String noCampo;
	private String coAntes;
	private String coDepois;
	
	
	public MudancaConteudoDTO() {
		
	}
	
	public MudancaConteudoDTO(String noCampo, String coAntes, String coDepois) {
		super();
		this.noCampo = noCampo;
		this.coAntes = coAntes;
		this.coDepois = coDepois;
	}


	public String getNoCampo() {
		return noCampo;
	}

	public void setNoCampo(String noCampo) {
		this.noCampo = noCampo;
	}

	public String getCoAntes() {
		return coAntes;
	}
	
	public void setCoAntes(String coAntes) {
		this.coAntes = coAntes;
	}
	
	public String getCoDepois() {
		return coDepois;
	}
	
	public void setCoDepois(String coDepois) {
		this.coDepois = coDepois;
	}

	public Long getValorIdRegistro() {
		return valorIdRegistro;
	}

	public void setValorIdRegistro(Long id) {
		this.valorIdRegistro = id;
	}

	@Override
	public String toString() {
		return "MudancaConteudo [" + (valorIdRegistro != null ? "valorIdRegistro=" + valorIdRegistro + ", " : "")
				+ (noCampo != null ? "noCampo=" + noCampo + ", " : "")
				+ (coAntes != null ? "coAntes=" + coAntes + ", " : "")
				+ (coDepois != null ? "coDepois=" + coDepois : "") + "]";
	}
	
}
