package br.com.cineclube.cineclube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cineclube.cineclube.model.Pessoa;

public interface PessoaRepository extends 
	JpaRepository<Pessoa, Long>{

}
