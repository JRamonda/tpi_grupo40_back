package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Alquiler;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.AlquilerDTO;
import com.frc.utn.grupo40.Alquileres.Repositories.IAlquilerRepository;
import com.frc.utn.grupo40.Alquileres.Services.apis.IconversionMonedas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class AlquilerServiceImpl implements IAlquilerService {

    @Autowired
    private IAlquilerRepository repository;
    private IconversionMonedas conversor;

    public List<Alquiler> FindAll() {
        return repository.findAll();
    }

    public List<Alquiler> FindAllById(List<Integer> ids) {
        return repository.findAllById(ids);
    }



    @Override
    public Alquiler terminarAlquiler(AlquilerDTO alquiler) {

        // validar que exista un alquiler a terminar

        Alquiler terminar = repository.findById(alquiler.getId());

        if (terminar == null)
        {
            Alquiler error = new Alquiler();
            error.setId(0);
            error.setIdCliente("no se pudo encontrar el alquiler");
            return error;
        }



        String[] monedas = {"EUR", "CLP", "BRL", "COP", "PEN", "GBP"};
        String[] conversion = new String[2];

// si la moneda no es pesos
        if (alquiler.getMoneda() != null) {

            // si la moneda se puede convertir dentro de las permitidas
            if (Arrays.stream(monedas).anyMatch(moneda -> alquiler.getMoneda() == moneda)) {
                conversion = conversor.conversion(alquiler.getMoneda(), alquiler.getMonto());
            }
            // si la moneda no está dentro de las permitidas
            else
            {
                Alquiler error = new Alquiler();
                error.setId(0);
                error.setIdCliente("la moneda ingresada no corresponde con las permitidas en el sistema ");
                return error;
            }

            // si el conversor pudo convertir bien a la moneda
            if (Double.parseDouble(conversion[1]) != 0) {
                terminar.setEstado(2);
                terminar.setMonto(Double.parseDouble(conversion[1]));
                repository.save(terminar);

            }
            // si el conversor tuvo un problema
            else {
               Alquiler error = new Alquiler();
                error.setIdCliente(conversion[0]);
            }
        }
        /// si la moneda es en pesos

        else {
            terminar.setEstado(2);
            terminar.setMonto(Double.parseDouble(conversion[1]));
            repository.save(terminar);

        }


        return terminar;
    }
}
