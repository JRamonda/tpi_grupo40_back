package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Estacion;
import com.frc.utn.grupo40.Alquileres.Repositories.IEstacionesRepository;
import org.springframework.stereotype.Service;

@Service
public class IEstacioneServiceImpl implements IEstacionesService{

    private IEstacionesRepository repository;

    public IEstacioneServiceImpl(IEstacionesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Estacion findById(int id) {
        Estacion estacion = repository.findById(id);
        return  estacion;
    }
}
