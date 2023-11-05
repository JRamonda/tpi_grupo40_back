package com.frc.utn.grupo40.Alquileres.Repositories;

import com.frc.utn.grupo40.Alquileres.Entities.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAlquilerRepository extends JpaRepository<Alquiler, Integer> {

}
