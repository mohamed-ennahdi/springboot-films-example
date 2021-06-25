package com.sf.ennahdi.example.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sf.ennahdi.example.service.film.FilmService;
import com.sf.ennahdi.example.service.film.dto.FilmDto;

import javassist.NotFoundException;

@RestController
public class FilmController {
	
	@Autowired
	FilmService service;
	
	@GetMapping("/films")
    public ResponseEntity<List<FilmDto>> getAllFilms() {
		try {
			return new ResponseEntity<>(service.getAllFilms(), HttpStatus.OK);
		} catch(Exception e) {
			throw e;
		}
    }
	
	@GetMapping("/films/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable("id") long id) throws NotFoundException {
		try {
			return new ResponseEntity<>(service.getFilmById(id), HttpStatus.OK);
		} catch(NotFoundException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/films")
    public ResponseEntity<String> saveFilm(@RequestBody FilmDto film) {
		try {
			service.createFilm(film);
			return new ResponseEntity<>("Film " + film.getId() + " created successfully.", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/films/{id}")
	public ResponseEntity<String> updateFilm(@PathVariable("id") long id, @RequestBody FilmDto film) throws NotFoundException {
		try {
			service.updateFilm(id, film);
			return new ResponseEntity<>("Film " + id + " updated successfully.", HttpStatus.OK);
		} catch(NotFoundException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/films/{id}")
	public ResponseEntity<String> deleteFilm(@PathVariable("id") long id) throws NotFoundException {
		try {
			service.deleteFilm(id);
			return new ResponseEntity<>("Film " + id + " deleted successfully.", HttpStatus.OK);
		} catch(NotFoundException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
