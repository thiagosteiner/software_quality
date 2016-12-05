package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.ConviteOrientadorDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ConviteOrientador.
 */
public interface ConviteOrientadorService {

    /**
     * Save a conviteOrientador.
     *
     * @param conviteOrientadorDTO the entity to save
     * @return the persisted entity
     */
    ConviteOrientadorDTO save(ConviteOrientadorDTO conviteOrientadorDTO);

    /**
     *  Get all the conviteOrientadors.
     *
     *  @return the list of entities
     */
    List<ConviteOrientadorDTO> findAll();


    /**
     *  Get all the conviteOrientadors associated to userName.
     *
     *  @return the list of entities
     */
    List<ConviteOrientadorDTO> findAllWhereAlunoIs(String userName);

    /**
     *  Get the "id" conviteOrientador.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConviteOrientadorDTO findOne(Long id);

    /**
     *  Delete the "id" conviteOrientador.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
