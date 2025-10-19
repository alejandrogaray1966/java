package org.example.entrega3.service.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
// RequiredArgsConstructor: Genera un constructor con todos los atributos de tipo final(obligatorio)
@RequiredArgsConstructor

public class Manejo extends RuntimeException {
    private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private final String message;

    public Manejo(String entity, int id){
        this.message = String.format("La entidad %s con id %d no encontrado", entity, id);
    }
    
}