package com.frc.utn.grupo40.Estaciones.Services;

import com.frc.utn.grupo40.Estaciones.Entities.Estacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@SpringBootTest

class EstacionesServicesImplTest {
    @Autowired
    private IEstacionesService estacionesService;

    @Test
    void findClosest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Estacion estacion1 = new Estacion(18,"Gral. Bustos",LocalDateTime.parse("2023-10-03 21:40:12", formatter),-31.389057292916398,-64.17237351083637
);

        Estacion cercana = estacionesService.findClosest(-31.37,-64.16);

        assertEquals(cercana,estacion1);
    }


}