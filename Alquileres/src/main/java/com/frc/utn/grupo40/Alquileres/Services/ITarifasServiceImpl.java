package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Tarifas;
import com.frc.utn.grupo40.Alquileres.Repositories.ITarifasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITarifasServiceImpl implements ITarifasService{

    @Autowired
    private ITarifasRepository repositoryTarifa;
    @Override
    public Tarifas findById(int id) {
        Tarifas tarifa = repositoryTarifa.findById(id);
        return  tarifa;
    }
}
