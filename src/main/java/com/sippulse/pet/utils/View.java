package com.sippulse.pet.utils;

/**
 * Classe que contem interfaces utilizadas pelo Jackson. Essas interfaces
 * permitem configurar quais partes da entidade serão devolvidas em um JSON,
 * evitando a ocorrência de referências cíclicas.
 * 
 * @author schmitt
 *
 */
public class View {

	public interface Basic {
	}

	public interface ListaPet {
	}

	public interface ClienteDoPet {
	}

	public interface VeterinarioDaConsulta {
	}

	public interface PetDaConsulta {
	}

	public interface PetComCliente extends Basic, ClienteDoPet {
	}

	public interface ClienteComPet extends Basic, ListaPet {
	}

	public interface ConsultaComPetVeterinario extends Basic, VeterinarioDaConsulta, PetDaConsulta, ClienteDoPet {
	}

}
