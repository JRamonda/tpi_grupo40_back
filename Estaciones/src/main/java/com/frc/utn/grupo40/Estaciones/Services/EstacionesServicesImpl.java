package com.frc.utn.grupo40.Estaciones.Services;

import com.frc.utn.grupo40.Estaciones.Entities.Estacion;
import com.frc.utn.grupo40.Estaciones.Repositories.IEstacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstacionesServicesImpl implements IEstacionesService {

    @Autowired
    private IEstacionesRepository repository;

    public List<Estacion> FindAll(){
        return repository.findAll();
    }

    public Estacion findClosest(double lat, double lon){
        List<Estacion> estaciones = repository.findAll();
        Estacion closest = estaciones.get(0);
        double minDist = Double.MAX_VALUE;
        double lat_origen = Math.toRadians(lat);
        double lon_origen = Math.toRadians(lon);
        for (Estacion e : estaciones) {
            // formula harvesine para determinar distancias con latitud y longitud
            double lat_destino = Math.toRadians(e.getLatitud());
            double lon_destino = Math.toRadians(e.getLongitud());
            double dlon = lon_destino - lon_origen;
            double dlat = lat_destino - lat_origen;
            double a = Math.pow(Math.sin(dlat / 2), 2)
                    + Math.cos(lat_origen) * Math.cos(lat_destino)
                    * Math.pow(Math.sin(dlon / 2),2);
            double c = 2 * Math.asin(Math.sqrt(a));
            double r = 6371; // radio de la tierra en km
            double dist = c * r;
            //double dist = Math.sqrt(Math.pow(e.getLatitud() - lat, 2) + Math.pow(e.getLongitud() - lon, 2));
            if (dist < minDist) {
                minDist = dist;
                closest = e;
            }
        }
        return closest;
    }

    @Override
    public Estacion create( Estacion estacion) {
        System.out.println("Estacion en el servicio:");
        System.out.println(estacion.toString());
        return repository.save(estacion);
    }

}
