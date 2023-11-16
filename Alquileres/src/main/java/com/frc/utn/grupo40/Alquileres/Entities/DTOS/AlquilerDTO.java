package com.frc.utn.grupo40.Alquileres.Entities.DTOS;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerDTO {

    private int id;
    private String idCliente;

    private int estado;

    private String moneda;

    private Integer idPuntoEntrega;
}
