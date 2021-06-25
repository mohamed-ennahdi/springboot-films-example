package com.sf.ennahdi.example.service.film.mapper;

import org.mapstruct.Mapper;

import com.sf.ennahdi.example.dao.entity.Language;
import com.sf.ennahdi.example.service.film.dto.LanguageDto;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
	Language fromLanguageDtoToLanguage(LanguageDto language);
	LanguageDto fromLanguageToLanguageDto(Language language);
}
