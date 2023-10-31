package com.frc.utn.grupo40.Estaciones.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "tarifas")
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString

public class Tarifas {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    @Column(name = "tipo_tarifa")
    private int tipoTarifa;
    private char definicion;
    @Column(name = "dia_semana")
    private int diaSemana;
    @Column(name = "dia_mes")
    @Nullable
    private Integer diaMes;
    @Nullable
    private Integer mes;
    @Nullable
    private Integer anio;
    @Column(name= "monto_fijo_alquiler")
    private double montoFijoAlquiler;
    @Column(name= "monto_minuto_fraccion")
    private double montoMinutoFraccion;
    @Column(name="monto_km")
    private double montoKm;
    @Column(name="monto_hora")
    private double montoHora;


}
