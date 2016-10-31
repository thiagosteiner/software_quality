package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.TeseService;
import br.ufrj.cos.qsoftware.domain.Tese;
import br.ufrj.cos.qsoftware.repository.TeseRepository;
import br.ufrj.cos.qsoftware.service.dto.TeseDTO;
import br.ufrj.cos.qsoftware.service.mapper.TeseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tese.
 */
@Service
@Transactional
public class TeseServiceImpl implements TeseService{

    private final Logger log = LoggerFactory.getLogger(TeseServiceImpl.class);
    
    @Inject
    private TeseRepository teseRepository;

    @Inject
    private TeseMapper teseMapper;

    /**
     * Save a tese.
     *
     * @param teseDTO the entity to save
     * @return the persisted entity
     */
    public TeseDTO save(TeseDTO teseDTO) {
        log.debug("Request to save Tese : {}", teseDTO);
        Tese tese = teseMapper.teseDTOToTese(teseDTO);
        tese = teseRepository.save(tese);
        TeseDTO result = teseMapper.teseToTeseDTO(tese);
        return result;
    }

    /**
     *  Get all the tese.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TeseDTO> findAll() {
        log.debug("Request to get all Tese");
        List<TeseDTO> result = teseRepository.findAll().stream()
            .map(teseMapper::teseToTeseDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one tese by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TeseDTO findOne(Long id) {
        log.debug("Request to get Tese : {}", id);
        Tese tese = teseRepository.findOne(id);
        TeseDTO teseDTO = teseMapper.teseToTeseDTO(tese);
        return teseDTO;
    }

    /**
     *  Delete the  tese by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tese : {}", id);
        teseRepository.delete(id);
    }
}
