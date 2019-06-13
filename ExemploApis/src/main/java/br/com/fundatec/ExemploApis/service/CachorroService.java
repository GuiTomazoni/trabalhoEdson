package br.com.fundatec.ExemploApis.service;

import java.util.List;

import javax.management.RuntimeErrorException;

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

	public Cachorro salvar(Cachorro cachorro) {
		validarSalvarCachorro(cachorro);
		return cachorroRepository.save(cachorro);
	}

	public void validarSalvarCachorro(Cachorro cachorro) {
		validarPorte(cachorro);
		validarPorteRaca(cachorro);
	}

	private void validarPorteRaca(Cachorro cachorro) {
		if (!porteParametroService.racasComPortesDefinidos(cachorro.getRaca(), cachorro.getPorte())) {
			throw new IllegalArgumentException("N�o existem ra�as para este porte");
		}

	}

	public void validarPorte(Cachorro cachorro) {
		if (!porteParametroService.porteValido(cachorro.getPorte())) {
			throw new IllegalArgumentException("Porte inv�lido. Porte deve ser Pequeno, M�dio ou Grande");
		}
	}

	public Cachorro consultar(Long id) {
		return cachorroRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("N�o encontrou cachorro para o id: " + id));
	}

	public void excluir(Long id) {
		if (cachorroRepository.existsById(id)) {
			cachorroRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("N�o existe cachorro com este id: " + id);
		}
	}
}
