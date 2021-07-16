package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring4.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;


/**
 * Classe que cria um Processador customizado para o Thymeleaf, com o 
 * objetivo de incluir código específico que será processado pelo Thymeleaf
 * via Tag extendida.
 * 
 * @author mpituba
 */
public class ClassForErrorAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String NOME_ATRIBUTO = "classforerror";
	private static final int PRECEDENCIA = 1000;
	
	public ClassForErrorAttributeTagProcessor(String dialectPrefix) {
		//classforerror é o nome do atributo novo usado pelo dialeto
		super(TemplateMode.HTML, dialectPrefix, null, false, NOME_ATRIBUTO,
			true, PRECEDENCIA, true);
	}
	
	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		
		boolean temErro = FieldUtils.hasErrors(context, attributeValue);
		
		if(temErro) {
			//Resgata as classes existentes
			String classesExistentes = tag.getAttributeValue("class");
			/**
			 * SetAttribute pega o que tem class e substitui pelo que vem depois dele.
			 * Obs. Sem o espaço no " has-error não funciona, pois será concatenado junto
			 * com a string anterior!"
			 */
			structureHandler.setAttribute("class", classesExistentes + " has-error");
		}
		
		
		
	}

}
