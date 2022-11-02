package mcereja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mcereja.domain.Autor;
import mcereja.repository.Autores;
import mcereja.service.exceptions.AutorExistenteException;
import mcereja.service.exceptions.AutorNaoEncontradoException;

@Service
public class AutoresService {

	@Autowired
	private Autores autores;
	
	public List<Autor> listaTodos() {
		return autores.findAll();
	}
	
	public Autor salvar(Autor autor) {
		if ( autor.getId() != null ) {
			if ( autores.findById(autor.getId()) != null ) {
				throw new AutorExistenteException("Autor já existe");
			}
		}
		
		return autores.save(autor);
		
	}

	public Autor buscar(Long id) {
		Autor autor = autores.findById(id).get();
		
		if (autor == null) {
			throw new AutorNaoEncontradoException("O autor não foi encontrado");
		}
		
		return autor;
	}
	
}
