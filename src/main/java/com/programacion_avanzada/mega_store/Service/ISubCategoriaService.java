package com.programacion_avanzada.mega_store.Service;

import java.util.List;


import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

public interface ISubCategoriaService {
    SubCategoriaDTO registrarSubCategoria(long categoriaId,SubCategoriaDTO dto);
    List<SubCategoriaDTO> listar();
    SubCategoria buscarPorId(long id);
    void eliminar(long id);
    SubCategoriaDTO actualizar(long id, SubCategoriaDTO dto);
}
