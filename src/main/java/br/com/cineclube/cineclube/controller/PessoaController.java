package br.com.cineclube.cineclube.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import br.com.cineclube.cineclube.model.Person;
import br.com.cineclube.cineclube.model.Pessoa;
import br.com.cineclube.cineclube.repository.PessoaRepository;
import br.com.cineclube.cineclube.util.mvc.WrapperPersonSearch;

@Controller
@RequestMapping("/people")
public class PessoaController {

	@Autowired
	public PessoaRepository pessoaRepository;
	
	@Autowired
	private RestTemplate apiRequest;
	
	@Value("${api.base_servico2}")
	private String apiBaseServico2;
	
	@GetMapping("/extern_people")
	public String externPerson(Model model, @RequestParam String id) {
		model.addAttribute("search_id", true);
		Person person=null;
		try {
			String endpoint = "http://localhost:8080"+apiBaseServico2+"/people/"+id;
			
			person = apiRequest.getForObject(endpoint, Person.class);
			
			List<Person> people = new ArrayList<>();
			people.add(person);
			model.addAttribute("extern_people",people);
//			System.out.println(person+" "+person==null);
		}catch(Exception e) {
//			System.out.println(person==null);
			model.addAttribute("mensagem","Não há uma pessoa externa registrada com esse id.");
		}finally {
			return "pessoas/list.html";
		}	
	}
	
	@GetMapping("/extern_people/search")
	public String externPeople(Model model, @RequestParam String name) {
		model.addAttribute("search_name",true);
		WrapperPersonSearch res=null;
		String mensagemDeErro = "Não há uma pessoa externa com um nome que combine com esse.";
		try {
			String endpoint = "http://localhost:8080/"+apiBaseServico2+"/people/search?name="+name;
			
			res = apiRequest.getForObject(endpoint, WrapperPersonSearch.class);
			
			if(res.getResults().size() == 0) {
				model.addAttribute("mensagem",mensagemDeErro);
			}else {
				model.addAttribute("extern_people",res.getResults());
			}
//			System.out.println(res+" "+res==null);
		}catch(Exception e) {
//			System.out.println(res==null);
			model.addAttribute("mensagem","Houve um erro ao procurar a pessoa.");
		}finally {
			return "pessoas/list.html";
		}	
	}
	
	@RequestMapping()
	public String home() {
		return "redirect:/people/list";
	}
	
	@PostMapping("/save")
	public String pageCadastro(@Valid Pessoa pessoa, BindingResult result, Model model) {
		if(result.hasErrors()) {
			System.out.println("ENTROU EHEHEH");
			System.out.println(result.getFieldErrors());
			return this.enviarInformacoes(pessoa,model);
		}
		
		pessoaRepository.save(pessoa);
		return "redirect:/people/list";
	}
	
	@GetMapping("/save/extern")
	public String saveExtern(@RequestParam String name) {
		Pessoa pessoa = new Pessoa();
		pessoa.setName(name);
		pessoaRepository.save(pessoa);
		
		return "redirect:/people/list";
	}
	@RequestMapping("/list")
	public String list(Model model) {
		List<Pessoa> people = pessoaRepository.findAll();
		model.addAttribute("pessoaList",people);
		
		return "pessoas/list.html";
	}
	
	@GetMapping("/new")
	public String newForm(Model model) {
		Pessoa person = new Pessoa();
		return this.enviarInformacoes(person, model);
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable Long id) {
		Pessoa pessoa = pessoaRepository.getById(id);
		
		return this.enviarInformacoes(pessoa, model);
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		pessoaRepository.deleteById(id);
		
		return "redirect:/people/list";
	}
	
	private String enviarInformacoes(Pessoa pessoa,Model model) {
		model.addAttribute("pessoa",pessoa);
		
		return "pessoas/manter.html";
	}
}
