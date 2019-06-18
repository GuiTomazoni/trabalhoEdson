package br.com.fundatec.ExemploApis.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import java.util.List;

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
import br.com.fundatec.ExemploApis.entity.Pessoa;
import br.com.fundatec.ExemploApis.entity.PorteParametro;
import br.com.fundatec.ExemploApis.repository.CachorroRepository;
import br.com.fundatec.ExemploApis.repository.PessoaRepository;
import br.com.fundatec.ExemploApis.repository.PorteParametroRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IncluirCachorroTest {

	@LocalServerPort
	private int port;
	@Autowired
	private CachorroRepository cachorroRepository;
	@Autowired
	private PorteParametroRepository porteParametroRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	private Pessoa pessoa;
	
	@Before
	public void setup() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
		cachorroRepository.deleteAll();
		porteParametroRepository.deleteAll();
		porteParametroRepository.save(new PorteParametro("Pequeno"));
		porteParametroRepository.save(new PorteParametro("Médio"));
		porteParametroRepository.save(new PorteParametro("Grande"));
		pessoa = pessoaRepository.save(new Pessoa(null, "Alberto", 16));
	}
	
	@Test
	public void deveIncluirUmCachorro() {
		RestAssured
			.given()
			.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body("{" + 
					"	\"nome\": \"Urso\"," + 
					"	\"raca\": \"Pastor Belga\"," + 
					"	\"porte\": \"Grande\"," + 
					"	\"idade\": 2," +
					"   \"cpc\": \"012.345.678-90\", " +
					"   \"idPessoa\": " + pessoa.getId() +
					"}")
			.when()
			.post("/v1/cachorros")
			.then()
			.assertThat()
			.body("nome", Matchers.equalTo("Urso"))
			.body("raca", Matchers.equalTo("Pastor Belga"))
			.body("porte", Matchers.equalTo("Grande"))
			.body("idade", Matchers.equalTo(2))
			.body("id", Matchers.greaterThan(0))
			.statusCode(HttpStatus.CREATED.value());
		
		Assert.assertTrue(cachorroRepository.count() > 0);
		Cachorro cachorroIncluido = ((List<Cachorro>) cachorroRepository.findAll()).get(0);
		Assert.assertNotNull(cachorroIncluido.getPessoa());
		Assert.assertEquals("Urso", cachorroIncluido.getNome());
		Assert.assertEquals("Pastor Belga", cachorroIncluido.getRaca());
		Assert.assertEquals(2, cachorroIncluido.getIdade());
	}
	
	@Test
	public void deveValidarCachorroSemNome() {
		RestAssured
		.given()
		.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.body("{" +  
				"	\"raca\": \"Pastor Belga\"," + 
				"	\"porte\": \"Grande\"," + 
				"	\"idade\": 2" + 
				"}")
		.when()
		.post("/v1/cachorros")
		.then()
		.assertThat()
		.statusCode(HttpStatus.BAD_REQUEST.value())
		.body("errors[0].defaultMessage", Matchers.equalTo("O campo nome deve ser preenchido"));
			
		Assert.assertTrue(cachorroRepository.count() == 0);
	}
	
	@Test
	public void deveValidarCpcInvalido() {
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
		.post("/v1/cachorros")
		.then()
		.assertThat()
		.statusCode(HttpStatus.BAD_REQUEST.value())
		.body("errors[0].defaultMessage", Matchers.equalTo("Campo cpc inválido"));
		
		Assert.assertTrue(cachorroRepository.count() == 0);
			
	}
	
	@Test
	public void deveValidarPorteInvalido() {
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
		
		Assert.assertTrue(cachorroRepository.count() == 0);
	}
	
	@Test
	public void deveValidarIdadeInvalida() {
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
		
		Assert.assertTrue(cachorroRepository.count() == 0);
	}
	
	@Test
	public void deveValidarPorteComRacaInvalida() {
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
		
		Assert.assertTrue(cachorroRepository.count() == 0);
	}
}
