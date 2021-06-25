package com.sf.ennahdi.example.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sf.ennahdi.example.dao.entity.Film;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
	

}
