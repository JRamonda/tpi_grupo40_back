package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Alquiler;
import com.frc.utn.grupo40.Alquileres.Repositories.IAlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AlquilerServiceImpl implements IAlquilerService {

    @Autowired
    private IAlquilerRepository repository;

    public List<Alquiler> FindAll(){
        return repository.findAll();
    }

    public List<Alquiler> FindAllById(List<Integer> ids){
        return repository.findAllById(ids);
    }


}