package com.frc.utn.grupo40.Alquileres.Repositories;

import com.frc.utn.grupo40.Alquileres.Entities.Tarifas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITarifasRepository extends JpaRepository<Tarifas, Integer > {

    Tarifas findById(int id);
}
