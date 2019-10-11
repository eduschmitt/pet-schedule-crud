package com.sippulse.pet.exception;

public class NegocioException extends Exception {

	private static final long serialVersionUID = -4945966094631068512L;
	
	public NegocioException() {	
		super();
	}
	
	public NegocioException(String mensagem) {
		super(mensagem);
	}

}
