package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class MarcaDto {

    private Long id;
    
    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[^\\d]*$")
    private String nombre;

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[^\\d]*$")
    private String descripcion;

    private boolean estaActivo;

}
