package com.frc.utn.grupo40.Alquileres.Utilidades;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DistanciaTest {

    @Test
    void calcularDistancia() {

        Distancia dis = new Distancia();
        double esperada=4547.64303;
       double calculada = dis.calcularDistancia(-31.383393789385238,-64.2133259589045, -31.389057292916398,-64.17237351083637);
        BigDecimal roundedNumber = BigDecimal.valueOf(calculada).setScale(5, BigDecimal.ROUND_HALF_UP);

       assertEquals(roundedNumber,BigDecimal.valueOf(esperada).setScale(5, BigDecimal.ROUND_HALF_UP));

    }
}