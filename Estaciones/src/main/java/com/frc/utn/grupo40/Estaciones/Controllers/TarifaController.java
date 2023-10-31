package com.frc.utn.grupo40.Estaciones.Controllers;

import com.frc.utn.grupo40.Estaciones.Entities.Tarifas;
import com.frc.utn.grupo40.Estaciones.Services.TarifaServicesImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api")
public class TarifaController {

    private TarifaServicesImpl tarifaServices;

    public TarifaController(TarifaServicesImpl tarifaServices) {
        this.tarifaServices = tarifaServices;
    }

    @GetMapping("/tarifas")
    public List<Tarifas> getTarifa(){
        return tarifaServices.findAll();
    }
}
