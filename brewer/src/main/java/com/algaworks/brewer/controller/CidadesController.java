package com.algaworks.brewer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.Cidades;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.service.CadastroCidadeService;
import com.algaworks.brewer.service.exception.NomeCidadeJaCadastradaException;

/**
 * Atenção!!!
 * Este controlador é compartilhado entre as telas de Cadastrocliente e CadastroCidade
 * para o controle de cidade e estado. Parte do código em JS * @author mpituba
 */
@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private Cidades cidades;
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@RequestMapping("/nova")
	public ModelAndView nova(Cidade cidade) {
		
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		mv.addObject("estados", estados.findAll());
				
		return mv;
	}
	
	
	
	/**Chamada do Estado/Cidade do frontEnd no cadastro de clientes
	 @param codigoEstado @author mpituba */
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade>  pesquisarPorCodigoEstado(
	       @RequestParam(name= "estado", defaultValue = "-1")Long codigoEstado){
		
		//Lentidão no sistema para aparecer o icome do miniloading
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {	}
		
		return cidades.findByEstadoCodigo(codigoEstado);
	}
	
	/**
	 * Controlador do CadastroCidade @author mpituba
	 */
	@PostMapping("/nova")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return nova(cidade);
		}
		
		try {
			/**salva e verifica se existe cidade cadastrada para o estado no banco e
			   se necessário, lançará um exceção para validação. @author mpituba */
			cadastroCidadeService.salvar(cidade);
		
		}catch(NomeCidadeJaCadastradaException e) {
			
			result.rejectValue("nome",e.getMessage(), e.getMessage());
			
			return nova(cidade);
		}
		
		
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		
		return new ModelAndView("redirect:/cidades/nova");
	}
	
	
}




