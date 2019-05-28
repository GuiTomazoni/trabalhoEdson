package br.com.fundatec.ExemploApis.api.v1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fundatec.ExemploApis.api.v1.dto.CachorroDto;
import br.com.fundatec.ExemploApis.entity.Cachorro;
import br.com.fundatec.ExemploApis.service.CachorroService;

@RestController
public class CachorroController {
	
	private CachorroService cachorroService;
	
	public CachorroController(CachorroService cachorroService) {
		super();
		this.cachorroService = cachorroService;
	}

	@GetMapping("/v1/cachorros")
	public ResponseEntity<List<CachorroDto>> getCachorros(){
		
//		List<CachorroDto> listaCachorro = new ArrayList<>();
//		listaCachorro.add(new CachorroDto("Bob", "Poodle", "Médio", 15 ));
//		listaCachorro.add(new CachorroDto("Goku", "Vira-lata", "Grande", 3 ));
//		listaCachorro.add(new CachorroDto("Rex", "Pitbull", "Grande", 4 ));
//		listaCachorro.add(new CachorroDto("Bilu", "Salsichinha", "Pequeno", 2 ));
//		listaCachorro.add(new CachorroDto("Amarelo", "Golden Retriever", "Grande", 1 ));
		
		List<CachorroDto> listaCachorroDto = new ArrayList<>();
		List<Cachorro> listaCachorro = cachorroService.listarTodos();
		
		for(Cachorro cachorro : listaCachorro) {
			listaCachorroDto.add(new CachorroDto(cachorro.getNome(), cachorro.getRaca(), cachorro.getPorte(), cachorro.getIdade()));
		}
		return ResponseEntity.status(HttpStatus.OK).body(listaCachorroDto);
		
		
		
	}
}
