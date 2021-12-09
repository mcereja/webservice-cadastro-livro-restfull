package br.com.imaster7.socialbooks.service.exceptions;

public class AutorNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = -8791097582829206130L;

	public AutorNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public AutorNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	

}
