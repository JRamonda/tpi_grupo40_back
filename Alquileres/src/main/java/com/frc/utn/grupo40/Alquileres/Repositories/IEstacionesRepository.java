package com.frc.utn.grupo40.Alquileres.Repositories;

import com.frc.utn.grupo40.Alquileres.Entities.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstacionesRepository extends JpaRepository<Estacion, Integer> {

    Estacion findById(int id);
}
