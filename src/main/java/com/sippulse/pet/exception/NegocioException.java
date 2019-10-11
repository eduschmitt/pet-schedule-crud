package com.sippulse.pet.exception;

public class NegocioException extends Exception {


	private static final long serialVersionUID = -4945966094631068512L;
	private TipoExcecao tipoExcecao;
	
	public NegocioException(String mensagem, TipoExcecao tipoExcecao) {
		super(mensagem);
		this.tipoExcecao = tipoExcecao;
	}
	
	public TipoExcecao getTipoExcecao() {
		return tipoExcecao;
	}
	
	public enum TipoExcecao {
		VALIDACAO_CAMPOS,
		REGISTRO_NAO_ENCONTRADO
	}
}
