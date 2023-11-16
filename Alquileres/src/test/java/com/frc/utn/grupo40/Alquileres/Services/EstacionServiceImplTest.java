package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Estacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@SpringBootTest
class EstacionServiceImplTest {

    @Autowired
    private IEstacionService estacionService;

    @Test
    void findById() {
        // Elegimos la primera estacion de la base de datos y creamos una objeto local con los mismos datos
        Estacion eComparar = new Estacion(1,"UTN", LocalDateTime.parse("2023-10-03 21:22:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),-31.442961123175007,-64.19409112111947
        );

        // Buscamos de la base de datos la primera estacion
        Estacion result = estacionService.findById(1);
        // Si son iguales el primer registro de la base de datos debe coincidir con el objeto creado
        assertEquals(eComparar, result);
    }
}