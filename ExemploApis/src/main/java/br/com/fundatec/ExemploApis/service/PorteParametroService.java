package br.com.fundatec.ExemploApis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fundatec.ExemploApis.entity.PorteParametro;
import br.com.fundatec.ExemploApis.repository.PorteParametroRepository;

@Service
public class PorteParametroService {

	private PorteParametroRepository porteParametroRepository;

	public PorteParametroService(PorteParametroRepository porteParametroRepository) {
		this.porteParametroRepository = porteParametroRepository;
	}

	public boolean porteValido(String porte) {
		List<PorteParametro> listaPorteParametro = (List<PorteParametro>) porteParametroRepository.findAll();
		for (int i = 0; i < listaPorteParametro.size(); i++) {
			if (porte.equals(listaPorteParametro.get(i).getNome())) {
				return true;
			}
		}
		return false;
	}

}
