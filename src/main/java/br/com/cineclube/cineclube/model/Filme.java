package br.com.cineclube.cineclube.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="filmes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Filme {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@NotNull
	@NotBlank
	private String title;
	
	@NotNull
	@Past(message="não aceita data futura")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate release;
	
	@NotNull
	private Category category;
	
	
	@Min(value=0,message="deve ser maior ou igual a 0") @Max(value=10,message="deve ser menor ou igual a 10") @NotNull @NotNull()
	private BigDecimal score;
	
	public Filme() {}
	
	public Filme(String title, LocalDate release, Category category, BigDecimal score) {
		this.title=title;
		this.release=release;
		this.category=category;
		this.score=score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getRelease() {
		return release;
	}

	public void setRelease(LocalDate release) {
		this.release = release;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}
	 
	
	
}
