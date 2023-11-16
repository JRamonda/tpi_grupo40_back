package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Tarifas;

public interface ITarifasService {

    Tarifas findById(int id);

    Tarifas BuscarTarifaFecha(char definicion, int diaMes, int mes, int anio);

    Tarifas BuscarTarifaDiaSemana(int diaSemana);

}
