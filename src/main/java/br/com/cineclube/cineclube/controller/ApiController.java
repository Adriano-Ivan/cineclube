package br.com.cineclube.cineclube.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cineclube.cineclube.model.Filme;
import br.com.cineclube.cineclube.model.Pessoa;
import br.com.cineclube.cineclube.repository.FilmeRepository;
import br.com.cineclube.cineclube.repository.PessoaRepository;

@RestController
@RequestMapping("${api.base_servico}") // Variáveis de ambiente concentradas no application.properties (a base é "/api/v1/", para seguir a regra de versionamento)
public class ApiController {

	@Autowired
	private FilmeRepository filmeRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Value("${api.base_servico}")
	private String urlBaseDoServico;
	
	
	@GetMapping("${api.url_filmes}")
	public ResponseEntity<List<Filme>> getFilmes(){
		List<Filme> filmes = filmeRepository.findAll();
		
		return ResponseEntity.ok(filmes);
	}
	 
	@GetMapping("${api.uri_filme}")
	public ResponseEntity<Filme> getFilme(@PathVariable("id") Long id) {
		Optional<Filme> f = filmeRepository.findById(id);
		
		if(f.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(f.get());
	}
	
	@PostMapping("${api.url_filmes}")
	public ResponseEntity<Filme> cadastrarFilme(@RequestBody @Valid Filme filme,UriComponentsBuilder uriBuilder){
		try {
			filmeRepository.save(filme);
			
			URI uri = uriBuilder.path(urlBaseDoServico+"/filmes/{id}")
					.buildAndExpand(filme.getId()).toUri();
			
			return ResponseEntity.created(uri).body(filme);
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("${api.uri_filme}")
	public ResponseEntity<Filme> atualizarFilme(@PathVariable("id") Long id,
			@RequestBody @Valid Filme filme){
		try {
			Optional<Filme> filmeSolicitado = filmeRepository.findById(id);
			
			if(filmeSolicitado.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			Filme filmeExistente = filmeSolicitado.get();
			
	
			filmeExistente.setCategory(filme.getCategory());
			filmeExistente.setRelease(filme.getRelease());
			filmeExistente.setScore(filme.getScore());
			filmeExistente.setTitle(filme.getTitle());
			
			filmeRepository.save(filmeExistente);
			
			return ResponseEntity.ok(filmeExistente);
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("${api.uri_filme}")
	public ResponseEntity<?> deletarFilme(@PathVariable("id") Long id){
		Optional<Filme> filme= filmeRepository.findById(id);
		
		if(filme.isPresent()) {
			filmeRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("${api.url_pessoas}")
	public ResponseEntity<List<Pessoa>> getPessoas(){
		List<Pessoa> pessoas = pessoaRepository.findAll();
		
		return ResponseEntity.ok(pessoas);
	}
	
	@GetMapping("${api.uri_pessoa}")
	public ResponseEntity<Pessoa> getPessoa(@PathVariable("id") Long id){
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		
		if(pessoa.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(pessoa.get());
	}
	
	@PostMapping("${api.url_pessoas}")
	public ResponseEntity<Pessoa> cadastrarPessoa(@RequestBody @Valid Pessoa pessoa,
			UriComponentsBuilder uriBuilder){
		try {
			pessoaRepository.save(pessoa);
			
			URI uri = uriBuilder.path(urlBaseDoServico+"/pessoas/{id}")
					.buildAndExpand(pessoa.getId()).toUri();
			
			return ResponseEntity.created(uri).body(pessoa);
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("${api.uri_pessoa}")
	public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable("id") Long id,
			@RequestBody @Valid Pessoa pessoa){
		try {
			Optional<Pessoa> pessoaSolicitada = pessoaRepository.findById(id);
			
			if(pessoaSolicitada.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			Pessoa pessoaExistente = pessoaSolicitada.get();
			
			pessoaExistente.setName(pessoa.getName());
			
			pessoaRepository.save(pessoaExistente);
			
			return ResponseEntity.ok(pessoa);
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("${api.uri_pessoa}")
	public ResponseEntity<?> deletarPessoa(@PathVariable("id") Long id){
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		
		if(pessoa.isPresent()) {
			pessoaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
