package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Mapper.SubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;

import ch.qos.logback.core.util.StringUtil;
import jakarta.persistence.EntityExistsException;



@Service
public class SubCategoriaService implements ISubCategoriaService {

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Autowired
    private SubCategoriaMapper subCategoriaMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;

    /*
     * Metodo encargado de registrar las subcategorias, 
     * verificando que no haya 2 con el mismo nombre y normalizando los datos.
     */
    @Override
    public SubCategoriaDTO registrarSubCategoria(long categoriaId,SubCategoriaDTO dto) {
        
        SubCategoria subCategoria = subCategoriaMapper.toEntity(dto);
        
        
        if(subCategoriaRepository.existsByNombre(subCategoria.getNombre()) != true || categoriaRepository.existsById(categoriaId) == true){

            Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);

            subCategoria.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase()));
            subCategoria.setDescripcion(dto.getDescripcion().toLowerCase());
            subCategoria.setCategoria(categoria);

            return subCategoriaMapper.toDto(subCategoriaRepository.save(subCategoria));

        }else{
            throw new EntityExistsException("La subcategoria ya existe");
        }
    }

    @Override
    public List<SubCategoriaDTO> listar() {
        List<SubCategoria> subCategorias = subCategoriaRepository.findAll();
        return subCategorias.stream().map(subCategoriaMapper::toDto).toList();
    }

    @Override
    public SubCategoria buscarPorId(long id) {
        return subCategoriaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(long id) {
        SubCategoria subCategoria = subCategoriaRepository.findById(id).filter(SubCategoria::isEstaActivo).orElse(null);
        subCategoria.setEstaActivo(false);
        subCategoriaRepository.save(subCategoria);
    }

    @Override
    public SubCategoriaDTO actualizar(long id, SubCategoriaDTO dto) {
        SubCategoria subcategoria = subCategoriaRepository.findById(id).orElse(null);
        
        // Aquí actualizamos los campos de la subcategoría
        subcategoria.setNombre(dto.getNombre());
        //subcategoria.setCategoria(dto.getCategoria());

        subCategoriaRepository.save(subcategoria);
        return subCategoriaMapper.toDto(subcategoria);
    }
}
