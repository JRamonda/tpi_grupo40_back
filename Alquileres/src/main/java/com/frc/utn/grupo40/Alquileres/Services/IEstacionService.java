package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Estacion;

import java.util.List;

public interface IEstacionService {

    Estacion findById(int id);

    List<Estacion> getAll();
}
