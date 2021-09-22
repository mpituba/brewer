package com.algaworks.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import com.algaworks.brewer.model.Usuario;

public interface UsuariosQueries {
	//Chamada ao banco da tela de login
	public Optional<Usuario> porEmailEAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);
}
