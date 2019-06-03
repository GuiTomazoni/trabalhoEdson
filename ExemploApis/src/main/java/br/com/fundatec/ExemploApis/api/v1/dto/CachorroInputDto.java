package br.com.fundatec.ExemploApis.api.v1.dto;

import javax.validation.constraints.NotBlank;

public class CachorroInputDto {

	@NotBlank(message = "O campo nome deve ser preenchido")
	private String nome;
	private String raca;
	private String porte;
	private Integer idade;
	
	
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
	
	
}
