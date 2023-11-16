package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Tarifa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@SpringBootTest

class TarifaServiceImplTest {

    @Autowired

    private ITarifasService tarifaService;

    @Test
    void findById() {

        // Elegimos la primera tarifa de la base de datos y creamos una objeto local con los mismos datos
        Tarifa aComparar = new Tarifa(1,1,'S',1,null ,null ,null ,300,6,80,240
        );

        // Buscamos de la base de datos la primera tarifa
        Tarifa result = tarifaService.findById(1);
        // Si son iguales el primer registro de la base de datos debe coincidir con el objeto creado
        assertEquals(aComparar, result);

    }

    @Test
    void buscarTarifaFecha() {

        int dia = 2;
        int mes = 11;
        int anio = 2023;

        Tarifa descuento = tarifaService.BuscarTarifaFecha('C',dia,mes,anio);

        assertEquals(82.5, descuento.getMontoKm());
    }

    @Test
    void buscarTarifaDiaSemana() {

        int dia = 2;

        Tarifa semana = tarifaService.BuscarTarifaDiaSemana(2);

        assertEquals(2, semana.getDiaSemana());
    }
}