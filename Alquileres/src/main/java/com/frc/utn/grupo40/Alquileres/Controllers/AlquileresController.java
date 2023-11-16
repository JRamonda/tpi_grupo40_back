package com.frc.utn.grupo40.Alquileres.Controllers;

import com.frc.utn.grupo40.Alquileres.Entities.Alquiler;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.AlquilerDTO;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.CrearAlquilerDTO;
import com.frc.utn.grupo40.Alquileres.Services.IAlquilerService;
import com.frc.utn.grupo40.Alquileres.Services.apis.IconversionMonedas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/terminarAlquiler")
    public ResponseEntity<Alquiler> terminarAlquiler(@RequestBody AlquilerDTO terminar)
    {
            Alquiler Alq = alquileresservice.terminarAlquiler(terminar);

        if(terminar.getIdPuntoEntrega() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proporcione una Estacion");
        }


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Alq);
    }


    @PostMapping("/nuevo")
    public ResponseEntity<Alquiler> crearAlquiler(@RequestBody CrearAlquilerDTO nuevo)
    {
        Alquiler aCrear = alquileresservice.crear(nuevo);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(aCrear);
    }
}
