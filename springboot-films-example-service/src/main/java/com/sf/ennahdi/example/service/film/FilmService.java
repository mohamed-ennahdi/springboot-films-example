package com.sf.ennahdi.example.service.film;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.ennahdi.example.dao.entity.Film;
import com.sf.ennahdi.example.dao.repository.FilmRepository;
import com.sf.ennahdi.example.service.film.dto.FilmDto;
import com.sf.ennahdi.example.service.film.exception.FilmExistsAlreadyException;
import com.sf.ennahdi.example.service.film.exception.FilmNotFoundException;
import com.sf.ennahdi.example.service.film.mapper.FilmMapper;


@Service
@Transactional
public class FilmService {
	
	
	@Autowired
	FilmRepository repository;
	
	@Autowired
	FilmMapper filmMapper;
	
	public List<FilmDto> getAllFilms() {
		Iterable<Film> iterator = repository.findAll();
		
		List<FilmDto> films = new ArrayList<>();
		
		for (Film film : iterator) {
			films.add(filmMapper.filmToFilmDto(film));
		}
		return films;
	}
	
	
	public FilmDto getFilmById(long id) throws FilmNotFoundException {
		Optional<Film> filmToUpdate = repository.findById(id);
		if (filmToUpdate.isPresent()) {
			return filmMapper.filmToFilmDto(filmToUpdate.get());
		}
		throw new FilmNotFoundException("Film " + id + " Not Found");
	}
	
	public FilmDto createFilm(FilmDto input) throws FilmExistsAlreadyException {
		Optional<Film> film = repository.findById(input.getId());
		if (film.isEmpty()) {
			Film filmToSave = filmMapper.filmDtoToFilm(input);
			FilmDto filmSaved =  filmMapper.filmToFilmDto(repository.save(filmToSave));
			return filmSaved;
		}
		throw new FilmExistsAlreadyException("Film " + input.getId() + " exists already.");
	}
	
	public FilmDto updateFilm(long id, FilmDto input) throws FilmNotFoundException {
		Optional<Film> filmToUpdate = repository.findById(id);
		if (filmToUpdate.isPresent()) {
			Film filmToSave = filmMapper.filmDtoToFilm(input);
			FilmDto filmSaved =  filmMapper.filmToFilmDto(repository.save(filmToSave));
			return filmSaved;
		}
		throw new FilmNotFoundException("Film " + input.getId() + " Not Found");
	}
	
	public void deleteFilm(long id) throws FilmNotFoundException {
		Optional<Film> filmToDelete = repository.findById(id);
		if (filmToDelete.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new FilmNotFoundException("Film " + id + " Not Found");
		}
	}
}
