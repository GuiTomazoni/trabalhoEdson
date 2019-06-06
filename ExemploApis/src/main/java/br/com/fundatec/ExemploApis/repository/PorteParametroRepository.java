package br.com.fundatec.ExemploApis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fundatec.ExemploApis.entity.PorteParametro;

@Repository
public interface PorteParametroRepository extends CrudRepository<PorteParametro, Long> {

}
