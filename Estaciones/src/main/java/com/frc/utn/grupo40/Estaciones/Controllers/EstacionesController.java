package com.frc.utn.grupo40.Estaciones.Controllers;

import com.frc.utn.grupo40.Estaciones.Entities.Estacion;
import com.frc.utn.grupo40.Estaciones.Services.IEstacionesService;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/estaciones")
    public Estacion createEstacion(@RequestBody Estacion estacion){
        System.out.println("Estacion recibida:");
        System.out.println(estacion.toString());
        Estacion estacionCreada = estacionesservice.create(estacion);
        return estacionCreada;
    }
    @GetMapping("/estaciones/cercana")
    public Estacion getEstacionCercana(double lat, double lon){
        return estacionesservice.findClosest(lat, lon);
    }
}
