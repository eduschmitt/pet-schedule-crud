package com.sippulse.pet.service;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sippulse.pet.entity.Funcionario;
import com.sippulse.pet.repository.FuncionarioRepository;

/**
 *
 * @author schmitt
 */
@Service
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Funcionario funcionario = funcionarioRepository.findOne(Long.parseLong(username));
        if (funcionario == null) {
            throw new UsernameNotFoundException("Funcionário de CPF '" + username + "' not found");
        }

        return new org.springframework.security.core.userdetails.User(username, funcionario.getSenha(), Collections.emptyList());

    }

}