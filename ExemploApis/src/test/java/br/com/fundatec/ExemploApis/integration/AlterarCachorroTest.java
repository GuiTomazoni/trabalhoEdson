package br.com.fundatec.ExemploApis.integration;

import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fundatec.ExemploApis.entity.Cachorro;
import br.com.fundatec.ExemploApis.entity.PorteParametro;
import br.com.fundatec.ExemploApis.repository.CachorroRepository;
import br.com.fundatec.ExemploApis.repository.PorteParametroRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlterarCachorroTest {
	
	@LocalServerPort
	private int port;
	@Autowired
	private CachorroRepository cachorroRepository;
	@Autowired
	private PorteParametroRepository porteParametroRepository; 
	
	private Cachorro cachorroParaAlterar;
	
	@Before
	public void setup() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
		cachorroRepository.deleteAll();
		porteParametroRepository.deleteAll();
		porteParametroRepository.save(new PorteParametro("Pequeno"));
		porteParametroRepository.save(new PorteParametro("Médio"));
		porteParametroRepository.save(new PorteParametro("Grande"));
		cachorroParaAlterar = new Cachorro(null, "Tobias", "Beagle", "Pequeno", 2);
		cachorroParaAlterar = cachorroRepository.save(cachorroParaAlterar);
	}
	
	@Test
	public void deveAlterarCachorro() {
		RestAssured
			.given()
			.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body("{" + 
					"	\"nome\": \"Tobias Ferreira\"," + 
					"	\"raca\": \"Chow chow\"," + 
					"	\"porte\": \"Médio\"," + 
					"	\"idade\": 6" + 
					"}")
			.when()
			.put("/v1/cachorros/{id}", cachorroParaAlterar.getId())
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK.value())
			.body("id", Matchers.equalTo(cachorroParaAlterar.getId().intValue()))
			.body("nome", Matchers.equalTo("Tobias Ferreira"))
			.body("raca", Matchers.equalTo("Chow chow"))
			.body("porte", Matchers.equalTo("Médio"))
			.body("idade", Matchers.equalTo(6));
		
		Cachorro cachorroAlterado = cachorroRepository.findById(cachorroParaAlterar.getId()).orElse(null);
		Assert.assertEquals("Tobias Ferreira", cachorroAlterado.getNome());
		Assert.assertEquals("Chow chow", cachorroAlterado.getRaca());
		Assert.assertEquals("Médio", cachorroAlterado.getPorte());
		Assert.assertEquals(6, cachorroAlterado.getIdade());
					
	}
	
	@Test
	public void deveValidarAoAlterarCachorroSemNome() {

		RestAssured
			.given()
			.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
			.body("{" + 
					"	\"raca\": \"Beagle\"," + 
					"	\"porte\": \"Pequeno\"," + 
					"	\"idade\": 2" + 
					"}")
			.when()
			.put("/v1/cachorros/{id}", cachorroParaAlterar.getId())
			.then()
			.assertThat()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("errors[0].defaultMessage", Matchers.equalTo("O campo nome deve ser preenchido"));
		
		Cachorro cachorroEstanciado = cachorroRepository.findById(cachorroParaAlterar.getId()).orElse(null);
		Assert.assertEquals("Tobias", cachorroEstanciado.getNome());
		Assert.assertEquals("Beagle", cachorroEstanciado.getRaca());
		Assert.assertEquals("Pequeno", cachorroEstanciado.getPorte());
		Assert.assertEquals(2, cachorroEstanciado.getIdade());
	}
	
	@Test
	public void deveValidarCpcInvalidoAoAlterar() {
		RestAssured
		.given()
		.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.body("{" +  
				"   \"nome\": \"Urso\"," +
				"	\"raca\": \"Pastor Belga\"," + 
				"	\"porte\": \"Grande\"," + 
				"	\"idade\": 2," +
				"   \"cpc\": \"cpc\" " +
				"}")
		.when()
		.put("/v1/cachorros/{id}", cachorroParaAlterar.getId())
		.then()
		.assertThat()
		.statusCode(HttpStatus.BAD_REQUEST.value())
		.body("errors[0].defaultMessage", Matchers.equalTo("Campo cpc inválido"));
		
		Cachorro cachorroEstanciado = cachorroRepository.findById(cachorroParaAlterar.getId()).orElse(null);
		Assert.assertEquals("Tobias", cachorroEstanciado.getNome());
		Assert.assertEquals("Beagle", cachorroEstanciado.getRaca());
		Assert.assertEquals("Pequeno", cachorroEstanciado.getPorte());
		Assert.assertEquals(2, cachorroEstanciado.getIdade());					
	}	
	
	@Test
	public void deveValidarPorteInvalidoAoAlterar() {
		RestAssured
		.given()
		.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.body("{" +  
				"   \"nome\": \"Urso\"," +
				"	\"raca\": \"Pastor Belga\"," + 
				"	\"porte\": \"grande\"," + 
				"	\"idade\": 2," +
				"   \"cpc\": \"012.345.678-90\" " +
				"}")
		.when()
		.post("/v1/cachorros")
		.then()
		.assertThat()
		.statusCode(HttpStatus.EXPECTATION_FAILED.value())
		.body("mensagem", Matchers.equalTo("Porte inválido. Porte deve ser Pequeno, Médio ou Grande"));
		
		Cachorro cachorroEstanciado = cachorroRepository.findById(cachorroParaAlterar.getId()).orElse(null);
		Assert.assertEquals("Tobias", cachorroEstanciado.getNome());
		Assert.assertEquals("Beagle", cachorroEstanciado.getRaca());
		Assert.assertEquals("Pequeno", cachorroEstanciado.getPorte());
		Assert.assertEquals(2, cachorroEstanciado.getIdade());
	}
	
	@Test
	public void deveValidarIdadeInvalidaAoAlterar() {
		RestAssured
		.given()
		.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.body("{" +  
				"   \"nome\": \"Urso\"," +
				"	\"raca\": \"Pastor Belga\"," + 
				"	\"porte\": \"Grande\"," + 
				"	\"idade\": -2," +
				"   \"cpc\": \"012.345.678-90\" " +
				"}")
		.when()
		.post("/v1/cachorros")
		.then()
		.assertThat()
		.statusCode(HttpStatus.BAD_REQUEST.value())
		.body("errors[0].defaultMessage", Matchers.equalTo("A idade deve ser maior ou igual a zero"));
		
		Cachorro cachorroEstanciado = cachorroRepository.findById(cachorroParaAlterar.getId()).orElse(null);
		Assert.assertEquals("Tobias", cachorroEstanciado.getNome());
		Assert.assertEquals("Beagle", cachorroEstanciado.getRaca());
		Assert.assertEquals("Pequeno", cachorroEstanciado.getPorte());
		Assert.assertEquals(2, cachorroEstanciado.getIdade());
	}
	
	@Test
	public void deveValidarPorteComRacaInvalidaAoAlterar() {
		RestAssured
		.given()
		.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.body("{" +  
				"   \"nome\": \"Urso\"," +
				"	\"raca\": \"Pastor Belga\"," + 
				"	\"porte\": \"Médio\"," + 
				"	\"idade\": 2," +
				"   \"cpc\": \"012.345.678-90\" " +
				"}")
		.when()
		.post("/v1/cachorros")
		.then()
		.assertThat()
		.statusCode(HttpStatus.EXPECTATION_FAILED.value())
		.body("mensagem", Matchers.equalTo("Não existem raças para este porte"));
		
		Cachorro cachorroEstanciado = cachorroRepository.findById(cachorroParaAlterar.getId()).orElse(null);
		Assert.assertEquals("Tobias", cachorroEstanciado.getNome());
		Assert.assertEquals("Beagle", cachorroEstanciado.getRaca());
		Assert.assertEquals("Pequeno", cachorroEstanciado.getPorte());
		Assert.assertEquals(2, cachorroEstanciado.getIdade());
	}
}
