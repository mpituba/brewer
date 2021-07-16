package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Classe para a criação de um novo elemento do Thymeleaf.
 * 
 * @author mpituba
 */
public class MessageElementTagProcessor extends AbstractElementTagProcessor{

	private static final String NOME_TAG = "message";
	private static final int PROCEDENCIA = 1000;
	
	public MessageElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, NOME_TAG, true, null, false, PROCEDENCIA);
		
	}


	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model = modelFactory.createModel();
		
		/**Passa o nome da TAG, o nome do atributo e o valor do atributo,
		 * os dois model abaixo criam os cógigos que chamam os fragmentos
		 * para as mensagens de sucesso e de erros de validação.
		 * @mpituba
		 */
		model.add(modelFactory.createStandaloneElementTag("th:block",
				"th:include", "fragments/MensagemSucesso"));
		
		model.add(modelFactory.createStandaloneElementTag("th:block",
				"th:include", "fragments/MensagensErroValidacao"));
		
		/**Visto o código Thymeleaf precisar ser executado passa-se true
		*se fosse um código html false.
		*@mpituba
		*/
		structureHandler.replaceWith(model, true);
		
	}

}
