package com.algaworks.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Estilo;

@Repository
public interface Estilos extends JpaRepository<Estilo, Long>{
	
	//Usado para verificar se já existe um estilo cadastrado
	public Optional<Estilo> findByNomeIgnoreCase(String nome);
	
}
