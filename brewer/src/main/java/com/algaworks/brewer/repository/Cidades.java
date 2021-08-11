package com.algaworks.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.model.Estado;

public interface Cidades extends JpaRepository<Cidade, Long>{
	
	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
	//Faz a busca para a tela de cadastro da cidade
	public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado); 
}
