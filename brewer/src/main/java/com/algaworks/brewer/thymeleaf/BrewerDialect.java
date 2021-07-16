package com.algaworks.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.algaworks.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import com.algaworks.brewer.thymeleaf.processor.MessageElementTagProcessor;



public class BrewerDialect extends AbstractProcessorDialect{
	
	//Construtor da classe herdada
	public BrewerDialect() {
		super("AlgaWorks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
				
		final Set<IProcessor> processadores = new HashSet<>();
		
		//Processador que inclui brewer:classforerror
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		
		//Processador dos fragmentos das mensagens de sucesso e erro brewer:message.
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		
		return processadores;
	}

}
