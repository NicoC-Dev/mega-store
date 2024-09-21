package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubCategoriaDto {

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;
}