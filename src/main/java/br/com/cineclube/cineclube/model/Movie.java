package br.com.cineclube.cineclube.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movie {

	private Long id;
	private String title;
	private String overview;
	private BigDecimal vote_average;
	private String poster_path;
	private LocalDate release_date;
	private Double vote_count;
	
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
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public BigDecimal getVote_average() {
		return vote_average;
	}
	public void setVote_average(BigDecimal vote_average) {
		this.vote_average = vote_average;
	}
	public String getPoster_path() {
		return poster_path;
	}
	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}
	public LocalDate getRelease_date() {
		return release_date;
	}
	public void setRelease_date(LocalDate release_date) {
		this.release_date = release_date;
	}
	public Double getVote_count() {
		return vote_count;
	}
	public void setVote_count(Double vote_count) {
		this.vote_count = vote_count;
	}
	
}
