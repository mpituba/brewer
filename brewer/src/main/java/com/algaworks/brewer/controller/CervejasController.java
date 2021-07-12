package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;

@Controller
public class CervejasController {
	
	@Autowired
	private Cervejas cervejas;
	
	//Usado para o Logger
	private static final Logger logger = LoggerFactory.getLogger(CervejasController.class);
	
	@RequestMapping("/cervejas/novo")
	public String novo(Cerveja cerveja) {
		//Repository fazendo um seleciona todos
		cervejas.findAll();
		
		if(logger.isDebugEnabled()) {
			//Para este log deve-se ter level="debug" no log4j2.xml e o toString no model
			logger.info("Log Debug - O objeto cervaja eh: " + cerveja);
		}
		
		logger.info("Aqui é um log nível info!");
		logger.error("Aqui é um log nível error!");
		
		return "cerveja/CadastroCerveja";
	}
	
	//Quando for feito um POST em cervejas/novo chamará este método 
	@RequestMapping(value = "cervejas/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model,
			RedirectAttributes attributes) {
		
		//Verificador do BindingResult
		if(result.hasErrors()) {
			return novo(cerveja);
		}
		
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso!");
		return "redirect:/cervejas/novo";
	}
	
	/**	
	 * Exemplo de controller para página de protótipo
	 * @RequestMapping("/cervejas/cadastro")
		public String cadastro() {
			return "cerveja/cadastro-produto";
		}
	**/
}
