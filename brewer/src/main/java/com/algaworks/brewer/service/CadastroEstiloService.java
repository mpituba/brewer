package com.algaworks.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;

@Service
public class CadastroEstiloService {
	
	//Injeção do estilos repository
	@Autowired
	private Estilos estilos;
	
	//Método que salva o estilo no banco de dados
	@Transactional
	public void salvar(Estilo estilo) {
		
		estilos.save(estilo);
		
	}
}
