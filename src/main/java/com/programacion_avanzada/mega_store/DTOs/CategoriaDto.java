package com.programacion_avanzada.mega_store.DTOs;


import lombok.Data;


@Data
public class CategoriaDto {

    private long id;

    private String nombre;

    private String descripcion;

    private boolean estaActivo;

    
}
