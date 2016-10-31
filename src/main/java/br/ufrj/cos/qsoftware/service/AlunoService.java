package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.AlunoDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Aluno.
 */
public interface AlunoService {

    /**
     * Save a aluno.
     *
     * @param alunoDTO the entity to save
     * @return the persisted entity
     */
    AlunoDTO save(AlunoDTO alunoDTO);

    /**
     *  Get all the alunos.
     *  
     *  @return the list of entities
     */
    List<AlunoDTO> findAll();

    /**
     *  Get the "id" aluno.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AlunoDTO findOne(Long id);

    /**
     *  Delete the "id" aluno.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
