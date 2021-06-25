package com.sf.ennahdi.example.service.film.mapper;

import org.mapstruct.Mapper;

import com.sf.ennahdi.example.dao.entity.Film;
import com.sf.ennahdi.example.service.film.dto.FilmDto;

@Mapper(componentModel = "spring")
public interface FilmMapper {
	
//	FilmMapper INSTANCE = Mappers.getMapper( FilmMapper.class );
	
	FilmDto filmToFilmDto(Film film);
	Film filmDtoToFilm(FilmDto film);
}
