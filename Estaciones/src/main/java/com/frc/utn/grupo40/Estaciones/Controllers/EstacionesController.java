package com.frc.utn.grupo40.Estaciones.Controllers;

import com.frc.utn.grupo40.Estaciones.Entities.Estacion;
import com.frc.utn.grupo40.Estaciones.Services.IEstacionesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EstacionesController {
    private IEstacionesService estacionesservice;
    public EstacionesController(IEstacionesService estacionesservice) {
        this.estacionesservice = estacionesservice;
    }
    @GetMapping("/estaciones")
    public List<Estacion> getEstaciones(){
        return estacionesservice.FindAll();
    }

    @GetMapping("/estaciones/cercana")
    public Estacion getEstacionCercana(double lat, double lon){
        return estacionesservice.findClosest(lat, lon);
    }
}
