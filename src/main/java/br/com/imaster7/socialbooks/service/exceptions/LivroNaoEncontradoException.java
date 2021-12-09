package br.com.imaster7.socialbooks.service.exceptions;

public class LivroNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = -8791097582829206130L;

	public LivroNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public LivroNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	

}
