package br.com.cineclube.cineclube.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.com.cineclube.cineclube.model.Category;
import br.com.cineclube.cineclube.model.Filme;
import br.com.cineclube.cineclube.model.Movie;
import br.com.cineclube.cineclube.repository.FilmeRepository;
import br.com.cineclube.cineclube.util.mvc.GenderStringToEnumConverter;
import br.com.cineclube.cineclube.util.mvc.ResourceGenre;
import br.com.cineclube.cineclube.util.mvc.WrapperMovieSearch;

@Controller
@RequestMapping("/filmes")
public class FilmeController {
	
	@Autowired
	private FilmeRepository filmeRepository;
	
	@Autowired
	private RestTemplate apiRequest;
	
	@Value("${api.base_servico2}")
	private String apiBaseServico2;
	
	@Autowired
	private ResourceGenre resourceGenre;
//	
//	@Autowired
//	private GenderStringToEnumConverter converterCategory;
//	
	
	@GetMapping("/extern_movies")
	public String externMovies(Model model, @RequestParam(required=false) String minDate, 
			@RequestParam(required=false) String maxDate, @RequestParam(required=false) String genre) {
		WrapperMovieSearch res = null;
		String mensagemDeErro = "Não foram encontrados filmes que combinem com esse nome.";
		model.addAttribute("search_film",true);
		model.addAttribute("genres", resourceGenre.returnGenres());
		try {
			String endpoint = "http://localhost:8080"+apiBaseServico2+"/movies/filter?minDate="+minDate+"&maxDate="+
					maxDate+"&genre="+genre;
			
			res = apiRequest.getForObject(endpoint, WrapperMovieSearch.class);
			
			if(res.getResults().size() == 0) {
				model.addAttribute("mensagemDeErro",mensagemDeErro);
			}else {
				model.addAttribute("extern_movies",res.getResults());
			}
		}catch(Exception e) {
			model.addAttribute("mensagemDeErro","Houve um erro na procura de filmes.");
		}finally {
			return "filmes/list.html";
		}
	}
	
	@GetMapping("/extern_movies/details/{id}")
	public String detailMovie(Model model, @PathVariable("id") String id) {
		model.addAttribute("search_film",true);
		model.addAttribute("genres", resourceGenre.returnGenres());
		try {
			String endpoint = "http://localhost:8080"+apiBaseServico2+"/movies/"+id;
			
			Movie movie = apiRequest.getForObject(endpoint, Movie.class);
			
			model.addAttribute("extern_movie",movie);
		}catch(Exception e) {
			model.addAttribute("mensagemDeErro", "O ID fornecido não corresponde a um filme.");
		}finally {
			return "filmes/manter.html";
		}
		
	}
	
	
	@RequestMapping()
	public String home(Model model) {
		// redireciona para a rota list
		return "redirect:/filmes/list";
	}
	
	@PostMapping("/save")
	public String pageCadastro(@Valid Filme filme,BindingResult result, Model model) {	
		if(result.hasErrors()) {
			// voltar para manter.html
			System.out.println("EITA - ENTROU");
			
			return this.enviarInformacoes(filme, model);
		}
		filmeRepository.save(filme);
		return "redirect:/filmes/list";
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<Filme> filmes = filmeRepository.findAll();
		Category[] categorias = Category.values();
		model.addAttribute("filmeList",filmes);
		model.addAttribute("categorias",categorias);
		
		return "filmes/list.html";
	}
	
	
	@GetMapping("/new")
	public String newForm(Model model) {
		Filme filme = new Filme();
		return this.enviarInformacoes(filme,model);
	}
	
	@GetMapping("/edit/{id}")
	public String pageEdit(Model model,@PathVariable("id") Long id) {
		Filme filme = filmeRepository.getById(id);
		
		return this.enviarInformacoes(filme,model);
	}
	@RequestMapping("/delete/{id}")
	public String pageDeletar(@PathVariable("id") Long id) {
		filmeRepository.deleteById(id);
		
		return "redirect:/filmes/list";
	}

	private String enviarInformacoes(Filme filme,Model model) {
		// filme será mapeado para ${filme}
		
		model.addAttribute("filme",filme);
	
		// criar lista de categorias
		model.addAttribute("categorias", Category.values());
		
		return "filmes/manter.html";
	}
}
