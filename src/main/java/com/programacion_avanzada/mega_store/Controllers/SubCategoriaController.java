package com.programacion_avanzada.mega_store.Controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.SubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

import com.programacion_avanzada.mega_store.Service.SubCategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class SubCategoriaController {

    @Autowired
    private SubCategoriaService subCategoriaService;
    @Autowired
    private SubCategoriaMapper subCategoriaMapper;

    // Crear una subcategoría
    @PostMapping
    public SubCategoriaDto registrar(@RequestBody @Valid SubCategoriaDto dto) {
        return subCategoriaService.registrarCategoria(dto);
    }

    // Listar todas las subcategorías
    @GetMapping
    public List<SubCategoriaDto> listar() {
        return subCategoriaService.listar();
    }

    // Buscar subcategoría por ID
    @GetMapping("/{id}")
    public SubCategoriaDto buscarPorId(@PathVariable long id) {
        return subCategoriaService.buscarPorId(id)
                                   .map(subCategoriaMapper::toDto)
                                   .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));
    }

    // Desactivar una subcategoría
    @PutMapping("/desactivar/{id}")
    public void desactivar(@PathVariable long id) {
        SubCategoria subCategoria = subCategoriaService.buscarPorId(id)
                                                      .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));
        subCategoriaService.eliminar(subCategoria);  // eliminar` desactiva la subcategoría.
    }
}

