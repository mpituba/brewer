package com.algaworks.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.Cidades;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.filter.CidadeFilter;
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
	@Cacheable("cidades")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade>  pesquisarPorCodigoEstado(
	       @RequestParam(name= "estado", defaultValue = "-1")Long codigoEstado){
		
		//Lentidão no sistema para aparecer o icome do miniloading
		try {
			Thread.sleep(1000);
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
			
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			
			return nova(cidade);
		}
		
		
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		
		return new ModelAndView("redirect:/cidades/nova");
	}
	
	/**
	 * Controlador de PesquisasCidades
	 * @param cidadeFilter - Modelo do Filtro passado para a pesquisa da página.
	 * @param result - Binding das validações. 
	 * @param pageable - Objeto responsável pela paginação da página de pesquisa.
	 * @param httpServletRequest  - Objeto Servlet.
	 * @PageableDefault(size = 2) - Parâmetro responsável pelo número de registros
	 * por página, aqui são dois registros por página.
	 * PageWrapper - Envelopador de filtros genérico neste caso cidade e seus parâmetros.
	 * @author mpituba
	 */
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter, BindingResult result, 
			@PageableDefault(size = 7) Pageable pageable, HttpServletRequest httpServletRequest ) {
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");
		mv.addObject("estados", estados.findAll());
		
		
		//System.out.println(">>>>> PageNumber : " + pageable.getPageNumber());
		//System.out.println(">>>>> PageSize   : " + pageable.getPageSize());
		
		//Instanciado o PageWrapper		
		PageWrapper <Cidade> paginaWrapper = new PageWrapper<> (cidades.filtrar(cidadeFilter, pageable),
				httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	
}




