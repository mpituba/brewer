package com.algaworks.brewer.model;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_grupo")
public class UsuarioGrupo {
	
	@EmbeddedId
	private UsuarioGrupoId id;

	
	//Getters and Setters
	
	public UsuarioGrupoId getId() {
		return id;
	}

	public void setId(UsuarioGrupoId id) {
		this.id = id;
	}
	
	//Equals and hashCode
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioGrupo other = (UsuarioGrupo) obj;
		return Objects.equals(id, other.id);
	}
	

	
}

