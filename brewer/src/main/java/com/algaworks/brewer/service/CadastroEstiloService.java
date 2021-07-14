package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {
	
	//Injeção do estilos repository
	@Autowired
	private Estilos estilos;
	
	//Método que salva o estilo no banco de dados
	@Transactional
	public Estilo salvar(Estilo estilo) {
		
		//Verifica se o estilo já existe e lança exceção para erro na tela
		Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());
		if (estiloOptional.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		}
				
		//Salva o estilo e retorna dados
		return estilos.saveAndFlush(estilo);
		
	}
	
	
	
}
