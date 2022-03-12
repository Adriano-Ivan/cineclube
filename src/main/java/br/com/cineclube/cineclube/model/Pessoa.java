package br.com.cineclube.cineclube.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="pessoas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pessoa {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@NotNull
	@NotBlank
	private String name;
	
	public Pessoa() {}
	
	public Pessoa(String name) {
		this.name=name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
