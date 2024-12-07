package com.programacion_avanzada.mega_store.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.programacion_avanzada.mega_store.DTOs.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarMarcaMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Service.MarcaService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private RegistrarMarcaMapper registrarMarcaMapper;

    @InjectMocks
    private MarcaService marcaService;


    @Test
    void testRegistrarMarca() {
        // Crear un objeto DTO de prueba
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("MarcaValida");
        dto.setDescripcion("Descripción válida");

        // Configurar el comportamiento del mock para que devuelva una nueva Marca
        Marca marcaEsperada = new Marca();
        marcaEsperada.setNombre("MarcaValida");
        marcaEsperada.setDescripcion("Descripción válida");
        when(registrarMarcaMapper.toEntity(dto)).thenReturn(marcaEsperada);

        // Ahora llama al metodo después de haber configurado el mock
        Marca marca = registrarMarcaMapper.toEntity(dto);

        // Simular el comportamiento del repositorio
        when(marcaRepository.existsByNombre(anyString())).thenReturn(false);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        when(registrarMarcaMapper.toDto(marca)).thenReturn(dto); // Supongamos que también quieres devolver el DTO.

        // Ejecutar el metodo a probar
        RegistrarMarcaDto result = marcaService.registrarMarca(dto);

        // Verificar los resultados
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals("MarcaValida", result.getNombre());
        assertEquals("Descripción válida", result.getDescripcion());

        // Verificar que se hayan llamado los métodos esperados
        verify(marcaRepository).existsByNombre("MarcaValida");
        verify(marcaRepository).save(marca);
    }

    @Test
    void testRegistrarMarcaYaExistente() {
        // Crear un objeto DTO de prueba
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("MarcaExistente");
        dto.setDescripcion("Descripción existente");

        // Configurar el comportamiento del mock para que devuelva una nueva Marca
        Marca marcaEsperada = new Marca();
        marcaEsperada.setNombre("MarcaValida");
        marcaEsperada.setDescripcion("Descripción válida");
        when(registrarMarcaMapper.toEntity(dto)).thenReturn(marcaEsperada);

        // Simular que la marca ya existe en el repositorio
        when(marcaRepository.existsByNombre(anyString())).thenReturn(true);

        // Ejecutar el método a probar y verificar que se lanza la excepción
        Exception exception = assertThrows(EntityExistsException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("La marca ya existe", exception.getMessage());
    }
}
