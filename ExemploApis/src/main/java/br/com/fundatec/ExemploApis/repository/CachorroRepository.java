package br.com.fundatec.ExemploApis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fundatec.ExemploApis.entity.Cachorro;

@Repository
public interface CachorroRepository extends CrudRepository<Cachorro, Long>  {
	
	

}
