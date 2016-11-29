package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.ConviteDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Convite.
 */
public interface ConviteService {

    /**
     * Save a convite.
     *
     * @param conviteDTO the entity to save
     * @return the persisted entity
     */
    ConviteDTO save(ConviteDTO conviteDTO);

    /**
     *  Get all the convites.
     *  
     *  @return the list of entities
     */
    List<ConviteDTO> findAll();

    /**
     *  Get the "id" convite.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConviteDTO findOne(Long id);

    /**
     *  Delete the "id" convite.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
