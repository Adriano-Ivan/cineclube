package br.com.cineclube.cineclube.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.cineclube.cineclube.model.Movie;
import br.com.cineclube.cineclube.model.Person;
import br.com.cineclube.cineclube.util.mvc.WrapperMovieSearch;
import br.com.cineclube.cineclube.util.mvc.WrapperPersonSearch;

@RestController
@RequestMapping("${api.base_servico2}")
public class PersonConsumer {

	@Value("${api.moviedb.key}")
	private String apiKey;
	
    @Autowired
    private RestTemplate apiRequest;
    // avatar == 1995
    // http :8080
    
    // MÉTODO: getMovieById() -> retorna objeto
    @RequestMapping("/people/{id}")
    public Person getMovieById(@PathVariable Long id) {
    	String endpoint = 
        		"https://api.themoviedb.org/3/person/" + id + "?api_key=" +  apiKey;
        Person person = apiRequest.getForObject(endpoint, Person.class);
        return person;
    }
    
 // MÉTODO: searchMovie() -> retorna uma lista
    @GetMapping("people/search")
	public WrapperPersonSearch searchPerson(@RequestParam String name) {
    	System.out.println("EITA");
		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);
		params.put("query",name);
		params.put("lang", "en-US");
		String url = "https://api.themoviedb.org/3/search/person?api_key={key}&query={query}&language={lang}";
		WrapperPersonSearch res = apiRequest.getForObject(url, WrapperPersonSearch.class, params);
		return res;
	}
    
}
