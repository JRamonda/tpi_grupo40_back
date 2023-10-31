package com.frc.utn.grupo40.Estaciones.Repositories;

import com.frc.utn.grupo40.Estaciones.Entities.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstacionesRepository extends JpaRepository<Estacion, Integer> {
}
