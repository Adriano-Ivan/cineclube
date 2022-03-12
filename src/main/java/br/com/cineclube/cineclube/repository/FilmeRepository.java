package br.com.cineclube.cineclube.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cineclube.cineclube.model.Category;
import br.com.cineclube.cineclube.model.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long>{

	List<Filme> findByCategory(Category cat);

}
