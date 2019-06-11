package br.com.fundatec.ExemploApis.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public boolean racasComPortesDefinidos(String raca, String porte) {
		Map<String, List<String>> mapaPorteRaca = new HashMap<>();
		mapaPorteRaca.put("Pequeno", Arrays.asList("Poodle", "Chuaua", "Vira-Latas", "Beagle"));
		mapaPorteRaca.put("Médio", Arrays.asList("Chow chow", "Vira-Latas"));
		mapaPorteRaca.put("Grande", Arrays.asList("Pastor Belga", "Vira-Latas"));
		
		List<String> racas = mapaPorteRaca.get(porte);
		
		if(racas == null) {
			throw new IllegalArgumentException("Não existem raças para este porte");
		}
		
		return racas.contains(raca);		
	}

}
