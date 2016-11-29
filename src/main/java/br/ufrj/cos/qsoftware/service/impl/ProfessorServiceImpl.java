package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.ProfessorService;
import br.ufrj.cos.qsoftware.domain.Professor;
import br.ufrj.cos.qsoftware.repository.ProfessorRepository;
import br.ufrj.cos.qsoftware.service.dto.ProfessorDTO;
import br.ufrj.cos.qsoftware.service.mapper.ProfessorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Professor.
 */
@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService{

    private final Logger log = LoggerFactory.getLogger(ProfessorServiceImpl.class);
    
    @Inject
    private ProfessorRepository professorRepository;

    @Inject
    private ProfessorMapper professorMapper;

    /**
     * Save a professor.
     *
     * @param professorDTO the entity to save
     * @return the persisted entity
     */
    public ProfessorDTO save(ProfessorDTO professorDTO) {
        log.debug("Request to save Professor : {}", professorDTO);
        Professor professor = professorMapper.professorDTOToProfessor(professorDTO);
        professor = professorRepository.save(professor);
        ProfessorDTO result = professorMapper.professorToProfessorDTO(professor);
        return result;
    }

    /**
     *  Get all the professors.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ProfessorDTO> findAll() {
        log.debug("Request to get all Professors");
        List<ProfessorDTO> result = professorRepository.findAll().stream()
            .map(professorMapper::professorToProfessorDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one professor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProfessorDTO findOne(Long id) {
        log.debug("Request to get Professor : {}", id);
        Professor professor = professorRepository.findOne(id);
        ProfessorDTO professorDTO = professorMapper.professorToProfessorDTO(professor);
        return professorDTO;
    }

    /**
     *  Delete the  professor by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Professor : {}", id);
        professorRepository.delete(id);
    }
}
