package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.service.CadastroEstiloService;
import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilos")
public class EstilosController {
	
	@Autowired
	private CadastroEstiloService cadastroEstiloService;
	
	@RequestMapping("/novo")
	public String novo(Estilo estilo) {
		return "estilo/CadastroEstilo";
	}
	
	//Quando for feito um POST em cidades/novo chamará este método 
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Estilo estilo, BindingResult result, Model model,
			RedirectAttributes attributes) {
		
		//Verificador do BindingResult
		if(result.hasErrors()) {
			return novo(estilo);
		}
		
		//Usa try catch pois este método pode lançar a exceção para um estilo existente
		try {
			
			cadastroEstiloService.salvar(estilo);
		
		}catch(NomeEstiloJaCadastradoException e) {
			
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		
		}
		
		
		attributes.addFlashAttribute("mensagem", "Estilo salvo com sucesso!");
		return "redirect:/estilos/novo";
	}
	
	
	//1. Salvar utilizado pelo modal do cadastro rapido de estilo via estilo.cadastro-rapido.js
	//2. @Request body transforma o corpo da requisição via js no objeto Estilo e usa validação!
	//3 .Verifica se há erros de validação
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
		public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		
		/**
		 * Salva dados e verifica se já cadastrado.
		 *Se tiver exceção retorna a mensagem via ControllerAdviceHandler
		 *que monitora os controllers para uma exceção NomeEstiloJaCadastradoException.
		 *@mpituba 
		**/
		estilo = cadastroEstiloService.salvar(estilo);
		
		return ResponseEntity.ok(estilo);
		
	}
	
}
