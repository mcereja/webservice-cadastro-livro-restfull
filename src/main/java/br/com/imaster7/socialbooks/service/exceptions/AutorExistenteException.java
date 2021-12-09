package br.com.imaster7.socialbooks.service.exceptions;

public class AutorExistenteException extends RuntimeException {
	private static final long serialVersionUID = 4084641674478355708L;

	public AutorExistenteException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public AutorExistenteException(String mensagem) {
		super(mensagem);
	}
	
	

}
