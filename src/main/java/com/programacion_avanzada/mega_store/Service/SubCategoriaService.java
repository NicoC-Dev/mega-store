package com.programacion_avanzada.mega_store.Service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Mapper.RegistrarSubCategoriaMapper;
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
    private RegistrarSubCategoriaMapper registrarSubCategoriaMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;

    /*
     * Metodo encargado de registrar las subcategorias, 
     * verificando que no haya 2 con el mismo nombre y normalizando los datos.
     */
    @Override
    public RegistrarSubCategoriaDto registrarSubCategoria(RegistrarSubCategoriaDto dto) {
        // Primero, verifica si la categoría existe
        verificarCategoria(dto.getCategoriaId());

        // Verifica si la subcategoría ya existe
        verificarUnicidad(dto.getNombre());

        // Convertir el DTO a entidad
        SubCategoria subCategoria = registrarSubCategoriaMapper.toEntity(dto);

        valirdarNombre(subCategoria.getNombre());
        valirdarDescripcion(subCategoria.getDescripcion());
        
        // Normalizar los datos
        normalizarDatos(subCategoria);

        // Obtener la categoría y asignarla a la subcategoría
        asignarCategoria(subCategoria, dto.getCategoriaId());
        subCategoria.setEstaActivo(true);
        // Guardar la subcategoría en el repositorio
        return registrarSubCategoriaMapper.toDto(subCategoriaRepository.save(subCategoria));
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
        SubCategoria subCategoria = subCategoriaRepository.findById(id).orElse(null);
        if (subCategoria == null || subCategoria.isEstaActivo() == false) {
            throw new EntityNotFoundException("La subcategoria no existe o ya esta inactiva.");
            
        }
        subCategoria.setEstaActivo(false);
        subCategoriaRepository.save(subCategoria);
    }

    @Override
    public void reactivar(long id){
        SubCategoria subCategoria = subCategoriaRepository.findById(id).orElse(null);
        if(subCategoria == null || subCategoria.isEstaActivo() == true){
            throw new EntityNotFoundException("La subcategoria no existe o ya esta activa.");
        }
        subCategoria.setEstaActivo(true);
        subCategoriaRepository.save(subCategoria);
    }

    @Override
    public SubCategoriaDTO actualizar(long id, RegistrarSubCategoriaDto dto) {
        SubCategoria subcategoria = subCategoriaRepository.findById(id).orElse(null);
        
        // Aquí actualizamos los campos de la subcategoría
        valirdarNombre(dto.getNombre());
        valirdarDescripcion(dto.getDescripcion());
        verificarUnicidad(dto.getNombre());
        

        // Asignamos la categoría si es necesario
        if (dto.getCategoriaId() != null) {
            verificarCategoria(dto.getCategoriaId());
            asignarCategoria(subcategoria, dto.getCategoriaId());
        }

        subcategoria.setNombre(dto.getNombre());
        subcategoria.setDescripcion(dto.getDescripcion());

        normalizarDatos(subcategoria);

        subCategoriaRepository.save(subcategoria);
        return subCategoriaMapper.toDto(subcategoria);
    }

    public void valirdarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (nombre.length() < 2 || nombre.length() > 64) {
            throw new IllegalArgumentException("El nombre debe tener entre 2 y 64 caracteres.");
        }
        if (nombre.contains(" ")) {
            throw new IllegalArgumentException("El nombre no debe contener espacios.");
        }
        if (nombre.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre no debe contener números.");
        }
        
    }

    public void valirdarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede estar vacia.");
        }
        if (descripcion.length() < 2 || descripcion.length() > 64) {
            throw new IllegalArgumentException("La descripcion debe tener entre 2 y 64 caracteres.");
        }
        if (descripcion.matches(".*\\d.*")) {
            throw new IllegalArgumentException("La descripcion no debe contener números.");
        }
    }

    public void verificarCategoria(Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new EntityNotFoundException("La categoría no existe");
        }
    }

    public void verificarUnicidad(String nombre) {
        if (subCategoriaRepository.existsByNombre(nombre)) {
            throw new EntityExistsException("La subcategoria ya existe");
        }
    }

    public void normalizarDatos(SubCategoria subCategoria) {
        subCategoria.setNombre(StringUtil.capitalizeFirstLetter(subCategoria.getNombre().toLowerCase().trim()));
        subCategoria.setDescripcion(subCategoria.getDescripcion().toLowerCase().trim());
    }

    public void asignarCategoria(SubCategoria subCategoria, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La categoría no existe")); // Lanza excepción si no se encuentra la categoría
        subCategoria.setCategoria(categoria);
    }
}
