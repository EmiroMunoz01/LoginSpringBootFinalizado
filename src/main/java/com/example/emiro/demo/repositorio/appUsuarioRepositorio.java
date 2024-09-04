package com.example.emiro.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.emiro.demo.modelos.appUsuario;

public interface appUsuarioRepositorio extends JpaRepository <appUsuario, Integer>{
    public appUsuario findByEmail(String email);
}