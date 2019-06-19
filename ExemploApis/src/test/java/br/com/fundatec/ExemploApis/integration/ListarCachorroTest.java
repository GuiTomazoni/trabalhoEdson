package br.com.fundatec.ExemploApis.integration;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fundatec.ExemploApis.api.v1.dto.CachorroOutputDto;
import br.com.fundatec.ExemploApis.entity.Cachorro;
import br.com.fundatec.ExemploApis.repository.CachorroRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListarCachorroTest {
	@LocalServerPort
	private int port;
	
	@Autowired
	private CachorroRepository cachorroRepository;
	
	@Before
	public void setUp() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
		cachorroRepository.deleteAll();
	}
	
	@Test
	public void deveBuscarUmaListaDeCachorros() {
		cachorroRepository.save(new Cachorro(null, "Bob", "Poodle", "Médio", 15));
		cachorroRepository.save(new Cachorro(null, "Rex", "Pitbull", "Grande", 3));
		cachorroRepository.save(new Cachorro(null, "Roberto", "chihuahua", "Pequeno", 10));
		RestAssured
		.given()
		.when()
		.get("/v1/cachorros")
		.then()
		.body("nome", Matchers.hasItems("Bob"))
		.body("raca", Matchers.hasItems("Poodle"))
		.body("porte", Matchers.hasItems("Médio"))
		.body("idade", Matchers.hasItems(15))
		.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveListarCachorrosFiltrandoPorNome() {
		cachorroRepository.save(new Cachorro(null, "Bob", "Poodle", "Médio", 15));
		cachorroRepository.save(new Cachorro(null, "Rex", "Pitbull", "Grande", 3));
		cachorroRepository.save(new Cachorro(null, "Roberto", "Chihuahua", "Pequeno", 10));
		
		CachorroOutputDto[] resultado = RestAssured
		.given()
		.when()
		.get("/v1/cachorros?nome=ob")
		.then()
		.assertThat()
		.statusCode(HttpStatus.OK.value())
		.extract()
		.as(CachorroOutputDto[].class);
		
		List<String> nomesEsperados = Arrays.asList("Bob", "Roberto");
		List<String> racasEsperados = Arrays.asList("Poodle", "Chihuahua");
		List<String> portesEsperados = Arrays.asList("Médio", "Pequeno");
		List<Integer> idadesEsperados = Arrays.asList(15, 10);
		
		Assert.assertEquals(resultado.length, 2);
		
		for (CachorroOutputDto cachorroOutputDto : resultado) {
			Assert.assertTrue(nomesEsperados.contains(cachorroOutputDto.getNome()));
			Assert.assertTrue(racasEsperados.contains(cachorroOutputDto.getRaca()));
			Assert.assertTrue(portesEsperados.contains(cachorroOutputDto.getPorte()));
			Assert.assertTrue(idadesEsperados.contains(cachorroOutputDto.getIdade()));
		}
	}
}
