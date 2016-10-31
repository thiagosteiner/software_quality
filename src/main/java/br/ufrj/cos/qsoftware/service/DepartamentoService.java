package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.DepartamentoDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Departamento.
 */
public interface DepartamentoService {

    /**
     * Save a departamento.
     *
     * @param departamentoDTO the entity to save
     * @return the persisted entity
     */
    DepartamentoDTO save(DepartamentoDTO departamentoDTO);

    /**
     *  Get all the departamentos.
     *  
     *  @return the list of entities
     */
    List<DepartamentoDTO> findAll();

    /**
     *  Get the "id" departamento.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DepartamentoDTO findOne(Long id);

    /**
     *  Delete the "id" departamento.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
