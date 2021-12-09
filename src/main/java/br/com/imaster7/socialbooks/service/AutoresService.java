package br.com.imaster7.socialbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.imaster7.socialbooks.domain.Autor;
import br.com.imaster7.socialbooks.repository.Autores;
import br.com.imaster7.socialbooks.service.exceptions.AutorExistenteException;
import br.com.imaster7.socialbooks.service.exceptions.AutorNaoEncontradoException;

@Service
public class AutoresService {

	@Autowired
	private Autores autores;
	
	public List<Autor> listaTodos() {
		return autores.findAll();
	}
	
	public Autor salvar(Autor autor) {
		if ( autor.getId() != null ) {
			if ( autores.findOne(autor.getId()) != null ) {
				throw new AutorExistenteException("Autor já existe");
			}
		}
		
		return autores.save(autor);
		
	}

	public Autor buscar(Long id) {
		Autor autor = autores.findOne(id);
		
		if (autor == null) {
			throw new AutorNaoEncontradoException("O autor não foi encontrado");
		}
		
		return autor;
	}
	
}
