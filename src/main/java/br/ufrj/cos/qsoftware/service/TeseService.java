package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.TeseDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Tese.
 */
public interface TeseService {

    /**
     * Save a tese.
     *
     * @param teseDTO the entity to save
     * @return the persisted entity
     */
    TeseDTO save(TeseDTO teseDTO);

    /**
     *  Get all the tese.
     *  
     *  @return the list of entities
     */
    List<TeseDTO> findAll();

    /**
     *  Get the "id" tese.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TeseDTO findOne(Long id);

    /**
     *  Delete the "id" tese.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
