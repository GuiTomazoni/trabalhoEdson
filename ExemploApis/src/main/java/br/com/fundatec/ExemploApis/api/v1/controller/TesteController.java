package br.com.fundatec.ExemploApis.api.v1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {
	
	@GetMapping("/hello-world")
	public ResponseEntity<String> meuPrimeiroTeste(){
		return ResponseEntity.ok("Hello World");
	} 

}
