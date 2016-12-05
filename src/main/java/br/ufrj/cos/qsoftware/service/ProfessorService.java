package br.ufrj.cos.qsoftware.service;

import br.ufrj.cos.qsoftware.service.dto.ProfessorDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Professor.
 */
public interface ProfessorService {

    /**
     * Save a professor.
     *
     * @param professorDTO the entity to save
     * @return the persisted entity
     */
    ProfessorDTO save(ProfessorDTO professorDTO);

    /**
     *  Get all the professors.
     *
     *  @return the list of entities
     */
    List<ProfessorDTO> findAll();

    /**
     *  Get the "id" professor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProfessorDTO findOne(Long id);

    /**
     *  Get the "userName" professor.
     *
     *  @param userName the userName of the entity
     *  @return the entity
     */
    ProfessorDTO findOneByUser(String userName);

    /**
     *  Delete the "id" professor.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
