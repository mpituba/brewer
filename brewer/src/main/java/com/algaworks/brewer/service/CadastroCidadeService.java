package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.Cidades;
import com.algaworks.brewer.service.exception.NomeCidadeJaCadastradaException;

/** Esta classe é utilizada para o salvamento de dados vindos do cadastro de
 * cidades  @author mpituba 
 * */
@Service
public class CadastroCidadeService {
	
	@Autowired
	private Cidades cidades;
	
	@Transactional
	public void salvar(Cidade cidade) {
		
		//Faz busca da Cidade por nome e Estado retornando um Optional ao chamar repository
		Optional<Cidade> cidadeExistente = cidades.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		
		/**
		 * Lançamento de exceção utilizada como validação de salvamento da tela de 
		 * cadastro de cidade, para quando uma cidade já existe em um estado no banco.
		 * @author mpituba
		 */
		if (cidadeExistente.isPresent()) {
			throw new NomeCidadeJaCadastradaException("Nome de cidade já cadastrado");
		}
		
		//Se chegar aqui, salva no banco a cidade.
		cidades.save(cidade);
		
	}
	
}
