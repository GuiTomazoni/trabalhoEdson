package br.com.fundatec.ExemploApis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fundatec.ExemploApis.entity.Cachorro;
import br.com.fundatec.ExemploApis.repository.CachorroRepository;

@Service
public class CachorroService {

	private CachorroRepository cachorroRepository;
	private PorteParametroService porteParametroService;

	public CachorroService(CachorroRepository cachorroRepository, PorteParametroService porteParametroService) {
		this.cachorroRepository = cachorroRepository;
		this.porteParametroService = porteParametroService;
	}

	public List<Cachorro> listarTodos() {
		return (List<Cachorro>) cachorroRepository.findAll();
	}

	public Cachorro incluir(Cachorro cachorro) {
		validarSalvarCachorro(cachorro);
		return cachorroRepository.save(cachorro);
	}

	public void validarSalvarCachorro(Cachorro cachorro) {
		validarPorte(cachorro);
	}

	public void validarPorte(Cachorro cachorro) {
		if (!porteParametroService.porteValido(cachorro.getPorte())) {
			throw new IllegalArgumentException("Porte inválido. Porte deve ser Pequeno, Médio ou Grande");
		}
	}
}
