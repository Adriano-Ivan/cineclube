package br.com.cineclube.cineclube.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cineclube.cineclube.model.Pessoa;
import br.com.cineclube.cineclube.repository.PessoaRepository;

@Controller
@RequestMapping("/people")
public class PessoaController {

	@Autowired
	public PessoaRepository pessoaRepository;
	
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
