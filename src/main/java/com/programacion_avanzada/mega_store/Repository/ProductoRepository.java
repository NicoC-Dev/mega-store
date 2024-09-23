package com.programacion_avanzada.mega_store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.Producto;

public interface ProductoRepository  extends JpaRepository<Producto, Long>{
    boolean existsByNombre(String nombre); 
}
