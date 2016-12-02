package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.ComiteService;
import br.ufrj.cos.qsoftware.domain.Comite;
import br.ufrj.cos.qsoftware.repository.ComiteRepository;
import br.ufrj.cos.qsoftware.service.dto.ComiteDTO;
import br.ufrj.cos.qsoftware.service.mapper.ComiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Comite.
 */
@Service
@Transactional
public class ComiteServiceImpl implements ComiteService{

    private final Logger log = LoggerFactory.getLogger(ComiteServiceImpl.class);
    
    @Inject
    private ComiteRepository comiteRepository;

    @Inject
    private ComiteMapper comiteMapper;

    /**
     * Save a comite.
     *
     * @param comiteDTO the entity to save
     * @return the persisted entity
     */
    public ComiteDTO save(ComiteDTO comiteDTO) {
        log.debug("Request to save Comite : {}", comiteDTO);
        Comite comite = comiteMapper.comiteDTOToComite(comiteDTO);
        comite = comiteRepository.save(comite);
        ComiteDTO result = comiteMapper.comiteToComiteDTO(comite);
        return result;
    }

    /**
     *  Get all the comites.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ComiteDTO> findAll() {
        log.debug("Request to get all Comites");
        List<ComiteDTO> result = comiteRepository.findAllWithEagerRelationships().stream()
            .map(comiteMapper::comiteToComiteDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }


    /**
     *  get all the comites where Documento is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ComiteDTO> findAllWhereDocumentoIsNull() {
        log.debug("Request to get all comites where Documento is null");
        return StreamSupport
            .stream(comiteRepository.findAll().spliterator(), false)
            .filter(comite -> comite.getDocumento() == null)
            .map(comiteMapper::comiteToComiteDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one comite by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ComiteDTO findOne(Long id) {
        log.debug("Request to get Comite : {}", id);
        Comite comite = comiteRepository.findOneWithEagerRelationships(id);
        ComiteDTO comiteDTO = comiteMapper.comiteToComiteDTO(comite);
        return comiteDTO;
    }

    /**
     *  Delete the  comite by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Comite : {}", id);
        comiteRepository.delete(id);
    }
}
