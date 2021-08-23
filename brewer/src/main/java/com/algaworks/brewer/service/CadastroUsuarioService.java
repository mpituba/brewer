package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Usuarios;
import com.algaworks.brewer.service.exception.EmailUsuarioJaCadastradoException;


/**
 * Serviço utilizado para salvar transacionalmente um usuario e
 * publicar o evento UsuarioSalvaEvent. @author mpituba
 */

@Service
public class CadastroUsuarioService {
	
	//Injeção de dependencia do repositório de usuarios
	@Autowired
	private Usuarios usuarios;
	
		
	//Salva a transação do usuario.
	@Transactional
	public void salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarios.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent()) {
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		}
			
		usuarios.save(usuario);
			
	}
		
}
