package br.com.fundatec.ExemploApis.api.v1.dto;

public class CachorroDto {
	
	private String nome;
	private String raca;
	private String porte;
	private int idade;
	
	public CachorroDto() {
		
	}
	
	public CachorroDto(String nome, String raca, String porte, int idade) {
		this.nome = nome;
		this.raca = raca;
		this.porte = porte;
		this.idade = idade;
	}

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

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	

}
