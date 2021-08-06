package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.service.CadastroClienteService;

import com.algaworks.brewer.service.exception.CpfCnpjClienteJaCadastradoException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private Estados estados;
	
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
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, Model model,
			RedirectAttributes attributes) {
		
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
	
	
}
