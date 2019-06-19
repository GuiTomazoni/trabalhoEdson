package br.com.fundatec.ExemploApis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fundatec.ExemploApis.entity.Cachorro;

@Repository
public interface CachorroRepository extends CrudRepository<Cachorro, Long>  {
	
	List<Cachorro> findByNomeContainingIgnoringCase(String nome);
	

}
