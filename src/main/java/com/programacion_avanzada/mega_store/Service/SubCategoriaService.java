package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
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

    @Override
    public SubCategoriaDto registrarCategoria(long categoriaId,SubCategoriaDto dto) {
        
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

    
    public List<SubCategoriaDto> listar() {
        List<SubCategoria> subCategorias = subCategoriaRepository.findAll();
        return subCategorias.stream().map(subCategoriaMapper::toDto).toList();
    }

    @Override
    public Optional<SubCategoria> buscarPorId(long id) {
        return subCategoriaRepository.findById(id);
    }

    @Override
    public void eliminar(SubCategoria subCategoria) {
        subCategoria.setEstaActivo(false);
        subCategoriaRepository.save(subCategoria);
    }
}
