package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.ConviteComiteDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ConviteComite.
 */
public interface ConviteComiteService {

    /**
     * Save a conviteComite.
     *
     * @param conviteComiteDTO the entity to save
     * @return the persisted entity
     */
    ConviteComiteDTO save(ConviteComiteDTO conviteComiteDTO);

    /**
     *  Get all the conviteComites.
     *  
     *  @return the list of entities
     */
    List<ConviteComiteDTO> findAll();

    /**
     *  Get the "id" conviteComite.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConviteComiteDTO findOne(Long id);

    /**
     *  Delete the "id" conviteComite.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
