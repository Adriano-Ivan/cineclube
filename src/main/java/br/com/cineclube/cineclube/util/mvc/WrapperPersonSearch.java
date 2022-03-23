package br.com.cineclube.cineclube.util.mvc;

import java.util.List;

import br.com.cineclube.cineclube.model.Person;

public class WrapperPersonSearch {

	private List<Person> results;
	
	public List<Person> getResults(){
		return results;
	}
	
	public void setResults(List<Person> results) {
		this.results = results;
	}
}
