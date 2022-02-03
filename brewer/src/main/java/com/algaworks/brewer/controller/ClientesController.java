package com.algaworks.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.service.CadastroClienteService;
import com.algaworks.brewer.service.exception.CpfCnpjClienteJaCadastradoException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired 
	CadastroClienteService cadastroClienteService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());
		//System.out.println(estados.findAll());
		return mv;
	}
	
	//Quando for feito um POST em clientes/novo chamará este método 
	@PostMapping(value = "/novo")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		
		//Verificador do BindingResult
		if(result.hasErrors()) {
			return novo(cliente);
		}
		
		try {
			cadastroClienteService.salvar(cliente);
		}catch(CpfCnpjClienteJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			//após rejeitar precida do return para não continuar o fluxo
			return novo(cliente);
		}
		
		
		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		return new ModelAndView ("redirect:/clientes/novo");
	}
	
	/**
	 * Controlador de PesquisasClientes
	 * @param clienteFilter - Modelo do Filtro passado para a pesquisa da página.
	 * @param result - Binding das validações. 
	 * @param pageable - Objeto responsável pela paginação da página de pesquisa.
	 * @param httpServletRequest  - Objeto Servlet.
	 * @PageableDefault(size = 2) - Parâmetro responsável pelo número de registros
	 * por página, aqui são dois registros por página.
	 * PageWrapper - Envelopador do filtro de clientes e seus parâmetros.
	 * @author mpituba
	 */
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter, BindingResult result, 
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest ) {
		ModelAndView mv = new ModelAndView("cliente/PesquisaClientes");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		
		//Instanciado o PageWrapper		
		PageWrapper <Cliente> paginaWrapper = new PageWrapper<> (clientes.filtrar(clienteFilter, pageable),
				httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	/* Esta requisição gera um erro que é tratado abaixo */
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cliente> pesquisar(String nome){
		validarTamanhoNome(nome);
		return clientes.findByNomeStartingWithIgnoreCase(nome);
	}

	private void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
		
	}
	
	/* Este bloco trata um erro 500 gerado na requisição acima quando esta não recebe pelo 
	 * menos 3 caracteres */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
	
}
