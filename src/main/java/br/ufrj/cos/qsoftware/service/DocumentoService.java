package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.DocumentoDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Documento.
 */
public interface DocumentoService {

    /**
     * Save a documento.
     *
     * @param documentoDTO the entity to save
     * @return the persisted entity
     */
    DocumentoDTO save(DocumentoDTO documentoDTO);

    /**
     *  Get all the documentos.
     *  
     *  @return the list of entities
     */
    List<DocumentoDTO> findAll();

    /**
     *  Get the "id" documento.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DocumentoDTO findOne(Long id);

    /**
     *  Delete the "id" documento.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
