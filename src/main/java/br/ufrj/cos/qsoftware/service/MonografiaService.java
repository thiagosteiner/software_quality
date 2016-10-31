package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.MonografiaDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Monografia.
 */
public interface MonografiaService {

    /**
     * Save a monografia.
     *
     * @param monografiaDTO the entity to save
     * @return the persisted entity
     */
    MonografiaDTO save(MonografiaDTO monografiaDTO);

    /**
     *  Get all the monografias.
     *  
     *  @return the list of entities
     */
    List<MonografiaDTO> findAll();

    /**
     *  Get the "id" monografia.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MonografiaDTO findOne(Long id);

    /**
     *  Delete the "id" monografia.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
