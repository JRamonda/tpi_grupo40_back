package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Estacion;
import com.frc.utn.grupo40.Alquileres.Repositories.IEstacionesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacionServiceImpl implements IEstacionService {

    private IEstacionesRepository repository;

    public EstacionServiceImpl(IEstacionesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Estacion findById(int id) {
        Estacion estacion = repository.findById(id);
        return  estacion;
    }

    @Override
    public List<Estacion> getAll() {
        List<Estacion> estaciones = repository.findAll();
        return estaciones;
    }


}
