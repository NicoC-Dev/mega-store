package com.programacion_avanzada.mega_store.DTOs;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrarSubCategoriaDto {
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 64, message = "El nombre debe tener entre 2 y 64 caracteres.")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "El nombre no debe contener espacios ni números.")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(min = 2, max = 100, message = "La descripción debe tener entre 2 y 100 caracteres.")
    @Pattern(regexp = "^[^\\d]*$", message = "La descripción no debe contener números.")
    private String descripcion;

    @NotNull(message = "Debe seleccionar una categoría.")
    private Long categoriaId;
}
