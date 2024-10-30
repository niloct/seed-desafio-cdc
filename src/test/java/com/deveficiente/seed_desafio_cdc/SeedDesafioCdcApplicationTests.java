package com.deveficiente.seed_desafio_cdc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SeedDesafioCdcApplicationTests {
	@Autowired
	private TestRestTemplate template;

	@Test
	public void getHello() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/", String.class);
		assertThat(response.getBody()).isEqualTo("Saudações :) !");
	}

	@Test
	public void postAutorOK() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("descricao", "Um autor iniciante");
		map.add("email", "nilo.teixeira@gmail.com");
		map.add("nome", "Nilo César Teixeira");
		map.add("dataInscricao", "30/10/2024");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = template.postForEntity("/autores", request, String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	public void postAutorSemNome() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("descricao", "Um autor iniciante");
		map.add("email", "nilo.teixeira@gmail.com");
		map.add("dataInscricao", "30/10/2024");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = template.postForEntity("/autores", request, String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertTrue(response.getBody().equals("{\"nome\":\"O nome é obrigatório.\"}"));
	}

	@Test
	public void postAutorSemEmail() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("descricao", "Um autor iniciante");
		map.add("nome", "Nilo César Teixeira");
		map.add("dataInscricao", "30/10/2024");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = template.postForEntity("/autores", request, String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertTrue(response.getBody().equals("{\"email\":\"O e-mail é obrigatório.\"}"));
	}

	@Test
	public void postAutorComEmailInvalido() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("descricao", "Um autor iniciante");
		map.add("email", "nada");
		map.add("nome", "Nilo César Teixeira");
		map.add("dataInscricao", "30/10/2024");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = template.postForEntity("/autores", request, String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertTrue(response.getBody().equals("{\"email\":\"O e-mail está em formato inválido.\"}"));
	}

	@Test
	public void postAutorSemDescricao() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("email", "nilo.teixeira@gmail.com");
		map.add("nome", "Nilo César Teixeira");
		map.add("dataInscricao", "30/10/2024");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = template.postForEntity("/autores", request, String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertTrue(response.getBody().equals("{\"descricao\":\"A descrição é obrigatória.\"}"));
	}

	@Test
	public void postAutorSemDataInscricao() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("descricao", "Um autor iniciante");
		map.add("email", "nilo.teixeira@gmail.com");
		map.add("nome", "Nilo César Teixeira");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = template.postForEntity("/autores", request, String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertTrue(response.getBody().equals("{\"dataInscricao\":\"não deve ser nulo\"}"));
	}

	@Test
	public void postAutoComDescricaoMuitoLonga() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("email", "nilo.teixeira@gmail.com");
		map.add("nome", "Nilo César Teixeira");
		map.add("descricao", "abc".repeat(400));
		map.add("dataInscricao", "30/10/2024");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = template.postForEntity("/autores", request, String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertTrue(response.getBody().equals("{\"descricao\":\"O tamanho máximo da descrição é de 400 caracteres.\"}"));
	}
}
