package com.sippulse.pet.to;


public class BaseTO {
	
	private String mensagem;
	
	public Boolean getErro() {
		return getMensagem() != null;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
