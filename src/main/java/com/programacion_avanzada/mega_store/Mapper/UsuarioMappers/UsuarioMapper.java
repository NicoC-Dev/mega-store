package com.programacion_avanzada.mega_store.Mapper.UsuarioMappers;

import com.programacion_avanzada.mega_store.DTOs.UsuarioDtos.UsuarioDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;



public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setRol(usuario.getRol());

       

        return dto;
    }


    public static Usuario toEntity(UsuarioDto usuarioDto) {

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setRol(usuarioDto.getRol());

        
        return usuario;
    }
}
