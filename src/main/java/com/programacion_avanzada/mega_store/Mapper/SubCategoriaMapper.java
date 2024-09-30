package com.programacion_avanzada.mega_store.Mapper;

import org.mapstruct.Mapper;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

@Mapper(componentModel = "spring")
public interface SubCategoriaMapper {
    SubCategoriaDTO toDto(SubCategoria subCategoria);
    SubCategoria toEntity(SubCategoriaDTO subCategoriaDto);

    CategoriaDto toDto(Categoria categoria);
    Categoria toEntity(CategoriaDto categoriaDTO);
}

