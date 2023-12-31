package com.frc.utn.grupo40.Estaciones.Services;

import com.frc.utn.grupo40.Estaciones.Entities.Estacion;

import java.util.List;


public interface IEstacionesService {

    List<Estacion> FindAll();

    Estacion findClosest(double lat, double lon);
    // crear una nueva estacion:
    Estacion create(Estacion estacion);


}
