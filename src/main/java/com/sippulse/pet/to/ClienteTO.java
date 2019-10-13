package com.sippulse.pet.to;

import com.sippulse.pet.entity.Cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ClienteTO extends BaseTO {
	private Cliente cliente; 
}
