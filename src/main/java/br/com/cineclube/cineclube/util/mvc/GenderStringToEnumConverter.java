package br.com.cineclube.cineclube.util.mvc;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import br.com.cineclube.cineclube.model.Category;

// Tentando retornar lista de filmes por categoria
@Service
public class GenderStringToEnumConverter implements Converter<String, Category> {

	@Override
	public Category convert(String source) {
		try {
			return Category.valueOf(source.toUpperCase());
		}catch(IllegalArgumentException e) {
			return null;
		}
		
	}
}
