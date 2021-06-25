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
import com.sf.ennahdi.example.service.film.mapper.FilmMapper;

import javassist.NotFoundException;

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
	
	
	public FilmDto getFilmById(long id) throws NotFoundException {
		Optional<Film> filmToUpdate = repository.findById(id);
		if (filmToUpdate.isPresent()) {
			return filmMapper.filmToFilmDto(filmToUpdate.get());
		}
		throw new NotFoundException("Film Not Found");
	}
	
	public FilmDto createFilm(FilmDto input) {
		Film filmToSave = filmMapper.filmDtoToFilm(input);
		FilmDto filmSaved =  filmMapper.filmToFilmDto(repository.save(filmToSave));
		
		return filmSaved;
	}
	
	public FilmDto updateFilm(long id, FilmDto input) throws NotFoundException {
		Optional<Film> filmToUpdate = repository.findById(id);
		if (filmToUpdate.isPresent()) {
			Film filmToSave = filmMapper.filmDtoToFilm(input);
			FilmDto filmSaved =  filmMapper.filmToFilmDto(repository.save(filmToSave));
			return filmSaved;
		}
		throw new NotFoundException("Film Not Found");
	}
	
	public void deleteFilm(long id) throws NotFoundException {
		Optional<Film> filmToDelete = repository.findById(id);
		if (filmToDelete.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new NotFoundException("Film " + id + " Not Found");
		}
	}
}
