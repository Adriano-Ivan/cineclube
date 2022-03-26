package br.com.cineclube.cineclube.util.mvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.cineclube.cineclube.model.Genre;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Service
public class ResourceGenre {

	@Value("${api.moviedb.key}")
	private String api_key;
	
	private RestTemplate apiRequest = new RestTemplate();
	
	public List<Genre> returnGenres(){
		String url = "https://api.themoviedb.org/3/genre/movie/list?language=pt-BR&api_key="+api_key;
		WrapperGenre res = apiRequest.getForObject(url, WrapperGenre.class);
		
		return res.getGenres();
	}
	
	public List<String> extractGenres(List<Genre> genres){
		List<String> nomesDosGeneros = new ArrayList<String>();
		
		for(Genre genre : genres) {
			nomesDosGeneros.add(genre.getName());
		}
		
		return nomesDosGeneros;
	}
}
