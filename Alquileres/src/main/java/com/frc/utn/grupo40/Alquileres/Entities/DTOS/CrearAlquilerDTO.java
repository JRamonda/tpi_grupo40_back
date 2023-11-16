package com.frc.utn.grupo40.Alquileres.Entities.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearAlquilerDTO {

    private String idCliente;

    private Integer idEstacionRetiro;
    
}
