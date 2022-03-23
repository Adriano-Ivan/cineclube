package br.com.cineclube.cineclube.util.mvc;

import java.math.BigDecimal;
import java.util.List;

import br.com.cineclube.cineclube.model.Movie;

public class WrapperMovieSearch {
	
    private List<Movie> results;

    public List<Movie> getResults() {
    	
        // ordem decrescente == ordena por filmes mais populares
		results.sort( (f1,f2) -> f2.getVote_average().compareTo(f1.getVote_average()) 
				 );

		return results;
    }
    public void setResults(List<Movie> results) {
        this.results = results;
    }
}