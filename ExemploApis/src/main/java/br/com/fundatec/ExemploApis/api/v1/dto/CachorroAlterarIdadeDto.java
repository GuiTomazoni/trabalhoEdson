package br.com.fundatec.ExemploApis.api.v1.dto;

import javax.validation.constraints.PositiveOrZero;

public class CachorroAlterarIdadeDto {

	@PositiveOrZero(message = "Idade deve ser maior ou igual a zero")
	private Integer idade;

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	
}
