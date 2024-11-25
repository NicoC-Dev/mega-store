package com.programacion_avanzada.mega_store.Service;



import com.programacion_avanzada.mega_store.Modelos.Producto;

public interface IInventarioService {
    
    Producto agregarStock(long id, int cantidad);

    Producto quitarStock(long id, int cantidad);
}