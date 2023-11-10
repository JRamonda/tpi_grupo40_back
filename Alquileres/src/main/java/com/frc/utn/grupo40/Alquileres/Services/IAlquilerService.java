package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Alquiler;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.AlquilerDTO;

import java.util.List;


public interface IAlquilerService {

    List<Alquiler> FindAll();

    List<Alquiler> FindAllById(List<Integer> ids);

    Alquiler terminarAlquiler(AlquilerDTO alquiler);
}
