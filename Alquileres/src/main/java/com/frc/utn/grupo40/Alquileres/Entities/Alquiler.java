package com.frc.utn.grupo40.Alquileres.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "Alquileres")
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
@Table
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "id_cliente")
    private String idCliente;
    private int estado;

    @JoinColumn(name = "estacion_retiro")
    @ManyToOne
    private Estacion estacionRetiro;

    @ManyToOne
    @JoinColumn (name = "estacion_devolucion")
    private Estacion estacionDevolucion;
    @Column(name = "fecha_hora_retiro")
    private LocalDateTime fechaHoraRetiro;
    @Column(name = "fecha_hora_devolucion")
    private LocalDateTime fechaHoraDevolucion;
    private double monto;

    @JoinColumn(name = "id_tarifa")
@ManyToOne
    private Tarifa idTarifa;

}
