package br.com.fundatec.ExemploApis.service;

import org.springframework.stereotype.Service;

import br.com.fundatec.ExemploApis.entity.Pessoa;
import br.com.fundatec.ExemploApis.repository.PessoaRepository;

@Service
public class PessoaService {
	
	private PessoaRepository pessoaRepository;

	public PessoaService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}
	
	public Pessoa consultar(Long id) {
		return pessoaRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Não encontrou pessoa para o id: " + id));
	}
	
}
