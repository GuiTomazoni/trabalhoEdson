package br.com.fundatec.ExemploApis.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.fundatec.ExemploApis.api.v1.dto.CachorroInputDto;
import br.com.fundatec.ExemploApis.api.v1.dto.CachorroOutputDto;
import br.com.fundatec.ExemploApis.entity.Cachorro;

@Component
public class CachorroMapper {

	public CachorroOutputDto mapearCachorroOutputDto(Cachorro cachorro) {
		return new CachorroOutputDto(
				cachorro.getId(), 
				cachorro.getNome(), 
				cachorro.getRaca(), 
				cachorro.getPorte(), 
				cachorro.getIdade());
	}
	
	public Cachorro mapearCachorro(CachorroInputDto cachorroInputDto) {
		return new Cachorro (null, 
				cachorroInputDto.getNome(),
				cachorroInputDto.getRaca(),
				cachorroInputDto.getPorte(),
				cachorroInputDto.getIdade());
	}
	
	public List<CachorroOutputDto> mapearListaCachorroOutputDto(List<Cachorro> cachorros){
		List<CachorroOutputDto> listaCachorroDto = new ArrayList<>();
		for(Cachorro cachorro : cachorros) {
			listaCachorroDto.add(mapearCachorroOutputDto(cachorro));
		}
		return listaCachorroDto;
	}
}
