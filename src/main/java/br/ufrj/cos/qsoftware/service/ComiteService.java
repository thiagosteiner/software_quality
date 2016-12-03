package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.ComiteDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Comite.
 */
public interface ComiteService {

    /**
     * Save a comite.
     *
     * @param comiteDTO the entity to save
     * @return the persisted entity
     */
    ComiteDTO save(ComiteDTO comiteDTO);

    /**
     *  Get all the comites.
     *  
     *  @return the list of entities
     */
    List<ComiteDTO> findAll();

    /**
     *  Get the "id" comite.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ComiteDTO findOne(Long id);

    /**
     *  Delete the "id" comite.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
