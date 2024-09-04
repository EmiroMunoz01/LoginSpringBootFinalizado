package com.example.emiro.demo.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.emiro.demo.modelos.appUsuario;
import com.example.emiro.demo.repositorio.appUsuarioRepositorio;


@Service
public class appUsuarioServicio implements UserDetailsService {
    @Autowired
    private appUsuarioRepositorio repositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        appUsuario usuario = repositorio.findByEmail(email);

        if (usuario != null) {
            var usuarioSpring = User.withUsername(usuario.getEmail())
                    .password(usuario.getClave())
                    .build();

            return usuarioSpring;
        }


        return null;
    }
}
