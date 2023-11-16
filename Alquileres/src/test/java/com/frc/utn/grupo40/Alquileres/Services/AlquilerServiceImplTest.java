package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Alquiler;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.AlquilerDTO;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.CrearAlquilerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@SpringBootTest
class AlquilerServiceImplTest {

    @Autowired
    private IAlquilerService alquilerService;

    @Test
    void terminarAlquiler() {

        ///alquiler creado
        CrearAlquilerDTO crearDto = new CrearAlquilerDTO("34",1);

        alquilerService.crear(crearDto);

        // finalizar alquiler

        int id = alquilerService.maxId();
        AlquilerDTO terminarDto = new AlquilerDTO(id,"34",null, 5);


        Alquiler alquiler =alquilerService.terminarAlquiler(terminarDto);

        assertEquals(alquiler.getEstado(),2);

    }



    @Test
    void crear() {
        ///alquiler creado
        CrearAlquilerDTO crearDto = new CrearAlquilerDTO("34",1);

       Alquiler creado = alquilerService.crear(crearDto);

       Alquiler buscadoBd= alquilerService.FindById(creado.getId());

       assertEquals(creado.getIdCliente(),buscadoBd.getIdCliente());
       assertEquals(creado.getId(),buscadoBd.getId());

    }
}