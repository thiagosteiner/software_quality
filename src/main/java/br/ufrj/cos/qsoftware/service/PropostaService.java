package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.PropostaDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Proposta.
 */
public interface PropostaService {

    /**
     * Save a proposta.
     *
     * @param propostaDTO the entity to save
     * @return the persisted entity
     */
    PropostaDTO save(PropostaDTO propostaDTO);

    /**
     *  Get all the propostas.
     *  
     *  @return the list of entities
     */
    List<PropostaDTO> findAll();

    /**
     *  Get the "id" proposta.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PropostaDTO findOne(Long id);

    /**
     *  Delete the "id" proposta.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
