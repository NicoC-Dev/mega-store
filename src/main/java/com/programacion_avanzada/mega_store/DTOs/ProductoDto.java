package com.programacion_avanzada.mega_store.DTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoDto {

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[A-Za-z ]+$")
    private String nombre;

    @NotBlank
    @Size(min = 2)
    private String descripcion;

    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^[A-Za-z ]+$")
    private String tamano;

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[A-Za-z ]+$")
    private String color;

    @NotNull
    @Positive
    private double precioUnitario;

    @NotNull
    @Positive
    private int stock;

    @NotNull
    @Positive
    private int umbralBajoStock;

    @NotNull
    private MarcaDto marca;

    @NotNull
    private SubCategoriaDTO subCategoriaDto;


}
