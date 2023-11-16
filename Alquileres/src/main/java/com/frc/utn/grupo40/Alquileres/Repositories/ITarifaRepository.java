package com.frc.utn.grupo40.Alquileres.Repositories;

import com.frc.utn.grupo40.Alquileres.Entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITarifaRepository extends JpaRepository<Tarifa, Integer > {

    Tarifa findById(int id);

    Tarifa findByDefinicionAndDiaMesAndMesAndAnio(char definicion, int diaMes, int mes, int anio);

    Tarifa findByDiaSemana(int diaSemana);

}
