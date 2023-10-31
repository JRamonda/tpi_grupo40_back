package com.frc.utn.grupo40.Estaciones.Services;

import com.frc.utn.grupo40.Estaciones.Entities.Estacion;
import com.frc.utn.grupo40.Estaciones.Repositories.IEstacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstacionesServicesImpl implements IEstacionesService {

    @Autowired
    private IEstacionesRepository repository;

    public List<Estacion> FindAll(){
        return repository.findAll();
    }

}
