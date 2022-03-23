package br.com.cineclube.cineclube.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.cineclube.cineclube.model.Movie;
import br.com.cineclube.cineclube.util.mvc.WrapperMovieSearch;
import br.com.cineclube.cineclube.util.mvc.WrapperPersonSearch;

@RestController
@RequestMapping("${api.base_servico2}")
public class MovieConsumer {
	
	@Value("${api.moviedb.key}")
    private String apiKey;

    @Autowired
    private RestTemplate apiRequest;
    // avatar == 1995
    // http :8080
    
    // MÉTODO: getMovieById() -> retorna objeto
    @RequestMapping("/movies/{id}")
    public Movie getMovieById(@PathVariable Long id) {
    	System.out.println(apiKey);
    	String endpoint = 
        		"https://api.themoviedb.org/3/movie/" + id + "?api_key=" +  apiKey+"&language=pt-BR";
        Movie movie = apiRequest.getForObject(endpoint, Movie.class);
        return movie;
    }
    
    // MÉTODO: searchMovie() -> retorna uma lista
    @GetMapping("/movies/search")
	public WrapperMovieSearch searchMovie(@RequestParam String title, @RequestParam String year) {
    	
		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);
		params.put("query", title);
		params.put("year", year);
		params.put("lang", "pt-BR");
		String url = "https://api.themoviedb.org/3/search/movie?api_key={key}&query={query}&year={year}&language={lang}";
		WrapperMovieSearch res = apiRequest.getForObject(url, WrapperMovieSearch.class, params);
		return res;
	}
    
    @GetMapping("/movies/filter")
	public WrapperMovieSearch searchFilter(@RequestParam(required=false) String minDate, 
			@RequestParam(required=false) String maxDate, @RequestParam(required=false) String genre) {
    	
    	if(minDate.equals("null") || minDate.trim().equals("")) {
    		minDate="1980";
    	}
    	if(maxDate.equals("null") || maxDate.trim().equals("")) {
    		maxDate="1990";
    	}
    	if(genre.equals("null") || genre.trim().equals("")) {
    		genre="878";
    	}

		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);
		params.put("minDate",minDate);
		params.put("maxDate", maxDate);
		params.put("genre", genre);
		String url = "https://api.themoviedb.org/3/discover/movie?api_key={key}&primary_release_date.gte={minDate}&primary_release_date.lte={maxDate}&with_genres={genre}&sort_by=vote_count.desc";
		WrapperMovieSearch res = apiRequest.getForObject(url, WrapperMovieSearch.class, params);
		return res;
	}
    
    
//  https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=1980&sort_by=vote_count.desc&primary_release_date.lte=1990&with_genres=sci-fi&api_key=d92049cf0a2ee0189c6d61ede77ba315
}