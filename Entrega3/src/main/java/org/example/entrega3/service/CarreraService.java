package org.example.entrega3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.example.entrega3.service.exceptions.Manejo;
import org.example.entrega3.dto.DTOInscriptosCarrera;
import org.example.entrega3.entities.Carrera;
import org.example.entrega3.repository.RepositoryCarrera;

@Service
public class CarreraService {

    @Autowired
    private RepositoryCarrera carreraRepositorio;

    @Transactional(readOnly = true)
    public List<DTOInscriptosCarrera> findCarrerasConEstudiantes() {
        return carreraRepositorio.findCarrerasConEstudiantes();
    }

    @Transactional(readOnly = true)
    public List<DTOInscriptosCarrera> findAll() {
        return carreraRepositorio.findAll().stream().map(DTOInscriptosCarrera::new).toList();
    }

    @Transactional(readOnly = true)
    public DTOInscriptosCarrera findById(int id) {
        return carreraRepositorio.findById(id).map(DTOInscriptosCarrera::new)
                .orElseThrow(() -> new Manejo("Carrera",id));
    }

    @Transactional(readOnly = true)
    public Carrera findByIdEntity(int id){
        return carreraRepositorio.findById(id)
                .orElseThrow(() -> new Manejo("Carrera",id));
    }
}