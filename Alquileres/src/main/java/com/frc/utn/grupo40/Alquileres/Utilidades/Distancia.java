package com.frc.utn.grupo40.Alquileres.Utilidades;

public class Distancia {

    public double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
        double metersPerDegree = 110000;

        double deltaX = (longitud2 - longitud1) * metersPerDegree;
        double deltaY = (latitud2 - latitud1) * metersPerDegree;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
