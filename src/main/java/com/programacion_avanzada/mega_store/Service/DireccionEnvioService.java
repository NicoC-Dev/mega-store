package com.programacion_avanzada.mega_store.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.Mapper.DireccionEnvioMapper;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.DireccionEnvioRepository;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;

@Service
public class DireccionEnvioService implements IDireccionEnvioService {
    @Autowired
    DireccionEnvioRepository direccionEnvioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;


    /*
     * Metodo encargado de agregar direcciones a un usuario,
     * comprobando si el usuario existe.
     */
    @Override
    public DireccionEnvioDto agregaDireccionEnvio(long usuarioId, DireccionEnvioDto dto){
        DireccionEnvio direccionEnvio = DireccionEnvioMapper.toEntity(dto);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        validarProvincia(direccionEnvio.getProvincia());
        validarCiudad(direccionEnvio.getCiudad());
        validarCalle(direccionEnvio.getCalle());
        validarAltura(direccionEnvio.getAltura());
        validarCodigoPostal(direccionEnvio.getCodigoPostal());
        direccionEnvio.setUsuario(usuario);
        
        return DireccionEnvioMapper.toDto(direccionEnvioRepository.save(direccionEnvio));
    }

    private void validarProvincia(String provincia) {
        if (provincia == null || provincia.isEmpty()) {
            throw new IllegalArgumentException("La provincia no puede estar vacía.");
        }
        if (provincia.length() < 2 || provincia.length() > 64) {
            throw new IllegalArgumentException("La provincia debe tener entre 2 y 30 caracteres.");
        }
        if (!provincia.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("La provincia debe contener solo letras.");
        }
    }

    private void validarCiudad(String ciudad) {
        if (ciudad == null || ciudad.isEmpty()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía.");
            
        }
        if (ciudad.length() < 2 || ciudad.length() > 64) {
            throw new IllegalArgumentException("La ciudad debe tener entre 2 y 30 caracteres.");
        }
        if (!ciudad.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("La ciudad debe contener solo letras.");
        }
    }

    private void validarCalle(String calle) {
        if (calle == null || calle.isEmpty()) {
            throw new IllegalArgumentException("La calle no puede estar vacía.");
        }
        if (calle.length() < 2 || calle.length() > 64) {
            throw new IllegalArgumentException("La calle debe tener entre 2 y 30 caracteres.");
        }
        if (!calle.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("La calle debe contener solo letras.");
        }
    }

    private void validarAltura(String altura) {
        if (altura == null || altura.isEmpty()) {
            throw new IllegalArgumentException("La altura no puede estar vacía.");
            
        }
        if (altura.length() < 2 || altura.length() > 4) {
            throw new IllegalArgumentException("La altura debe tener entre 2 y 4 caracteres.");
        }
        if (!altura.matches("^[0-9]+$")) {
            throw new IllegalArgumentException("La altura debe contener solo números.");
        }
    }

    private void validarCodigoPostal(String codigoPostal) {
        if (codigoPostal == null || codigoPostal.isEmpty()) {
            throw new IllegalArgumentException("El código postal no puede estar vacío.");
        }
        if (codigoPostal.length() < 2 || codigoPostal.length() > 4) {
            throw new IllegalArgumentException("El código postal debe tener entre 2 y 4 caracteres.");
        }
        if (!codigoPostal.matches("^[0-9]+$")) {
            throw new IllegalArgumentException("El código postal debe contener solo números.");
        }
    }

    

}
