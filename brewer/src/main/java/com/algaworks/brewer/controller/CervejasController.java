package com.algaworks.brewer.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import com.algaworks.brewer.repository.Estilos;

@Controller
public class CervejasController {
	
	@Autowired
	private Estilos estilos;
	
	
	@RequestMapping("/cervejas/novo")
	public ModelAndView novo(Cerveja cerveja) {
		
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		return mv;
	}
	
	//Quando for feito um POST em cervejas/novo chamará este método 
	@RequestMapping(value = "cervejas/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model,
			RedirectAttributes attributes) {
		
		
		//Verificador do BindingResult
		//if(result.hasErrors()) {
		//	return novo(cerveja);
		//}
		
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso!");
		System.out.println(">>>>> SKU: " + cerveja.getSku());
		System.out.println(">>>>> Sabor: " + cerveja.getSabor());
		System.out.println(">>>>> Origem: " + cerveja.getOrigem());
		return new ModelAndView("redirect:/cervejas/novo");
	}
	
}
