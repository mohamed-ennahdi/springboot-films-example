package com.sf.ennahdi.example.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sf.ennahdi.example.dao.entity.Language;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Long> {

}
