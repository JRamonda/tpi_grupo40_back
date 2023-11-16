package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Tarifa;
import com.frc.utn.grupo40.Alquileres.Repositories.ITarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarifasServiceImpl implements ITarifasService{

    @Autowired
    private ITarifaRepository repositoryTarifa;
    @Override
    public Tarifa findById(int id) {
        Tarifa tarifa = repositoryTarifa.findById(id);
        return  tarifa;
    }

    @Override public Tarifa BuscarTarifaFecha(char definicion, int diaMes, int mes, int anio) {
        return repositoryTarifa.findByDefinicionAndDiaMesAndMesAndAnio(definicion, diaMes, mes, anio);
    }

    @Override public Tarifa BuscarTarifaDiaSemana(int diaSemana) {
        return repositoryTarifa.findByDiaSemana(diaSemana);
    }



}
