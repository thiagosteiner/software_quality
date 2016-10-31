package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.PublicacaoDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Publicacao.
 */
public interface PublicacaoService {

    /**
     * Save a publicacao.
     *
     * @param publicacaoDTO the entity to save
     * @return the persisted entity
     */
    PublicacaoDTO save(PublicacaoDTO publicacaoDTO);

    /**
     *  Get all the publicacaos.
     *  
     *  @return the list of entities
     */
    List<PublicacaoDTO> findAll();

    /**
     *  Get the "id" publicacao.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PublicacaoDTO findOne(Long id);

    /**
     *  Delete the "id" publicacao.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
