package com.sippulse.pet.security;

/**
 * Bean que representa as credenciais de acesso que devme ser informadas ao acessar o /login.
 * @author schmitt
 *
 */
public class AccountCredentials {
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
