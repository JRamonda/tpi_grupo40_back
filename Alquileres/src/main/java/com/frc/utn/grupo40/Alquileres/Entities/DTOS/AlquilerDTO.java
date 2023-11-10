package com.frc.utn.grupo40.Alquileres.Entities.DTOS;


import lombok.Data;

@Data
public class AlquilerDTO {

    private int id;

    private String idCliente;
    private int estado;

    private double monto;

    private String moneda;

}
