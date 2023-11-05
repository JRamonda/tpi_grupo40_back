package com.frc.utn.grupo40.Alquileres.Controllers;

import com.frc.utn.grupo40.Alquileres.Entities.Alquiler;
import com.frc.utn.grupo40.Alquileres.Services.IAlquilerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
public class AlquileresController {
    private IAlquilerService alquileresservice;

    public AlquileresController(IAlquilerService alquileresservice) {
        this.alquileresservice = alquileresservice;
    }

    @GetMapping
    public List<Alquiler> getAlquileres(){
        return alquileresservice.FindAll();
    }

    @GetMapping("/{ids}")
    public List<Alquiler> getAlquileresById(@PathVariable("ids") List<Integer> ids){
        return alquileresservice.FindAllById(ids);
    }
}
