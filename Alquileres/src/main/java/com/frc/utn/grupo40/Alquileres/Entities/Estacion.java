package com.frc.utn.grupo40.Alquileres.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "estaciones")
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class Estacion {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String nombre;
    @Column(name = "fecha_hora_creacion")
    private LocalDateTime fechaHoraCreacion;
    private double latitud;
    private double longitud;
}
