package com.frc.utn.grupo40.Estaciones.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Alquileres")
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "id_cliente")
    private String idCliente;
    private int estado;
    @Column(name = "estacion_retiro")
    private int estacionRetiro;
    @Column(name = "estacion_devolucion")
    private int estacionDevolucion;
    @Column(name = "fecha_hora_retiro")
    private String fechaHoraRetiro;
    @Column(name = "fecha_hora_devolucion")
    private String fechaHoraDevolucion;
    private double monto;
    @Column(name = "id_tarifa")
    private int idTarifa;

}
