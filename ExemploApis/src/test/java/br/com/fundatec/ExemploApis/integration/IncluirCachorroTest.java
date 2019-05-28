package br.com.fundatec.ExemploApis.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IncluirCachorroTest {

	@LocalServerPort
	private int port;
	
	@Before
	public void setuo() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
	}
	
	@Test
	public void deveIncluirUmCachorro() {
		RestAssured
		.given()
		.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.body("{" + 
				"	\"nome\": \"urso\"," + 
				"	\"raca\": \"Pastor Belga\"," + 
				"	\"porte\": \"Grande\"," + 
				"	\"idade\": 2" + 
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
	}
}
