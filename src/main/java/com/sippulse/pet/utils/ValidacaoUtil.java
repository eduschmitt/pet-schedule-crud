package com.sippulse.pet.utils;

import com.sippulse.pet.exception.ServiceException;
import com.sippulse.pet.exception.ServiceException.TipoExcecao;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe utilitária para validações.
 * @author schmitt
 *
 */
@Slf4j
public class ValidacaoUtil {
	
	/**
	 * Método para validar se uma String está com valor.
	 * @param s
	 * @return boolean
	 */
	public static boolean isValidString(String s) {
		if (s == null || s.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Método para validar CPF.
	 * @param cpf
	 * @return boolean indicando se é vaĺido ou não.
	 */
	public static boolean isValidCpf(String cpf) {
		if (!cpf.matches("\\d+")) {
			return false;
		}
		
		CPFValidator cpfValidator = new CPFValidator(false);
		if (!cpfValidator.isEligible(String.format("%011d", Long.parseLong(cpf)))) {
			return false;
		}
		
		try {
			cpfValidator.assertValid(String.format("%011d", Long.parseLong(cpf)));			
		} catch (InvalidStateException e) {
			log.error("Erros na validação do CPF informado. {}", e.getInvalidMessages());
			throw new ServiceException("Erros na validação do CPF informado. Erros: " + e.getInvalidMessages(), TipoExcecao.VALIDACAO_CAMPOS);
		}
		
		return true;
	}

}
