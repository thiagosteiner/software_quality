package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.MonografiaService;
import br.ufrj.cos.qsoftware.domain.Monografia;
import br.ufrj.cos.qsoftware.repository.MonografiaRepository;
import br.ufrj.cos.qsoftware.service.dto.MonografiaDTO;
import br.ufrj.cos.qsoftware.service.mapper.MonografiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Monografia.
 */
@Service
@Transactional
public class MonografiaServiceImpl implements MonografiaService{

    private final Logger log = LoggerFactory.getLogger(MonografiaServiceImpl.class);
    
    @Inject
    private MonografiaRepository monografiaRepository;

    @Inject
    private MonografiaMapper monografiaMapper;

    /**
     * Save a monografia.
     *
     * @param monografiaDTO the entity to save
     * @return the persisted entity
     */
    public MonografiaDTO save(MonografiaDTO monografiaDTO) {
        log.debug("Request to save Monografia : {}", monografiaDTO);
        Monografia monografia = monografiaMapper.monografiaDTOToMonografia(monografiaDTO);
        monografia = monografiaRepository.save(monografia);
        MonografiaDTO result = monografiaMapper.monografiaToMonografiaDTO(monografia);
        return result;
    }

    /**
     *  Get all the monografias.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<MonografiaDTO> findAll() {
        log.debug("Request to get all Monografias");
        List<MonografiaDTO> result = monografiaRepository.findAll().stream()
            .map(monografiaMapper::monografiaToMonografiaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one monografia by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MonografiaDTO findOne(Long id) {
        log.debug("Request to get Monografia : {}", id);
        Monografia monografia = monografiaRepository.findOne(id);
        MonografiaDTO monografiaDTO = monografiaMapper.monografiaToMonografiaDTO(monografia);
        return monografiaDTO;
    }

    /**
     *  Delete the  monografia by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Monografia : {}", id);
        monografiaRepository.delete(id);
    }
}
