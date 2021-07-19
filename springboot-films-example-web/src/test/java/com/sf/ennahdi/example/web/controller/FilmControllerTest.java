package com.sf.ennahdi.example.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.ennahdi.example.dao.entity.Film;
import com.sf.ennahdi.example.dao.entity.Language;
import com.sf.ennahdi.example.dao.repository.FilmRepository;
import com.sf.ennahdi.example.dao.repository.LanguageRepository;
import com.sf.ennahdi.example.service.film.dto.FilmDto;
import com.sf.ennahdi.example.web.app.FilmApp;

@EnableAutoConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {FilmApp.class})
//@EnableJpaRepositories(basePackageClasses = {FilmRepository.class})
//@EntityScan(basePackages = "com.sf.ennahdi.example.dao.entity")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class FilmControllerTest {
	
	@Autowired
	final FilmRepository repository = null;
	
	@Autowired
	final LanguageRepository langRepository = null;
	
	@BeforeAll
	public void init() {
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2006-02-15 05:02:19");
			Language japanese = new Language(3L, "Japanese", date);
			Language english = new Language(1L, "English", date);
			Language german = new Language(6L, "German", date);
			Language italian = new Language(2L, "Italian", date);
			langRepository.save(japanese);
			langRepository.save(english);
			langRepository.save(german);
			langRepository.save(italian);
		
			Film f1 = null;
			Film f2 = null;
			Film f3 = null;
			Film f4 = null;
			f1 = new Film(801L, "SISTER FREDDY", "A Stunning Saga of a Butler And a Woman who must Pursue a Explorer in Australia", japanese);
			f2 = new Film(802L, "SKY MIRACLE", "A Epic Drama of a Mad Scientist And a Explorer who must Succumb a Waitress in An Abandoned Fun House", english);
			f3 = new Film(803L, "SLACKER LIAISONS", "A Fast-Paced Tale of a A Shark And a Student who must Meet a Crocodile in Ancient China", german);
			f4 = new Film(804L, "SLEEPING SUSPECTS", "A Stunning Reflection of a Sumo Wrestler And a Explorer who must Sink a Frisbee in A MySQL Convention", italian);
			repository.save(f1);
			repository.save(f2);
			repository.save(f3);
			repository.save(f4);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	

	@Autowired
	FilmController testSubject;
	
	RestTemplate restTemplate = new RestTemplate();
	
	ObjectMapper mapper = new ObjectMapper();

	@LocalServerPort
	int randomServerPort;

	@Test
	@Order(1)
	public void getAllFilmsTest() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/films";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		
		FilmDto[] films = mapper.readValue(result.getBody(), FilmDto[].class);
		
		assertEquals(4, films.length);
		int i;
		i = 0;
		assertEquals(801L, films[i].getId());
		assertEquals("SISTER FREDDY", films[i].getTitle());
		assertEquals("A Stunning Saga of a Butler And a Woman who must Pursue a Explorer in Australia", films[i].getDescription());
		assertEquals("Japanese", films[i].getLanguage().getName());
		
		i = 1;
		assertEquals(802L, films[i].getId());
		assertEquals("SKY MIRACLE", films[i].getTitle());
		assertEquals("A Epic Drama of a Mad Scientist And a Explorer who must Succumb a Waitress in An Abandoned Fun House", films[i].getDescription());
		assertEquals("English", films[i].getLanguage().getName());
		
		i = 2;
		assertEquals(803L, films[i].getId());
		assertEquals("SLACKER LIAISONS", films[i].getTitle());
		assertEquals("A Fast-Paced Tale of a A Shark And a Student who must Meet a Crocodile in Ancient China", films[i].getDescription());
		assertEquals("German", films[i].getLanguage().getName());
		
		i = 3;
		assertEquals(804L, films[i].getId());
		assertEquals("SLEEPING SUSPECTS", films[i].getTitle());
		assertEquals("A Stunning Reflection of a Sumo Wrestler And a Explorer who must Sink a Frisbee in A MySQL Convention", films[i].getDescription());
		assertEquals("Italian", films[i].getLanguage().getName());
	}

	@Test
	@Order(2)
	public void getFilmByIdTest() throws Exception {
		long id = 801L;
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/films/" + id;
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		
		FilmDto film = mapper.readValue(result.getBody(), FilmDto.class);
		
		assertEquals(801L, film.getId());
		assertEquals("SISTER FREDDY", film.getTitle());
		assertEquals("A Stunning Saga of a Butler And a Woman who must Pursue a Explorer in Australia", film.getDescription());
		assertEquals("Japanese", film.getLanguage().getName());
	}
	
	@Test
	@Order(2)
	public void getFilmByIdNotFoundTest() throws Exception {
		long id = 700L;
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/films/" + id;
		URI uri = new URI(baseUrl);

		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.getForEntity(uri, String.class);
	    });
		
		String expectedMessage = "Film 700 Not Found";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
			
	    int expectedStatusCode = 404;
	    int actualStatusCode = exception.getStatusCode().value();

	    assertEquals(expectedStatusCode, actualStatusCode);
		
	}

	@Test
	@Order(2)
	public void createFilmTest() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/films/";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    JSONObject filmJsonObject = new JSONObject();
	    filmJsonObject.put("id", 999);
	    filmJsonObject.put("title", "ZOOLANDER FICTION");	
	    filmJsonObject.put("description", "A Fateful Reflection of a Waitress And a Boat who must Discover a Sumo Wrestler in Ancient China");
	    
	    JSONObject languageJsonObject = new JSONObject();
	    languageJsonObject.put("id", 1);
	    
	    filmJsonObject.put("language", languageJsonObject);
		
		HttpEntity<String> request = new HttpEntity<String>(filmJsonObject.toString(), headers);

		ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

		String expectedMessage = "Film 999 created successfully.";
	    String actualMessage = result.getBody();

	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    int actualStatusCode = result.getStatusCode().value();
	    int expectedStatusCode = 200;
	    
	    assertEquals(expectedStatusCode, actualStatusCode);
	}
	
	@Test
	@Order(2)
	public void createFilmExistsAlreadyTest() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/films/";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		JSONObject filmJsonObject = new JSONObject();
		filmJsonObject.put("id", 801);
		filmJsonObject.put("title", "ZOOLANDER FICTION");	
		filmJsonObject.put("description", "A Fateful Reflection of a Waitress And a Boat who must Discover a Sumo Wrestler in Ancient China");
		
		JSONObject languageJsonObject = new JSONObject();
		languageJsonObject.put("id", 1);
		
		filmJsonObject.put("language", languageJsonObject);
		
		HttpEntity<String> request = new HttpEntity<String>(filmJsonObject.toString(), headers);
		
		ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
			 
		String expectedMessage = "Film 801 exists already.";
	    String actualMessage = result.getBody();

	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    int expectedCode = 302;
	    int actualCode = result.getStatusCodeValue();
	    
	    assertEquals(expectedCode, actualCode);
	}

	@Test
	@Order(3)
	public void updateFilmTest() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/films/999";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    JSONObject filmJsonObject = new JSONObject();
	    filmJsonObject.put("id", 999);
	    filmJsonObject.put("title", "ZOOLANDER FICTION updated");	
	    filmJsonObject.put("description", "A Fateful Reflection of a Waitress And a Boat who must Discover a Sumo Wrestler in Ancient China");
	    
	    JSONObject languageJsonObject = new JSONObject();
	    languageJsonObject.put("id", 1);
	    
	    filmJsonObject.put("language", languageJsonObject);
		
		HttpEntity<String> request = new HttpEntity<>(filmJsonObject.toString(), headers);

		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);

		String expectedMessage = "Film 999 updated successfully.";
	    String actualMessage = result.getBody();

	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    int actualStatusCode = result.getStatusCode().value();
	    int expectedStatusCode = 200;
	    
	    assertEquals(expectedStatusCode, actualStatusCode);
	}
	@Test
	@Order(3)
	public void updateFilmNotExistsTest() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/films/700";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		JSONObject filmJsonObject = new JSONObject();
		filmJsonObject.put("id", 700);
		filmJsonObject.put("title", "ZOOLANDER FICTION updated");	
		filmJsonObject.put("description", "A Fateful Reflection of a Waitress And a Boat who must Discover a Sumo Wrestler in Ancient China");
		
		JSONObject languageJsonObject = new JSONObject();
		languageJsonObject.put("id", 1);
		
		filmJsonObject.put("language", languageJsonObject);
		
		HttpEntity<String> request = new HttpEntity<>(filmJsonObject.toString(), headers);
		
		
		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
	    });
		
		String expectedMessage = "Film 700 Not Found";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
			
	    int expectedStatusCode = 404;
	    int actualStatusCode = exception.getStatusCode().value();

	    assertEquals(expectedStatusCode, actualStatusCode);
	}

	@Test
	@Order(4)
	public void deleteFilmTest() throws Exception {
		long id = 999L;
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/films/" + id;
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
		
		String expectedMessage = "Film 999 deleted successfully.";
	    String actualMessage = result.getBody();

	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    int expectedStatusCode = 200;
	    int actualStatusCode = result.getStatusCodeValue();
	    
	    assertEquals(expectedStatusCode, actualStatusCode);
	}
	@Test
	@Order(4)
	public void deleteFilmNotFoundTest() throws Exception {
		long id = 555L;
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/films/" + id;
		URI uri = new URI(baseUrl);
		
		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
	    });
		
		String expectedMessage = "Film 555 Not Found";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
		int expectedStatusCode = 404;
	    int actualStatusCode = exception.getStatusCode().value();
	    
	    assertEquals(expectedStatusCode, actualStatusCode);
	}
}