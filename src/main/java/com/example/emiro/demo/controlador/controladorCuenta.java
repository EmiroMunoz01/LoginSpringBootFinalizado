package com.example.emiro.demo.controlador;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.emiro.demo.modelos.appUsuario;
import com.example.emiro.demo.modelos.registroDTO;
import com.example.emiro.demo.repositorio.appUsuarioRepositorio;

import jakarta.validation.Valid;

@Controller
public class controladorCuenta {

    @Autowired
    private appUsuarioRepositorio repositorio;

    @GetMapping("/registro")
    public String registro(Model model) {
        registroDTO RegistroDTO = new registroDTO();
        model.addAttribute(RegistroDTO);
        model.addAttribute("RegistroExitoso", false);
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(
            Model model,
            @Valid @ModelAttribute registroDTO RegistroDTO,
            BindingResult result) {

        if (!RegistroDTO.getClave().equals(RegistroDTO.getConfirmarClave())) {
            result.addError(
                    new FieldError("RegistroDTO", "confirmarClave", "Clave y confirmar clave no coinciden")

            );
        }

        appUsuario appUsuario = repositorio.findByEmail(RegistroDTO.getEmail());
        if (appUsuario != null) {
            result.addError(new FieldError("RegistroDTO", "email", "El correo ingresado ya esta en uso"));
        }

        if (result.hasErrors()) {
            return "registro";
        }


        try {
            
            //creacion de una nueva cuenta
            var bCryptEnconder = new BCryptPasswordEncoder();

            appUsuario nuevoUsuario = new appUsuario();
            nuevoUsuario.setNombre(RegistroDTO.getNombre());
            nuevoUsuario.setApellido(RegistroDTO.getApellido());
            nuevoUsuario.setEmail(RegistroDTO.getEmail());
            nuevoUsuario.setTelefono(RegistroDTO.getTelefono());
            nuevoUsuario.setDireccion(RegistroDTO.getDireccion());
            nuevoUsuario.setFechaCreacion(new Date());
            nuevoUsuario.setClave(bCryptEnconder.encode(RegistroDTO.getClave()));

            repositorio.save(nuevoUsuario);

            model.addAttribute("RegistroDTO", new registroDTO());
            model.addAttribute("RegistroExitoso", true);

            
        } catch (Exception e) {
            result.addError(new FieldError("RegistroDTO", "Nombre", e.getMessage()));
        }

        return "registro";
    }

}
