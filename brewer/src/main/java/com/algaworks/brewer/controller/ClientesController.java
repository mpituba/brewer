package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Cliente;

@Controller
public class ClientesController {
	
	@RequestMapping("/clientes/novo")
	public String novo(Cliente cliente) {
		return "cliente/CadastroCliente";
	}
	
	//Quando for feito um POST em clientes/novo chamará este método 
	@RequestMapping(value = "clientes/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Cliente cliente, BindingResult result, Model model,
			RedirectAttributes attributes) {
		
		//Verificador do BindingResult
		if(result.hasErrors()) {
			return novo(cliente);
		}
		
		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		return "redirect:/clientes/novo";
	}
	
	
}
