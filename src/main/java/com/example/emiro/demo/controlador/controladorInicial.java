package com.example.emiro.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controladorInicial {

    @GetMapping({"","/"})
    public String inicial() {
        return "index";
    }

    @GetMapping({"/contacto"})
    public String contacto() {
        return "contacto";
    }
}
