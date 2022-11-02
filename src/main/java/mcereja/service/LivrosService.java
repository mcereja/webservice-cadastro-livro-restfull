package mcereja.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import mcereja.domain.Comentario;
import mcereja.domain.Livro;
import mcereja.repository.Comentarios;
import mcereja.repository.Livros;
import mcereja.service.exceptions.LivroNaoEncontradoException;

@Service
public class LivrosService {

	@Autowired
	private Livros livros;
	
	@Autowired
	private Comentarios comentarios;
	
	public List<Livro> listarTodos() {
		return livros.findAll();
	}
	
	public Livro buscar(Long id) {
		Livro livro = null;
		livro = livros.findById(id).get();
		
		if ( livro == null ) {
			throw new LivroNaoEncontradoException("Atenção !!! Livro não encontrado.");
		}
		
		return livro;
	}
	
	public Livro salvar(Livro livro) {
		livro.setId(null);
		return livros.save(livro);
	}
	
	public void remover(Long id) {
		try {
			livros.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new LivroNaoEncontradoException("Livro não encontrado para remoção.");
		}
	}
	
	public void atualizar(Livro livro) {
		verificarExistencia(livro);
		livros.save(livro);
	}
	
	private void verificarExistencia(Livro livro) {
		buscar(livro.getId());
	}
	
	public Comentario salvarComentario(Long idLivro, Comentario comentario) {
		Livro livro = buscar(idLivro);
		comentario.setLivro(livro);
		comentario.setData(new Date());
		
		return comentarios.save(comentario);
	}
	
	public List<Comentario> listarComentarios(Long idLivro) {
		Livro livro = buscar(idLivro);
		
		return livro.getComentarios();
		
	}
}
