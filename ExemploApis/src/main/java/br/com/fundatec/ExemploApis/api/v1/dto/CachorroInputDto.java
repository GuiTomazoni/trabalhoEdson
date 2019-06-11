package br.com.fundatec.ExemploApis.api.v1.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class CachorroInputDto {

	@NotBlank(message = "O campo nome deve ser preenchido")
	private String nome;
	private String raca;
	private String porte;
	@PositiveOrZero(message = "A idade deve ser maior ou igual a zero")
	private Integer idade;
	@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Campo cpc inválido")
	private String cpc;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	public String getPorte() {
		return porte;
	}
	public void setPorte(String porte) {
		this.porte = porte;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	public String getCpc() {
		return cpc;
	}
	public void setCpc(String cpc) {
		this.cpc = cpc;
	}
	
}
