package com.frc.utn.grupo40.Estaciones.Services;

import com.frc.utn.grupo40.Estaciones.Entities.Tarifas;
import com.frc.utn.grupo40.Estaciones.Repositories.ITarifasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifaServicesImpl {

    @Autowired
    private ITarifasRepository repository;

    public List<Tarifas> findAll() {
        List<Tarifas> Tarifas = repository.findAll();
        return Tarifas;
    }

}
