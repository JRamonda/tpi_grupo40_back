package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Tarifa;


public interface ITarifasService {

    Tarifa findById(int id);

    Tarifa BuscarTarifaFecha(char definicion, int diaMes, int mes, int anio);

    Tarifa BuscarTarifaDiaSemana(int diaSemana);

}
