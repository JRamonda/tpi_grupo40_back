package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Alquiler;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.AlquilerDTO;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.CrearAlquilerDTO;
import com.frc.utn.grupo40.Alquileres.Entities.Estacion;
import com.frc.utn.grupo40.Alquileres.Entities.Tarifas;
import com.frc.utn.grupo40.Alquileres.Repositories.IAlquilerRepository;
import com.frc.utn.grupo40.Alquileres.Repositories.IEstacionesRepository;
import com.frc.utn.grupo40.Alquileres.Services.apis.IconversionMonedas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@Service
public class AlquilerServiceImpl implements IAlquilerService {

    @Autowired
    private IAlquilerRepository repository;
    @Autowired
    private IconversionMonedas conversor;

    @Autowired
    private IEstacionesService servicioEstaciones;

    @Autowired
    private ITarifasService servicioTarifas ;


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

        Alquiler error = new Alquiler();

        if (terminar == null)
        {
            error.setId(0);
            error.setIdCliente("no se pudo encontrar el alquiler");
            return error;
        }



        String[] monedas = {"EUR", "CLP", "BRL", "COP", "PEN", "GBP", "USD"};
        String[] conversion = new String[2];



// si la moneda no es pesos
        if (alquiler.getMoneda() != null) {
            // si la moneda se puede convertir dentro de las permitidas
            if (Arrays.stream(monedas).anyMatch(moneda -> alquiler.getMoneda().equals( moneda))) {
                conversion = conversor.conversion(alquiler.getMoneda(), terminar.getMonto());

            }
            // si la moneda no está dentro de las permitidas
            else
            {

                error.setId(0);
                error.setIdCliente("la moneda ingresada no corresponde con las permitidas en el sistema ");
                return error;
            }

            // si el conversor pudo convertir bien a la moneda
            if (Double.parseDouble(conversion[1]) != 0) {

                    terminar.setEstado(2);
                    Estacion estacion = servicioEstaciones.findById(alquiler.getIdEntidadEntrega());
                    terminar.setEstacionDevolucion(estacion);
                    terminar.setMonto(Double.parseDouble(conversion[1]));
                    terminar.setFechaHoraDevolucion(LocalDateTime.now().toString());
                    repository.save(terminar);

            }
            // si el conversor tuvo un problema
            else {
                error.setIdCliente(conversion[0]);
                error.setIdCliente("error con el conversor ");
            }
        }
        /// si la moneda es en pesos
        else {

            terminar.setEstado(2);
            Estacion estacion = servicioEstaciones.findById(alquiler.getIdEntidadEntrega());
            terminar.setEstacionDevolucion(estacion);
            terminar.setMonto(Double.parseDouble(conversion[1]));
            terminar.setFechaHoraDevolucion(LocalDateTime.now().toString());
            repository.save(terminar);

        }



        return terminar;
    }

    @Override
    public Alquiler crear(CrearAlquilerDTO crear) {
        Alquiler nuevo = new Alquiler();
        nuevo.setIdCliente(crear.getIdCliente());

        Estacion estacion = servicioEstaciones.findById(crear.getIdEstacionRetiro());
        nuevo.setEstacionRetiro(estacion);

        nuevo.setFechaHoraRetiro(LocalDateTime.now().toString());

        Tarifas tarifas = servicioTarifas.findById(crear.getTarifa());

        nuevo.setIdTarifa(tarifas);
        //try catch

        Alquiler creado = repository.save(nuevo);

        return creado;
    }






}
