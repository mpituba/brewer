package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Classe para a criação de um novo elemento do Thymeleaf. brewer:order
 * @author mpituba
 */

public class OrderElementTagProcessor extends AbstractElementTagProcessor{

	private static final String NOME_TAG = "order";
	private static final int PROCEDENCIA = 1000;
	
	public OrderElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, NOME_TAG, true, null, false, PROCEDENCIA);
		
	}


	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		
		IModelFactory modelFactory = context.getModelFactory();
		IModel model = modelFactory.createModel();
		
		//Recebe os argumentos procedentes da tag
		IAttribute page = tag.getAttribute("page");
		IAttribute field = tag.getAttribute("field");
		IAttribute text = tag.getAttribute("text");
		
		/**
		 * Recebe os parâmetros page, field e text. O model abaixo cria o cógigo
		 *  que chama o fragmento para a ordenação ascendente e descendente. @mpituba
		 */
		model.add(modelFactory.createStandaloneElementTag("th:block","th:replace",
						String.format("fragments/Ordenacao :: order (%s, %s, '%s')",
						page.getValue(), field.getValue(), text.getValue())));
		
		
		/**
		 * Visto o código Thymeleaf precisar ser executado, passa-se true;
		*  se fosse um código html, false. @mpituba
		*/
		structureHandler.replaceWith(model, true);
		
	}

}
