package br.com.fundatec.ExemploApis.api.v1.dto;

public class ErroDto {

	private String mensagem;
	
	
	public ErroDto() {
		
	}
	
	public ErroDto(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
