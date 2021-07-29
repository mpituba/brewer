package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.service.CadastroEstiloService;
import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilos")
public class EstilosController {
	
	@Autowired
	private Estilos estilos;
	
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
	
	/**
	 * Controlador de PesquisaEstiloss
	 * @param cervejaFilter - Modelo do Filtro passado para a pesquisa da página.
	 * @param result - Binding das validações. 
	 * @param pageable - Objeto responsável pela paginação da página de pesquisa.
	 * @param httpServletRequest  - Objeto Servlet.
	 * @PageableDefault(size = 2) - Parâmetro responsável pelo número de registros
	 * por página, aqui são dois registros por página.
	 * PageWrapper - Envelopador do filtro de cerveja e seus parâmetros.
	 * @author mpituba
	 */
	
	@GetMapping
	public ModelAndView pesquisar(EstiloFilter estiloFilter, BindingResult result, 
			@PageableDefault(size = 5) Pageable pageable, HttpServletRequest httpServletRequest ) {
		ModelAndView mv = new ModelAndView("estilo/PesquisaEstilos");
		
		//System.out.println(">>>>> PageNumber : " + pageable.getPageNumber());
		//System.out.println(">>>>> PageSize   : " + pageable.getPageSize());
		
		//Instanciado o PageWrapper		
		PageWrapper <Estilo> paginaWrapper = new PageWrapper<> (estilos.filtrar(estiloFilter, pageable),
				httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
}
